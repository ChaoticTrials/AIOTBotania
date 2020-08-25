package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.PlayerHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class ToolUtil {
    private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");
    private static final Pattern SAPLING_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)sapling)|(?:(?:[a-z-_.:]|^)Sapling))(?:[A-Z-_.:]|$)");

    public static void inventoryTick(ItemStack stack, World world, Entity player, int MPD) {
        if (!world.isRemote && player instanceof PlayerEntity && stack.getDamage() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, (PlayerEntity) player, MPD * 2, true)) {
            stack.setDamage(stack.getDamage() - 1);
        }
    }

    public static boolean hitEntity(ItemStack stack, LivingEntity entity, int MPD) {
        ToolCommons.damageItemIfPossible(stack, 1, entity, MPD);
        return true;
    }

    public static boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity, int MPD) {
        if (state.getBlockHardness(world, pos) != 0F) {
            ToolCommons.damageItemIfPossible(stack, 1, entity, MPD);
        }

        return true;
    }

    public static void toggleMode(PlayerEntity player, ItemStack stack) {
        Style dark_blue = Style.EMPTY.setFormatting(TextFormatting.DARK_BLUE).setItalic(true);
        Style aqua = Style.EMPTY.setFormatting(TextFormatting.AQUA).setItalic(true);
        IFormattableTextComponent text = new TranslationTextComponent(AIOTBotania.MODID + ".toggleMode").appendString(" ").mergeStyle(dark_blue);
        IFormattableTextComponent utilityMode = new TranslationTextComponent(AIOTBotania.MODID + ".utilityMode").mergeStyle(aqua);
        IFormattableTextComponent hoeMode = new TranslationTextComponent(AIOTBotania.MODID + ".hoeMode").mergeStyle(aqua);
        IFormattableTextComponent hoeModePath = new TranslationTextComponent(AIOTBotania.MODID + ".hoeModePath").mergeStyle(aqua);

        if (ItemNBTHelper.getBoolean(stack, "hoemode", true)) {
            ItemNBTHelper.setBoolean(stack, "hoemode", false);
            text.append(utilityMode);
        } else {
            ItemNBTHelper.setBoolean(stack, "hoemode", true);
            if (stack.getItem() instanceof ItemLivingrockAIOT)
                text = text.append(hoeMode);
            else
                text.append(hoeModePath);
        }

        player.sendStatusMessage(text, true);
    }

    private static ActionResultType tiltBlock(PlayerEntity player, World world, BlockPos pos, ItemStack stack, BlockState state, int MPD) {

        world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!world.isRemote) {
            world.setBlockState(pos, state);
            ToolCommons.damageItemIfPossible(stack, 1, player, MPD);
        }
        return ActionResultType.SUCCESS;
    }

    public static ActionResultType hoemodeUse(@Nonnull ItemUseContext ctx, PlayerEntity player, World world, BlockPos pos, Direction side, Block block, int manaPerDamage) {
        if (!player.isCrouching() && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.GRASS_PATH)) {
            return ToolUtil.hoeUse(ctx, false, true, manaPerDamage);
        } else {
            if (side != Direction.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                return ToolUtil.shovelUse(ctx, manaPerDamage);
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    public static ActionResultType hoeUse(ItemUseContext ctx, boolean special, boolean low_tier, int MPD) {
        ItemStack stack = ctx.getItem();
        PlayerEntity player = ctx.getPlayer();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        Direction side = ctx.getFace();

        if (player == null || !player.canPlayerEdit(pos, side, stack)) {
            return ActionResultType.PASS;
        } else {
            Block block = world.getBlockState(pos).getBlock();

            if (side != Direction.DOWN && world.isAirBlock(pos.up())) {
                if (block == Blocks.GRASS_BLOCK || block == Blocks.GRASS_PATH || block == Blocks.DIRT) {

                    BlockState farmland;
                    if (special) {
                        farmland = Registration.custom_farmland.get().getDefaultState();
                    } else {
                        farmland = Blocks.FARMLAND.getDefaultState();
                    }
                    return tiltBlock(player, world, pos, stack, farmland, MPD);
                } else if (block instanceof FarmlandBlock && special) {
                    Block block1 = Blocks.GRASS_BLOCK;
                    return tiltBlock(player, world, pos, stack, block1.getDefaultState(), MPD);
                } else if (block instanceof FarmlandBlock && !low_tier) {
                    Block block1 = Blocks.DIRT;
                    return tiltBlock(player, world, pos, stack, block1.getDefaultState(), MPD);
                }
            }
            return ActionResultType.SUCCESS;
        }
    }

    @Nonnull
    public static ActionResultType pickUse(ItemUseContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        if (player != null) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stackAt = player.inventory.getStackInSlot(i);
                if (!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getTranslationKey()).find()) {
                    ActionResultType did = PlayerHelper.substituteUse(ctx, stackAt);
                    if (did == ActionResultType.SUCCESS && !ctx.getWorld().isRemote())
                        ItemsRemainingRenderHandler.send(player, new ItemStack(Blocks.TORCH), TORCH_PATTERN);
                    return did;
                }
            }
        }

        return ActionResultType.PASS;
    }

    @Nonnull
    public static ActionResultType axeUse(ItemUseContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        if (player != null) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stackAt = player.inventory.getStackInSlot(i);
                if (!stackAt.isEmpty() && SAPLING_PATTERN.matcher(stackAt.getItem().getTranslationKey()).find()) {
                    ActionResultType did = PlayerHelper.substituteUse(ctx, stackAt);
                    if (did == ActionResultType.SUCCESS && !ctx.getWorld().isRemote())
                        ItemsRemainingRenderHandler.send(player, new ItemStack(Blocks.OAK_SAPLING), SAPLING_PATTERN);
                    return did;
                }
            }
        }

        return ActionResultType.PASS;
    }

    public static ActionResultType shovelUse(ItemUseContext ctx, int MPD) {
        ItemStack stack = ctx.getItem();
        PlayerEntity player = ctx.getPlayer();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();

        if (!(player == null || !player.canPlayerEdit(pos, ctx.getFace(), stack))) {
            Block block = world.getBlockState(pos).getBlock();

            if (ctx.getFace() != Direction.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)) {
                Block block1 = Blocks.GRASS_PATH;

                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!world.isRemote) {
                    world.setBlockState(pos, block1.getDefaultState());
                    ToolCommons.damageItemIfPossible(stack, 1, player, MPD);
                }
                return ActionResultType.SUCCESS;
            }

        }
        return ActionResultType.PASS;
    }

}

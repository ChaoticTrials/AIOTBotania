package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.Event;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class ToolUtil {
    private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");
    private static final Pattern SAPLING_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)sapling)|(?:(?:[a-z-_.:]|^)Sapling))(?:[A-Z-_.:]|$)");

    public static void inventoryTick(ItemStack stack, World world, Entity player, int MPD) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MPD * 2, true)) {
            stack.setDamage(stack.getDamage() - 1);
        }
    }

    public static boolean hitEntity(ItemStack stack, EntityLivingBase entity, int MPD) {
        ToolCommons.damageItem(stack, 1, entity, MPD);
        return true;
    }

    public static boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity, int MPD) {
        if (state.getBlockHardness(world, pos) != 0F) {
            ToolCommons.damageItem(stack, 1, entity, MPD);
        }

        return true;
    }

    public static void toggleMode(EntityPlayer player, ItemStack stack) {
        ITextComponent text;

        if(ItemNBTHelper.getBoolean(stack, "hoemode", true)) {
            ItemNBTHelper.setBoolean(stack, "hoemode", false);

            text = new TextComponentTranslation("aiotbotania.toggleMode").appendText(": ").setStyle(new Style().setColor(TextFormatting.DARK_BLUE).setItalic(true))
                    .appendSibling(new TextComponentTranslation("aiotbotania.utilityMode").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
        } else {
            ItemNBTHelper.setBoolean(stack, "hoemode", true);
            text = new TextComponentTranslation("aiotbotania.toggleMode").appendText(": ").setStyle(new Style().setColor(TextFormatting.DARK_BLUE).setItalic(true))
                    .appendSibling(new TextComponentTranslation("aiotbotania.hoeMode").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
        }

        player.sendStatusMessage(text, true);
    }

    private static EnumActionResult tiltBlock(EntityPlayer player, World world, BlockPos pos, ItemStack stack, Block block1, int MPD) {

        world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if(!world.isRemote) {
            world.setBlockState(pos, block1.getDefaultState());
            ToolCommons.damageItem(stack, 1, player, MPD);
        }
        return EnumActionResult.SUCCESS;
    }

    public static EnumActionResult hoeUse(ItemUseContext ctx, boolean special, boolean low_tier, int MPD) {
        ItemStack stack = ctx.getItem();
        EntityPlayer player = ctx.getPlayer();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        EnumFacing side = ctx.getFace();

        if (player == null || !player.canPlayerEdit(pos, side, stack)) {
            return EnumActionResult.PASS;
        } else {
            UseHoeEvent event = new UseHoeEvent(ctx);
            if (MinecraftForge.EVENT_BUS.post(event))
                return EnumActionResult.FAIL;

            if (event.getResult() == Event.Result.ALLOW) {
                ToolCommons.damageItem(stack, 1, player, MPD);
                return EnumActionResult.SUCCESS;
            }

            Block block = world.getBlockState(pos).getBlock();

            if(side != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if(block == Blocks.GRASS_BLOCK || block == Blocks.GRASS_PATH || block == Blocks.DIRT) {

                    Block block1 = Blocks.FARMLAND;
                    if(special) {
                        block1 = ModBlocks.superFarmland;
                    }
                    return tiltBlock(player, world, pos, stack, block1, MPD);
                } else if((block == Blocks.FARMLAND) && !low_tier) {
                    Block block1 = Blocks.DIRT;
                    return tiltBlock(player, world, pos, stack, block1, MPD);
                } else if((block == Blocks.FARMLAND || block == ModBlocks.superFarmland) && special) {
                    Block block1 = Blocks.GRASS_BLOCK;
                    return tiltBlock(player, world, pos, stack, block1, MPD);
                }
            }
            return EnumActionResult.SUCCESS;
        }
    }

    @Nonnull
    public static EnumActionResult pickUse(ItemUseContext ctx) {
        EntityPlayer player = ctx.getPlayer();

        if(player != null) {
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stackAt = player.inventory.getStackInSlot(i);
                if(!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getTranslationKey()).find()) {
                    EnumActionResult did = stackAt.getItem().onItemUse(new ItemUseContext(player, stackAt, ctx.getPos(), ctx.getFace(), ctx.getHitX(), ctx.getHitY(), ctx.getHitZ()));
                    ItemsRemainingRenderHandler.set(player, new ItemStack(Blocks.TORCH), TORCH_PATTERN);
                    return did;
                }
            }
        }

        return EnumActionResult.PASS;
    }

    @Nonnull
    public static EnumActionResult axeUse(ItemUseContext ctx) {
        EntityPlayer player = ctx.getPlayer();
        if(player != null) {
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stackAt = player.inventory.getStackInSlot(i);
                if(!stackAt.isEmpty() && SAPLING_PATTERN.matcher(stackAt.getItem().getTranslationKey()).find()) {
                    EnumActionResult did = stackAt.getItem().onItemUse(new ItemUseContext(player, stackAt, ctx.getPos(), ctx.getFace(), ctx.getHitX(), ctx.getHitY(), ctx.getHitZ()));
                    ItemsRemainingRenderHandler.set(player, new ItemStack(Blocks.OAK_SAPLING), SAPLING_PATTERN);
                    return did;
                }
            }
        }

        return EnumActionResult.PASS;
    }

    public static EnumActionResult shovelUse(ItemUseContext ctx, int MPD) {
        ItemStack stack = ctx.getItem();
        EntityPlayer player = ctx.getPlayer();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();

        if(player == null || !player.canPlayerEdit(pos, ctx.getFace(), stack))
            return EnumActionResult.PASS;
        else {
            UseHoeEvent event = new UseHoeEvent(ctx);
            if(MinecraftForge.EVENT_BUS.post(event))
                return EnumActionResult.FAIL;

            if(event.getResult() == Event.Result.ALLOW) {
                ToolCommons.damageItem(stack, 1, player, MPD);
                return EnumActionResult.SUCCESS;
            }

            Block block = world.getBlockState(pos).getBlock();

            if(ctx.getFace() != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)) {
                Block block1 = Blocks.GRASS_PATH;

                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (world.isRemote)
                    return EnumActionResult.SUCCESS;
                else {
                    world.setBlockState(pos, block1.getDefaultState());
                    ToolCommons.damageItem(stack, 1, player, MPD);
                    return EnumActionResult.SUCCESS;
                }
            }

            return EnumActionResult.PASS;
        }
    }

}

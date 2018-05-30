package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.regex.Pattern;

public class ToolUtil {
    private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");
    private static final Pattern SAPLING_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)sapling)|(?:(?:[a-z-_.:]|^)Sapling))(?:[A-Z-_.:]|$)");

    public static void onUpdate(ItemStack stack, World world, Entity player, int MPD) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MPD * 2, true)) {
            stack.setItemDamage(stack.getItemDamage() - 1);
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

    public static void changeMode(EntityPlayer player, ItemStack stack) {
        ITextComponent text;

        if(ItemNBTHelper.getBoolean(stack, "hoemode", true)) {
            ItemNBTHelper.setBoolean(stack, "hoemode", false);

            text = new TextComponentTranslation("aiotbotania.changeMode").appendText(": ").setStyle(new Style().setColor(TextFormatting.DARK_BLUE).setItalic(true))
                    .appendSibling(new TextComponentTranslation("aiotbotania.utilityMode").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
        } else {
            ItemNBTHelper.setBoolean(stack, "hoemode", true);
            text = new TextComponentTranslation("aiotbotania.changeMode").appendText(": ").setStyle(new Style().setColor(TextFormatting.DARK_BLUE).setItalic(true))
                    .appendSibling(new TextComponentTranslation("aiotbotania.hoeMode").setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)));
        }

        player.sendStatusMessage(text, true);
    }

    private static EnumActionResult tiltBlock(EntityPlayer player, World world, BlockPos pos, ItemStack stack, Block block1, int MPD) {

        SoundType soundtype = block1.getSoundType(block1.getDefaultState(), world, pos, player);

        world.playSound(null, pos, soundtype.getStepSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

        if(world.isRemote)
            return EnumActionResult.SUCCESS;
        else {
            world.setBlockState(pos, block1.getDefaultState());
            ToolCommons.damageItem(stack, 1, player, MPD);
            return EnumActionResult.SUCCESS;
        }
    }

    public static EnumActionResult hoeUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, boolean special, int MPD) {
        ItemStack stack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos, side, stack)) {
            return EnumActionResult.PASS;
        } else {
            UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
            if (MinecraftForge.EVENT_BUS.post(event))
                return EnumActionResult.FAIL;

            if (event.getResult() == Event.Result.ALLOW) {
                ToolCommons.damageItem(stack, 1, player, MPD);
                return EnumActionResult.SUCCESS;
            }

            Block block = world.getBlockState(pos).getBlock();

            if(side != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if(block == Blocks.GRASS || block == Blocks.GRASS_PATH || block == Blocks.DIRT) {

                    Block block1 = Blocks.FARMLAND;
                    if(special) {
                        block1 = ModBlocks.superfarmland;
                    }
                    return tiltBlock(player, world, pos, stack, block1, MPD);
                } else if((block == Blocks.FARMLAND || block == ModBlocks.superfarmland) && special) {
                    Block block1 = Blocks.DIRT;
                    return tiltBlock(player, world, pos, stack, block1, MPD);
                }
            }
            return EnumActionResult.PASS;
        }
    }

    public static EnumActionResult pickUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float sx, float sy, float sz) {
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stackAt = player.inventory.getStackInSlot(i);
            if (!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getUnlocalizedName()).find()) {
                ItemStack saveHeldStack = player.getHeldItem(hand);
                player.setHeldItem(hand, stackAt);
                EnumActionResult did = stackAt.getItem().onItemUse(player, world, pos, hand, side, sx, sy, sz);
                player.setHeldItem(hand, saveHeldStack);
                ItemsRemainingRenderHandler.set(player, new ItemStack(Blocks.TORCH), TORCH_PATTERN);
                return did;
            }
        }

        return EnumActionResult.PASS;
    }

    public static EnumActionResult axeUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float sx, float sy, float sz) {
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stackAt = player.inventory.getStackInSlot(i);
            if (!stackAt.isEmpty() && SAPLING_PATTERN.matcher(stackAt.getItem().getUnlocalizedName()).find()) {
                ItemStack saveHeldStack = player.getHeldItem(hand);
                player.setHeldItem(hand, stackAt);
                EnumActionResult did = stackAt.getItem().onItemUse(player, world, pos, hand, side, sx, sy, sz);
                player.setHeldItem(hand, saveHeldStack);
                ItemsRemainingRenderHandler.set(player, new ItemStack(Blocks.SAPLING), SAPLING_PATTERN);
                return did;
            }
        }

        return EnumActionResult.PASS;
    }

    public static EnumActionResult shovelUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, int MPD) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos, side, stack)) {
            return EnumActionResult.PASS;
        } else {
            UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return EnumActionResult.FAIL;
            } else if (event.getResult() == Event.Result.ALLOW) {
                ToolCommons.damageItem(stack, 1, player, MPD);
                return EnumActionResult.SUCCESS;
            } else {
                Block block = world.getBlockState(pos).getBlock();
                if (side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                    Block block1 = Blocks.GRASS_PATH;

                    tiltBlock(player, world, pos, stack, block1, MPD);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }
}

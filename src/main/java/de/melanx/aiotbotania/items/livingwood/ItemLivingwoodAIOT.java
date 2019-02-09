package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.blocks.ModBlocks;
import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemLivingwoodAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 66;

    public ItemLivingwoodAIOT() {
        super("livingwoodAIOT", ToolMaterials.livingwoodAIOTMaterial, 6.0F, -2.4F, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock();

        if(!player.isSneaking() && (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRASS_PATH)) {
                return ToolUtil.hoeUse(player, world, pos, hand, side, false, MANA_PER_DAMAGE);
        } else {
            if (side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                return ToolUtil.shovelUse(player, world, pos, hand, side, MANA_PER_DAMAGE);
            }else{
                return EnumActionResult.PASS;
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.FAIL, itemStack);
    }

}

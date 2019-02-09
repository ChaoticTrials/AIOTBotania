package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class ItemLivingrockAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 44;

    public ItemLivingrockAIOT() {
        super("livingrockAIOT", ToolMaterials.livingrockAIOTMaterial, 6.0F, -2.4F, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock();

        boolean hoemode = ItemNBTHelper.getBoolean(player.getHeldItem(hand), "hoemode", true);

        if(hoemode) {
            if (!player.isSneaking() && (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRASS_PATH)) {
                return ToolUtil.hoeUse(player, world, pos, hand, side, false, true, MANA_PER_DAMAGE);
            } else {
                if (side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                    return ToolUtil.shovelUse(player, world, pos, hand, side, MANA_PER_DAMAGE);
                } else {
                    return EnumActionResult.PASS;
                }
            }
        } else {
            if(!player.isSneaking()) {
                return ToolUtil.pickUse(player, world, pos, hand, side, hitX, hitY, hitZ);
            } else {
                if(side == EnumFacing.UP){
                    return ToolUtil.axeUse(player, world, pos, hand, side, hitX, hitY, hitZ);
                }
                return EnumActionResult.PASS;
            }
        }
    }

}

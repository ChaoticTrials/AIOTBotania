package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemShovelBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLivingrockShovel extends ItemShovelBase {

    private static final int MANA_PER_DAMAGE = 40;

    public ItemLivingrockShovel() {
        super("livingrockShovel", ToolMaterials.livingrockMaterial, MANA_PER_DAMAGE);
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock();

        if (side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
            return ToolUtil.shovelUse(player, world, pos, hand, side, MANA_PER_DAMAGE);
        } else if(!player.isSneaking() && block == Blocks.GRASS_PATH) {
            return ToolUtil.hoeUse(player, world, pos, hand, side, false, false, MANA_PER_DAMAGE);
        }
        return EnumActionResult.PASS;
    }
}

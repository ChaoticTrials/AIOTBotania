package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class ItemLivingrockAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 44;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.4F;

    public ItemLivingrockAIOT() {
        super("livingrock_aiot", ItemTiers.LIVINGROCK_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemUseContext ctx) {
        ItemStack stack = ctx.getItem();
        EntityPlayer player = ctx.getPlayer();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        EnumFacing side = ctx.getFace();

        Block block = world.getBlockState(pos).getBlock();

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if(hoemode) {
            if (!player.isSneaking() && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.GRASS_PATH)) {
                return ToolUtil.hoeUse(ctx, false, true, MANA_PER_DAMAGE);
            } else {
                if (side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                    return ToolUtil.shovelUse(ctx, MANA_PER_DAMAGE);
                } else {
                    return EnumActionResult.PASS;
                }
            }
        } else {
            if(!player.isSneaking()) {
                return ToolUtil.pickUse(ctx);
            } else {
                if(side == EnumFacing.UP){
                    return ToolUtil.axeUse(ctx);
                }
                return EnumActionResult.PASS;
            }
        }
    }

}

package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAxeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemLivingrockAxe extends ItemAxeBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final float ATTACK_DAMAGE = 6.0F;
    private static final float ATTACK_SPEED = -3.1F;

    public ItemLivingrockAxe() {
        super("livingrock_axe", ItemTiers.LIVINGROCK_ITEM_TIER, MANA_PER_DAMAGE, ATTACK_DAMAGE, ATTACK_SPEED);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemUseContext ctx) {
        EnumFacing side = ctx.getFace();
        if(side == EnumFacing.UP) {
            return ToolUtil.axeUse(ctx);
        } else {
            World world = ctx.getWorld();
            BlockPos pos = ctx.getPos();
            IBlockState state = world.getBlockState(pos);
            Block block = BLOCK_STRIPPING_MAP.get(state.getBlock());
            if (block != null) {
                EntityPlayer player = ctx.getPlayer();
                world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote) {
                    world.setBlockState(pos, block.getDefaultState().with(BlockRotatedPillar.AXIS, state.get(BlockRotatedPillar.AXIS)), 11);
                    if (player != null) {
                        ctx.getItem().damageItem(1, player);
                    }
                }

                return EnumActionResult.SUCCESS;
            } else {
                return EnumActionResult.PASS;
            }
        }
    }

}


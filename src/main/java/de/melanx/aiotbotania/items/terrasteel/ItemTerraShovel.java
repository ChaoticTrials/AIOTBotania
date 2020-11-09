package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.items.base.ItemShovelBase;
import de.melanx.aiotbotania.util.ToolBreakContext;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;

public class ItemTerraShovel extends ItemShovelBase implements ISequentialBreaker {

    public ItemTerraShovel() {
        this(BotaniaAPI.instance().getTerrasteelItemTier());
    }

    public ItemTerraShovel(IItemTier mat) {
        super(mat, (int) (ItemTerraSteelAIOT.DAMAGE / 2f), 15, ItemTerraSteelAIOT.MANA_PER_DAMAGE);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemTerraSteelAIOT.setEnabled(stack, !ItemTerraSteelAIOT.isEnabled(stack));
        if (!world.isRemote) {
            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.terraPickMode, SoundCategory.PLAYERS, 0.5F, 0.4F);
        }
        return ActionResult.resultSuccess(stack);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        BlockRayTraceResult raycast = ToolCommons.raytraceFromEntity(player, 10.0D, false);
        if (!player.world.isRemote && raycast.getType() == RayTraceResult.Type.BLOCK) {
            Direction face = raycast.getFace();
            this.breakOtherBlock(player, stack, pos, pos, face);
            BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
        }
        return false;
    }

    @Override
    public void breakOtherBlock(PlayerEntity player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (ItemTerraSteelAIOT.isEnabled(stack)) {
            World world = player.world;
            BlockState state = world.getBlockState(pos);
            if (stack.getToolTypes().stream().anyMatch(state::isToolEffective)) {
                if (!world.isAirBlock(pos)) {
                    ToolUtil.removeBlocksInRange(new ToolBreakContext(player, pos, this.getTier()), side, 1);
                }
            }
        }
    }

    @Override
    public boolean disposeOfTrashBlocks(ItemStack itemStack) {
        return false;
    }
}

package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.items.base.ItemShovelBase;
import de.melanx.aiotbotania.util.ToolBreakContext;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.common.handler.ModSounds;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;

public class ItemTerraShovel extends ItemShovelBase implements ISequentialBreaker {

    public ItemTerraShovel() {
        this(BotaniaAPI.instance().getTerrasteelItemTier());
    }

    public ItemTerraShovel(Tier tier) {
        super(tier, (int) (ItemTerraSteelAIOT.DAMAGE / 2f), -2, ItemTerraSteelAIOT.MANA_PER_DAMAGE);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemTerraSteelAIOT.setEnabled(stack, !ItemTerraSteelAIOT.isEnabled(stack));
        if (!level.isClientSide) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.terraPickMode, SoundSource.PLAYERS, 0.5F, 0.4F);
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        BlockHitResult hitResult = ToolCommons.raytraceFromEntity(player, 10.0D, false);
        if (!player.level.isClientSide && hitResult.getType() == HitResult.Type.BLOCK) {
            Direction face = hitResult.getDirection();
            this.breakOtherBlock(player, stack, pos, pos, face);
            BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
        }

        return false;
    }

    @Override
    public void breakOtherBlock(Player player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (ItemTerraSteelAIOT.isEnabled(stack)) {
            Level level = player.level;
            BlockState state = level.getBlockState(pos);
            if (this.isCorrectToolForDrops(stack, state)) {
                if (!level.isEmptyBlock(pos)) {
                    ToolUtil.removeBlocksInRange(
                            new ToolBreakContext(player, pos, this.getTier()),
                            side,
                            1,
                            blockState -> (!state.requiresCorrectToolForDrops() || stack.isCorrectToolForDrops(state))
                                    && (stack.getDestroySpeed(state) > 1.0F) || state.is(BlockTags.MINEABLE_WITH_SHOVEL));
                }
            }
        }
    }
}

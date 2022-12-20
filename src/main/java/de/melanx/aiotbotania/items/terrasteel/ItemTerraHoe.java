package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.handler.BotaniaSounds;

import javax.annotation.Nonnull;

public class ItemTerraHoe extends ItemHoeBase {

    public ItemTerraHoe() {
        this(BotaniaAPI.instance().getTerrasteelItemTier());
    }

    public ItemTerraHoe(Tier tier) {
        super(tier, -2, ItemTerraSteelAIOT.MANA_PER_DAMAGE, true, false);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemTerraSteelAIOT.setEnabled(stack, !ItemTerraSteelAIOT.isEnabled(stack));
        if (!level.isClientSide) {
            level.playSound(player, player.getX(), player.getY(), player.getZ(), BotaniaSounds.terraPickMode, SoundSource.PLAYERS, 0.5F, 0.4F);
        }

        return InteractionResultHolder.success(stack);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        BlockState originalState = context.getLevel().getBlockState(context.getClickedPos());
        BlockState modifiedState = ForgeEventFactory.onToolUse(originalState, context, ToolActions.HOE_TILL, false);
        if (originalState == modifiedState || modifiedState == null) {
            return InteractionResult.FAIL;
        }

        if (ItemTerraSteelAIOT.isEnabled(context.getItemInHand())) {
            return ToolUtil.hoeUseAOE(context, this.special, this.lowTier, 1);
        } else {
            return ToolUtil.hoeUse(context, this.special, this.lowTier);
        }
    }
}

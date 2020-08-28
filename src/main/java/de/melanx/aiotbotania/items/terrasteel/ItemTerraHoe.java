package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.core.handler.ModSounds;

import javax.annotation.Nonnull;

public class ItemTerraHoe extends ItemHoeBase {

    public ItemTerraHoe() {
        super(BotaniaAPI.instance().getTerrasteelItemTier(), 15, ItemTerraSteelAIOT.MANA_PER_DAMAGE, true, false);
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

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        if (ItemTerraSteelAIOT.isEnabled(ctx.getItem())) {
            return ToolUtil.hoeUseAOE(ctx, special, low_tier, MANA_PER_DAMAGE, 1);
        } else {
            return ToolUtil.hoeUse(ctx, special, low_tier, MANA_PER_DAMAGE);
        }
    }
}

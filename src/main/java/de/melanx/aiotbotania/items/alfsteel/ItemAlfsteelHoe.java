package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraHoe;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemAlfsteelHoe extends ItemTerraHoe implements MythicBotany, ModPylonRepairable {

    public ItemAlfsteelHoe() {
        super(ItemTiers.ALFSTEEL_ITEM_TIER);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext ctx) {
        if (ItemTerraSteelAIOT.isEnabled(ctx.getItemInHand())) {
            return ToolUtil.hoeUseAOE(ctx, this.special, this.low_tier, 2);
        } else {
            return ToolUtil.hoeUse(ctx, this.special, this.low_tier);
        }
    }

    @Override
    public boolean canRepairPylon(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public int getRepairManaPerTick(ItemStack stack) {
        return (int) (2.5 * this.MANA_PER_DAMAGE);
    }

    @Override
    public ItemStack repairOneTick(ItemStack stack) {
        stack.setDamageValue(Math.max(0, stack.getDamageValue() - 5));
        return stack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (!ModList.get().isLoaded("mythicbotany")) {
            tooltip.add(new TranslatableComponent(AIOTBotania.MODID + ".mythicbotany.disabled").withStyle(ChatFormatting.DARK_RED));
        } else {
            super.appendHoverText(stack, level, tooltip, flag);
        }
    }
}

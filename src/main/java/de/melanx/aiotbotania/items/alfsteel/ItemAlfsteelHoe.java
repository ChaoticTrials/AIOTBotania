package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraHoe;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        if (ItemTerraSteelAIOT.isEnabled(ctx.getItem())) {
            return ToolUtil.hoeUseAOE(ctx, this.special, this.low_tier, 2);
        } else {
            return ToolUtil.hoeUse(ctx, this.special, this.low_tier);
        }
    }

    @Override
    public boolean canRepairPylon(ItemStack stack) {
        return stack.getDamage() > 0;
    }

    @Override
    public int getRepairManaPerTick(ItemStack stack) {
        return (int) (2.5 * this.MANA_PER_DAMAGE);
    }

    @Override
    public ItemStack repairOneTick(ItemStack stack) {
        stack.setDamage(Math.max(0, stack.getDamage() - 5));
        return stack;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        if (!ModList.get().isLoaded("mythicbotany")) {
            tooltip.add(new TranslationTextComponent(AIOTBotania.MODID + ".mythicbotany.disabled").mergeStyle(TextFormatting.DARK_RED));
        } else {
            super.addInformation(stack, world, tooltip, flag);
        }
    }
}

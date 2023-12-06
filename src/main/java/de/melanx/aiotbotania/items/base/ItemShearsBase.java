package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ItemShearsBase extends ShearsItem implements CustomDamageItem {

    protected final int manaPerDamage;

    public ItemShearsBase(int manaPerDamage, int durability) {
        super(new Item.Properties().stacksTo(1).defaultDurability(durability));
        this.manaPerDamage = manaPerDamage;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.manaPerDamage);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slot, boolean isSelected) {
        ToolUtil.inventoryTick(stack, level, entity, this.manaPerDamage);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SILK_TOUCH) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}

package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import vazkii.botania.common.item.equipment.ICustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ItemSwordBase extends SwordItem implements ICustomDamageItem {

    protected final int manaPerDamage;

    public ItemSwordBase(Tier tier, int damage, float speed, int manaPerDamage) {
        super(tier, damage, speed, new Item.Properties().tab(AIOTBotania.instance.getTab()));
        this.manaPerDamage = manaPerDamage;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slot, boolean isSelected) {
        ToolUtil.inventoryTick(stack, level, entity, this.manaPerDamage);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.manaPerDamage);
    }
}

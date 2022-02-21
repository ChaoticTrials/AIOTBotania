package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import vazkii.botania.common.item.equipment.ICustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.function.Consumer;

public class ItemShovelBase extends ShovelItem implements ICustomDamageItem {

    protected final int MANA_PER_DAMAGE;

    public ItemShovelBase(Tier mat, int damage, float speed, int MANA_PER_DAMAGE) {
        super(mat, damage, speed, new Item.Properties().tab(AIOTBotania.instance.getTab()));

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        ToolUtil.inventoryTick(stack, level, entity, this.MANA_PER_DAMAGE);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.MANA_PER_DAMAGE);
    }
}

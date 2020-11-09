package de.melanx.aiotbotania.items.alfsteel;

import net.minecraft.item.ItemStack;

public interface ModPylonRepairable {
    int getRepairManaPerTick(ItemStack var1);

    default boolean canRepairPylon(ItemStack stack) {
        return stack.getDamage() > 0;
    }

    default ItemStack repairOneTick(ItemStack stack) {
        if (stack.getDamage() > 0) {
            stack.setDamage(Math.max(0, stack.getDamage() - 1));
        }
        return stack;
    }
}

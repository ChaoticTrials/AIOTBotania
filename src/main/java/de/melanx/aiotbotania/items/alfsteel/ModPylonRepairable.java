package de.melanx.aiotbotania.items.alfsteel;

import net.minecraft.world.item.ItemStack;

public interface ModPylonRepairable {

    int getRepairManaPerTick(ItemStack stack);

    default boolean canRepairPylon(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    default ItemStack repairOneTick(ItemStack stack) {
        if (stack.getDamageValue() > 0) {
            stack.setDamageValue(Math.max(0, stack.getDamageValue() - 1));
        }

        return stack;
    }
}

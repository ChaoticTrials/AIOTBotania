package de.melanx.aiotbotania.items.alfsteel;

import mythicbotany.pylon.PylonRepairable;
import mythicbotany.pylon.PylonRepairables;
import net.minecraft.item.ItemStack;

public class Bla {
    public static Runnable pylonRepairable() {
        return () -> {
            PylonRepairable pylonRepairable = new PylonRepairable() {
                @Override
                public int getRepairManaPerTick(ItemStack stack) {
                    return stack.getItem() instanceof ModPylonRepairable ? ((ModPylonRepairable) stack.getItem()).getRepairManaPerTick(stack) : 0;
                }

                @Override
                public boolean canRepairPylon(ItemStack stack) {
                    return stack.getItem() instanceof ModPylonRepairable && ((ModPylonRepairable) stack.getItem()).canRepairPylon(stack);
                }

                @Override
                public ItemStack repairOneTick(ItemStack stack) {
                    return stack.getItem() instanceof ModPylonRepairable ? ((ModPylonRepairable) stack.getItem()).repairOneTick(stack) : stack;
                }
            };

            PylonRepairables.register(pylonRepairable, PylonRepairables.PRIORITY_ITEM_WITH_INTERFACE);
        };
    }
}

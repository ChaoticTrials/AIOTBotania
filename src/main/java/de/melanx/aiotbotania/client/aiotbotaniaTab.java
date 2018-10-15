package de.melanx.aiotbotania.client;

import de.melanx.aiotbotania.config.values.ConfigBoolValues;
import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.lib.LibMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class aiotbotaniaTab extends CreativeTabs {

    public aiotbotaniaTab() {
        super(LibMisc.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        if (ConfigBoolValues.MANASTEEL_AIOT.isEnabled())
            return new ItemStack(ModItems.manaaiot);
        if (!ConfigBoolValues.MANASTEEL_AIOT.isEnabled()) {
            if (ConfigBoolValues.ELEMENTIUM_AIOT.isEnabled())
                return new ItemStack(ModItems.elementiumaiot);
            else
                return new ItemStack(ModItems.elementiumhoe);
        }
        return getTabIconItem();
    }

}

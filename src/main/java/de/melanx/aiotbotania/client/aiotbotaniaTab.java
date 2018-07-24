package de.melanx.aiotbotania.client;

import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.lib.LibMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class aiotbotaniaTab extends CreativeTabs {

    private static final String modID = LibMisc.NAME;

    public aiotbotaniaTab() {
        super(LibMisc.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.manaaiot);
    }

}

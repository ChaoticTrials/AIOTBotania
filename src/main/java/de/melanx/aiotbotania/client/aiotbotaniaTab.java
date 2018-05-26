package de.melanx.aiotbotania.client;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class aiotbotaniaTab extends CreativeTabs {

    private static final String modID = "MelanX Tweaks";

    public aiotbotaniaTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.manahoe);
    }

}

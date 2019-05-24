package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.lib.LibMisc;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTab extends ItemGroup {

    public CreativeTab() {
        super(LibMisc.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.manasteel_hoe);
    }

}

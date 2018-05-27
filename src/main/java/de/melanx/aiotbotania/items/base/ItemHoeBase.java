package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.item.ItemHoe;

public class ItemHoeBase extends ItemHoe {

    public ItemHoeBase(String name, ToolMaterial mat) {
        super(mat);
        ModItems.registerItem(this, name);
        ModItems.registerModel(this);
    }
}

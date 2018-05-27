package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.item.ItemTool;

import java.util.HashSet;

public class ItemAIOTBase extends ItemTool {

    public ItemAIOTBase(String name, ToolMaterial mat, float attackDamage, float attackSpeed) {
        super(attackDamage, attackSpeed, mat, new HashSet<>());
        ModItems.registerItem(this, name);
        ModItems.registerModel(this);
    }
}

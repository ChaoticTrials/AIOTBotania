package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.Registry;
import net.minecraft.item.ItemTool;

import java.util.HashSet;

public class ItemAIOTBase extends ItemTool {

    public ItemAIOTBase(String name, ToolMaterial mat, float attackDamage, float attackSpeed) {
        super(attackDamage, attackSpeed, mat, new HashSet<>());
        Registry.registerItem(this, name);
        Registry.registerModel(this);
    }
}

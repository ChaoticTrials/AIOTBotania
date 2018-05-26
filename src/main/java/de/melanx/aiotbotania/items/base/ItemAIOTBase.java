package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.HashSet;
import java.util.Objects;

public class ItemAIOTBase extends ItemTool {

    public ItemAIOTBase(String name, ToolMaterial mat, float attackDamage, float attackSpeed) {
        super(attackDamage, attackSpeed, mat, new HashSet<>());
        ModItems.registerItem(this, name);
        registerItemModel();
    }

    private void registerItemModel() {
        ModItems.MODEL_LOCATIONS.put(new ItemStack(this), new ModelResourceLocation(Objects.requireNonNull(this.getRegistryName()), "inventory"));
    }
}

package de.melanx.aiotbotania.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record ToolSet(Item sword, Item axe, Item pickaxe, Item shovel, Item hoe) {

    public boolean testSword(ItemStack stack) {
        return Ingredient.of(this.sword).test(stack);
    }

    public boolean testAxe(ItemStack stack) {
        return Ingredient.of(this.axe).test(stack);
    }

    public boolean testPickaxe(ItemStack stack) {
        return Ingredient.of(this.pickaxe).test(stack);
    }

    public boolean testShovel(ItemStack stack) {
        return Ingredient.of(this.shovel).test(stack);
    }

    public boolean testHoe(ItemStack stack) {
        return Ingredient.of(this.hoe).test(stack);
    }
}

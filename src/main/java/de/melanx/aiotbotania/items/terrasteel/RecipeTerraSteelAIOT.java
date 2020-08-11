package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nonnull;

public class RecipeTerraSteelAIOT extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_SWORD = Ingredient.fromItems(ModItems.terraSword);
    private static final Ingredient INGREDIENT_AXE = Ingredient.fromItems(ModItems.terraAxe);
    private static final Ingredient INGREDIENT_PICK = Ingredient.fromItems(ModItems.terraPick);

    public RecipeTerraSteelAIOT(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, new ItemStack(Registration.terrasteel_aiot.get()), NonNullList.from(Ingredient.fromItems(Blocks.BARRIER),
                Ingredient.fromItems(ModItems.terraSword), Ingredient.fromItems(ModItems.terraAxe), Ingredient.fromItems(ModItems.terraPick)));
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }

    public boolean matches(CraftingInventory inv, @Nonnull World worldIn) {
        boolean foundSword = false;
        boolean foundAxe = false;
        boolean foundPick = false;
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stack = inv.getStackInSlot(j);
            if (!stack.isEmpty()) {
                if (INGREDIENT_SWORD.test(stack) && !foundSword) {
                    foundSword = true;
                } else if (INGREDIENT_AXE.test(stack) && !foundAxe) {
                    foundAxe = true;
                } else if (INGREDIENT_PICK.test(stack) && !foundPick) {
                    foundPick = true;
                } else {
                    return false;
                }
            }
        }
        return foundSword && foundAxe && foundPick;
    }

    @Nonnull
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack ingredient = inv.getStackInSlot(j);
            if (!ingredient.isEmpty() && INGREDIENT_PICK.test(ingredient)) {
                stack.setTag(ingredient.getOrCreateTag().copy());
            }
        }
        return stack;
    }

    public boolean canFit(int width, int height) {
        return width * height >= 3;
    }
}

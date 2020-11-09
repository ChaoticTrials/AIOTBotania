package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.core.Registration;
import mythicbotany.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class RecipeAlfsteelAIOT extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_SWORD = Ingredient.fromItems(ModItems.alfsteelSword);
    private static final Ingredient INGREDIENT_AXE = Ingredient.fromItems(ModItems.alfsteelAxe);
    private static final Ingredient INGREDIENT_PICK = Ingredient.fromItems(ModItems.alfsteelPick);
    private static final Ingredient INGREDIENT_SHOVEL = Ingredient.fromItems(Registration.alfsteel_shovel.get());
    private static final Ingredient INGREDIENT_HOE = Ingredient.fromItems(Registration.alfsteel_hoe.get());

    public RecipeAlfsteelAIOT(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, new ItemStack(Registration.alfsteel_aiot.get()), NonNullList.from(Ingredient.fromItems(Blocks.BARRIER),
                Ingredient.fromItems(ModItems.alfsteelSword), Ingredient.fromItems(ModItems.alfsteelAxe), Ingredient.fromItems(ModItems.alfsteelPick),
                Ingredient.fromItems(Registration.alfsteel_shovel.get()), Ingredient.fromItems(Registration.alfsteel_hoe.get())));
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
        boolean foundShovel = false;
        boolean foundHoe = false;
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stack = inv.getStackInSlot(j);
            if (!stack.isEmpty()) {
                if (INGREDIENT_SWORD.test(stack) && !foundSword) {
                    foundSword = true;
                } else if (INGREDIENT_AXE.test(stack) && !foundAxe) {
                    foundAxe = true;
                } else if (INGREDIENT_PICK.test(stack) && !foundPick) {
                    foundPick = true;
                } else if (INGREDIENT_SHOVEL.test(stack) && !foundShovel) {
                    foundShovel = true;
                } else if (INGREDIENT_HOE.test(stack) && !foundHoe) {
                    foundHoe = true;
                } else {
                    return false;
                }
            }
        }
        return foundSword && foundAxe && foundPick && foundShovel && foundHoe;
    }

    @Nonnull
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
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

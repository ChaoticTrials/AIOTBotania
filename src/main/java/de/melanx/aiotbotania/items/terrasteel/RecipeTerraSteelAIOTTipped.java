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
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nonnull;

public class RecipeTerraSteelAIOTTipped extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_TERRA = Ingredient.fromItems(Registration.terrasteel_aiot.get());
    private static final Ingredient INGREDIENT_ELEMENTIUM = Ingredient.fromItems(ModItems.elementiumPick);

    public RecipeTerraSteelAIOTTipped(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, new ItemStack(Registration.terrasteel_aiot.get()), NonNullList.from(Ingredient.fromItems(Blocks.BARRIER),
                Ingredient.fromItems(Registration.terrasteel_aiot.get()), Ingredient.fromItems(ModItems.elementiumPick)));
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }

    public boolean matches(CraftingInventory inv, @Nonnull World worldIn) {
        boolean foundTerra = false;
        boolean foundElementium = false;

        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stack = inv.getStackInSlot(j);
            if (!stack.isEmpty()) {
                if (INGREDIENT_TERRA.test(stack) && !foundTerra && !ItemNBTHelper.getBoolean(stack, "tipped", false)) {
                    foundTerra = true;
                } else if (INGREDIENT_ELEMENTIUM.test(stack) && !foundElementium) {
                    foundElementium = true;
                } else {
                    return false;
                }
            }
        }

        return foundTerra && foundElementium;
    }

    @Nonnull
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());

        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack ingredient = inv.getStackInSlot(j);
            if (!ingredient.isEmpty() && INGREDIENT_TERRA.test(ingredient)) {
                stack.setTag(ingredient.getOrCreateTag().copy());
            }
        }

        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }

    public boolean canFit(int width, int height) {
        return width * height >= 3;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }
}

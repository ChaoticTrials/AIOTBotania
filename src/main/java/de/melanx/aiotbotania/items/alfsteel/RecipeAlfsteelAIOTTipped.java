package de.melanx.aiotbotania.items.alfsteel;

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

public class RecipeAlfsteelAIOTTipped extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_ALFSTEEL = Ingredient.fromItems(Registration.alfsteel_aiot.get());
    private static final Ingredient INGREDIENT_ELEMENTIUM = Ingredient.fromItems(ModItems.elementiumPick);

    public RecipeAlfsteelAIOTTipped(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, new ItemStack(Registration.alfsteel_aiot.get()), NonNullList.from(Ingredient.fromItems(Blocks.BARRIER),
                Ingredient.fromItems(Registration.alfsteel_aiot.get()), Ingredient.fromItems(ModItems.elementiumPick)));
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }

    public boolean matches(CraftingInventory inv, @Nonnull World worldIn) {
        boolean foundAlfsteel = false;
        boolean foundElementium = false;
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stack = inv.getStackInSlot(j);
            if (!stack.isEmpty()) {
                if (INGREDIENT_ALFSTEEL.test(stack) && !foundAlfsteel && !ItemNBTHelper.getBoolean(stack, "tipped", false)) {
                    foundAlfsteel = true;
                } else if (INGREDIENT_ELEMENTIUM.test(stack) && !foundElementium) {
                    foundElementium = true;
                } else {
                    return false;
                }
            }
        }
        return foundAlfsteel && foundElementium;
    }

    @Nonnull
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack ingredient = inv.getStackInSlot(j);
            if (!ingredient.isEmpty() && INGREDIENT_ALFSTEEL.test(ingredient)) {
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
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }
}

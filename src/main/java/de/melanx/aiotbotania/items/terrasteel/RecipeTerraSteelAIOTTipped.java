package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nonnull;

public class RecipeTerraSteelAIOTTipped extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_TERRA = Ingredient.of(Registration.terrasteel_aiot.get());
    private static final Ingredient INGREDIENT_ELEMENTIUM = Ingredient.of(ModItems.elementiumPick);

    public RecipeTerraSteelAIOTTipped(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, new ItemStack(Registration.terrasteel_aiot.get()), NonNullList.of(Ingredient.of(Blocks.BARRIER),
                Ingredient.of(Registration.terrasteel_aiot.get()), Ingredient.of(ModItems.elementiumPick)));
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    public boolean matches(CraftingContainer inv, @Nonnull Level level) {
        boolean foundTerra = false;
        boolean foundElementium = false;
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack stack = inv.getItem(j);
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
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack ingredient = inv.getItem(j);
            if (!ingredient.isEmpty() && INGREDIENT_TERRA.test(ingredient)) {
                stack.setTag(ingredient.getOrCreateTag().copy());
            }
        }
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }
}

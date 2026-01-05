package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import vazkii.botania.common.crafting.recipe.NoOpRecipeSerializer;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.Nonnull;

public class RecipeTerraSteelAIOTTipped extends CustomRecipe {

    public static final NoOpRecipeSerializer<RecipeTerraSteelAIOTTipped> SERIALIZER = new NoOpRecipeSerializer<>(RecipeTerraSteelAIOTTipped::new);

    private static final Ingredient INGREDIENT_TERRA = Ingredient.of(Registration.terrasteel_aiot.get());
    private static final Ingredient INGREDIENT_ELEMENTIUM = Ingredient.of(BotaniaItems.elementiumPick);

    public RecipeTerraSteelAIOTTipped(ResourceLocation id) {
        super(id, CraftingBookCategory.EQUIPMENT);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
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
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer inv, @Nonnull RegistryAccess registryAccess) {
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

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}

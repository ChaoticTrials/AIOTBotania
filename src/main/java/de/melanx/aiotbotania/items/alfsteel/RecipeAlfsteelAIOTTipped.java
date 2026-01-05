package de.melanx.aiotbotania.items.alfsteel;

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

public class RecipeAlfsteelAIOTTipped extends CustomRecipe {

    public static final NoOpRecipeSerializer<RecipeAlfsteelAIOTTipped> SERIALIZER = new NoOpRecipeSerializer<>(RecipeAlfsteelAIOTTipped::new);

    private static final Ingredient INGREDIENT_ALFSTEEL = Ingredient.of(Registration.alfsteel_aiot.get());
    private static final Ingredient INGREDIENT_ELEMENTIUM = Ingredient.of(BotaniaItems.elementiumPick);

    public RecipeAlfsteelAIOTTipped(ResourceLocation id) {
        super(id, CraftingBookCategory.EQUIPMENT);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer inv, @Nonnull Level level) {
        boolean foundAlfsteel = false;
        boolean foundElementium = false;
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack stack = inv.getItem(j);
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
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer craftingContainer, @Nonnull RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        for (int j = 0; j < craftingContainer.getContainerSize(); ++j) {
            ItemStack ingredient = craftingContainer.getItem(j);
            if (!ingredient.isEmpty() && INGREDIENT_ALFSTEEL.test(ingredient)) {
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
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}

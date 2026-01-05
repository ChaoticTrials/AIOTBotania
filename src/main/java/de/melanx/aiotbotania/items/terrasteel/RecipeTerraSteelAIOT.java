package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import vazkii.botania.common.crafting.recipe.NoOpRecipeSerializer;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.Nonnull;

public class RecipeTerraSteelAIOT extends CustomRecipe {

    public static final NoOpRecipeSerializer<RecipeTerraSteelAIOT> SERIALIZER = new NoOpRecipeSerializer<>(RecipeTerraSteelAIOT::new);

    private static final Ingredient INGREDIENT_SWORD = Ingredient.of(BotaniaItems.terraSword);
    private static final Ingredient INGREDIENT_AXE = Ingredient.of(BotaniaItems.terraAxe);
    private static final Ingredient INGREDIENT_PICK = Ingredient.of(BotaniaItems.terraPick);
    private static final Ingredient INGREDIENT_SHOVEL = Ingredient.of(Registration.terrasteel_shovel.get());
    private static final Ingredient INGREDIENT_HOE = Ingredient.of(Registration.terrasteel_hoe.get());

    public RecipeTerraSteelAIOT(ResourceLocation id) {
        super(id, CraftingBookCategory.EQUIPMENT);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public boolean matches(CraftingContainer inv, @Nonnull Level level) {
        boolean foundSword = false;
        boolean foundAxe = false;
        boolean foundPick = false;
        boolean foundShovel = false;
        boolean foundHoe = false;
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack stack = inv.getItem(j);
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
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer inv, @Nonnull RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack ingredient = inv.getItem(j);
            if (!ingredient.isEmpty() && INGREDIENT_PICK.test(ingredient)) {
                stack.setTag(ingredient.getOrCreateTag().copy());
            }
        }

        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}

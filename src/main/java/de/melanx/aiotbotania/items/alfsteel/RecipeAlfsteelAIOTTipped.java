package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.Nonnull;

public class RecipeAlfsteelAIOTTipped extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_ALFSTEEL = Ingredient.of(Registration.alfsteel_aiot.get());
    private static final Ingredient INGREDIENT_ELEMENTIUM = Ingredient.of(BotaniaItems.elementiumPick);

    public RecipeAlfsteelAIOTTipped(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, CraftingBookCategory.EQUIPMENT, new ItemStack(Registration.alfsteel_aiot.get()), NonNullList.of(Ingredient.of(Blocks.BARRIER),
                Ingredient.of(Registration.alfsteel_aiot.get()), Ingredient.of(BotaniaItems.elementiumPick)));
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    public boolean matches(CraftingContainer inv, @Nonnull Level level) {
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
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack ingredient = inv.getItem(j);
            if (!ingredient.isEmpty() && INGREDIENT_ALFSTEEL.test(ingredient)) {
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
    public ItemStack getResultItem(@Nonnull RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        ItemNBTHelper.setBoolean(stack, "tipped", true);
        return stack;
    }
}

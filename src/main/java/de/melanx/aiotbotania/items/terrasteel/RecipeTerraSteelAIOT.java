package de.melanx.aiotbotania.items.terrasteel;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.Nonnull;

public class RecipeTerraSteelAIOT extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_SWORD = Ingredient.of(BotaniaItems.terraSword);
    private static final Ingredient INGREDIENT_AXE = Ingredient.of(BotaniaItems.terraAxe);
    private static final Ingredient INGREDIENT_PICK = Ingredient.of(BotaniaItems.terraPick);
    private static final Ingredient INGREDIENT_SHOVEL = Ingredient.of(Registration.terrasteel_shovel.get());
    private static final Ingredient INGREDIENT_HOE = Ingredient.of(Registration.terrasteel_hoe.get());

    public RecipeTerraSteelAIOT(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, CraftingBookCategory.EQUIPMENT, new ItemStack(Registration.terrasteel_aiot.get()), NonNullList.of(Ingredient.of(Blocks.BARRIER),
                INGREDIENT_SWORD, INGREDIENT_AXE, INGREDIENT_PICK, INGREDIENT_SHOVEL, INGREDIENT_HOE));
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

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
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack stack = new ItemStack(Registration.terrasteel_aiot.get());
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack ingredient = inv.getItem(j);
            if (!ingredient.isEmpty() && INGREDIENT_PICK.test(ingredient)) {
                stack.setTag(ingredient.getOrCreateTag().copy());
            }
        }
        return stack;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }
}

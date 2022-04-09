package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.core.Registration;
import mythicbotany.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;

public class RecipeAlfsteelAIOT extends ShapelessRecipe {

    private static final Ingredient INGREDIENT_SWORD = Ingredient.of(ModItems.alfsteelSword);
    private static final Ingredient INGREDIENT_AXE = Ingredient.of(ModItems.alfsteelAxe);
    private static final Ingredient INGREDIENT_PICK = Ingredient.of(ModItems.alfsteelPick);
    private static final Ingredient INGREDIENT_SHOVEL = Ingredient.of(Registration.alfsteel_shovel.get());
    private static final Ingredient INGREDIENT_HOE = Ingredient.of(Registration.alfsteel_hoe.get());

    public RecipeAlfsteelAIOT(ResourceLocation idIn, String groupIn) {
        super(idIn, groupIn, new ItemStack(Registration.alfsteel_aiot.get()), NonNullList.of(Ingredient.of(Blocks.BARRIER),
                Ingredient.of(ModItems.alfsteelSword), Ingredient.of(ModItems.alfsteelAxe), Ingredient.of(ModItems.alfsteelPick),
                Ingredient.of(Registration.alfsteel_shovel.get()), Ingredient.of(Registration.alfsteel_hoe.get())));
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
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
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

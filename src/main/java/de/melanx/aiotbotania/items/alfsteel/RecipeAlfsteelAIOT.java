package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.compat.MythicBotanyCompat;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.util.ToolSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import vazkii.botania.common.crafting.recipe.NoOpRecipeSerializer;

import javax.annotation.Nonnull;

public class RecipeAlfsteelAIOT extends CustomRecipe {

    public static final NoOpRecipeSerializer<RecipeAlfsteelAIOT> SERIALIZER = new NoOpRecipeSerializer<>(RecipeAlfsteelAIOT::new);
    public static final ToolSet TOOL_SET = MythicBotanyCompat.isLoaded()
            ? MythicBotanyCompat.alfsteelToolset()
            : new ToolSet(Items.WOODEN_SWORD, Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, Items.WOODEN_HOE);

    public RecipeAlfsteelAIOT(ResourceLocation id) {
        super(id, CraftingBookCategory.EQUIPMENT);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer inv, @Nonnull Level level) {
        boolean foundSword = false;
        boolean foundAxe = false;
        boolean foundPick = false;
        boolean foundShovel = false;
        boolean foundHoe = false;

        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack stack = inv.getItem(j);
            if (!stack.isEmpty()) {
                if (TOOL_SET.testSword(stack) && !foundSword) {
                    foundSword = true;
                } else if (TOOL_SET.testAxe(stack) && !foundAxe) {
                    foundAxe = true;
                } else if (TOOL_SET.testPickaxe(stack) && !foundPick) {
                    foundPick = true;
                } else if (TOOL_SET.testShovel(stack) && !foundShovel) {
                    foundShovel = true;
                } else if (TOOL_SET.testHoe(stack) && !foundHoe) {
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
        ItemStack stack = new ItemStack(Registration.alfsteel_aiot.get());
        for (int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack ingredient = inv.getItem(j);
            if (!ingredient.isEmpty() && TOOL_SET.testPickaxe(ingredient)) {
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

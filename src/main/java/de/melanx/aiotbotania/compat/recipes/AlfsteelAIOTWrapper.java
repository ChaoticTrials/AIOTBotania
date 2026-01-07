package de.melanx.aiotbotania.compat.recipes;

import com.google.common.collect.ImmutableList;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOT;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class AlfsteelAIOTWrapper implements ICraftingCategoryExtension {

    private final List<List<ItemStack>> inputs;
    private final ItemStack output;
    private final ResourceLocation name;

    public AlfsteelAIOTWrapper(RecipeAlfsteelAIOT recipe) {
        this.inputs = ImmutableList.of(
                ImmutableList.of(new ItemStack(RecipeAlfsteelAIOT.TOOL_SET.sword())),
                ImmutableList.of(new ItemStack(RecipeAlfsteelAIOT.TOOL_SET.axe())),
                ImmutableList.of(new ItemStack(RecipeAlfsteelAIOT.TOOL_SET.pickaxe())),
                ImmutableList.of(new ItemStack(RecipeAlfsteelAIOT.TOOL_SET.shovel())),
                ImmutableList.of(new ItemStack(RecipeAlfsteelAIOT.TOOL_SET.hoe()))
        );
        this.output = new ItemStack(Registration.alfsteel_aiot.get());
        this.name = recipe.getId();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ICraftingGridHelper helper, @Nonnull IFocusGroup focuses) {
        helper.createAndSetInputs(builder, this.inputs, 0, 0);
        helper.createAndSetOutputs(builder, Collections.singletonList(this.output));
    }

    @Override
    public @Nullable ResourceLocation getRegistryName() {
        return this.name;
    }
}

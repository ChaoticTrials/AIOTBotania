package de.melanx.aiotbotania.core.handler.data;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.*;
import net.minecraft.util.IItemProvider;
import vazkii.botania.common.block.ModBlocks;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        getShears(Registration.livingwood_shears.get(), ModBlocks.livingwood).build(consumer);
        getShears(Registration.livingrock_shears.get(), ModBlocks.livingrock).build(consumer);

        getSwords(Registration.livingwood_sword.get(), ModBlocks.livingwood).build(consumer);
        getSwords(Registration.livingrock_sword.get(), ModBlocks.livingrock).build(consumer);

        getAxes(Registration.livingwood_axe.get(), ModBlocks.livingwood).build(consumer);
        getAxes(Registration.livingrock_axe.get(), ModBlocks.livingrock).build(consumer);

        getPickaxes(Registration.livingwood_pickaxe.get(), ModBlocks.livingwood).build(consumer);
        getPickaxes(Registration.livingrock_pickaxe.get(), ModBlocks.livingrock).build(consumer);

        getShovels(Registration.livingwood_shovel.get(), ModBlocks.livingwood).build(consumer);
        getShovels(Registration.livingrock_shovel.get(), ModBlocks.livingrock).build(consumer);

        getHoes(Registration.livingwood_hoe.get(), ModBlocks.livingwood, vazkii.botania.common.item.ModItems.livingwoodTwig).build(consumer);
        getHoes(Registration.livingrock_hoe.get(), ModBlocks.livingrock, vazkii.botania.common.item.ModItems.livingwoodTwig).build(consumer);
        getHoes(Registration.manasteel_hoe.get(), vazkii.botania.common.item.ModItems.manaSteel, vazkii.botania.common.item.ModItems.livingwoodTwig).build(consumer);
        getHoes(Registration.elementium_hoe.get(), vazkii.botania.common.item.ModItems.elementium, vazkii.botania.common.item.ModItems.dreamwoodTwig).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Registration.livingwood_aiot.get())
                .addIngredient(Registration.livingwood_sword.get())
                .addIngredient(Registration.livingwood_axe.get())
                .addIngredient(Registration.livingwood_pickaxe.get())
                .addIngredient(Registration.livingwood_shovel.get())
                .addIngredient(Registration.livingwood_hoe.get())
                .addCriterion("has_sword", hasItem(Registration.livingwood_sword.get()))
                .addCriterion("has_axe", hasItem(Registration.livingwood_axe.get()))
                .addCriterion("has_shovel", hasItem(Registration.livingwood_shovel.get()))
                .addCriterion("has_pickaxe", hasItem(Registration.livingwood_pickaxe.get()))
                .addCriterion("has_hoe", hasItem(Registration.livingwood_hoe.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Registration.livingrock_aiot.get())
                .addIngredient(Registration.livingrock_sword.get())
                .addIngredient(Registration.livingrock_axe.get())
                .addIngredient(Registration.livingrock_pickaxe.get())
                .addIngredient(Registration.livingrock_shovel.get())
                .addIngredient(Registration.livingrock_hoe.get())
                .addCriterion("has_sword", hasItem(Registration.livingrock_sword.get()))
                .addCriterion("has_axe", hasItem(Registration.livingrock_axe.get()))
                .addCriterion("has_pickaxe", hasItem(Registration.livingrock_pickaxe.get()))
                .addCriterion("has_shovel", hasItem(Registration.livingrock_shovel.get()))
                .addCriterion("has_hoe", hasItem(Registration.livingrock_hoe.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Registration.manasteel_aiot.get())
                .addIngredient(vazkii.botania.common.item.ModItems.manasteelSword)
                .addIngredient(vazkii.botania.common.item.ModItems.manasteelAxe)
                .addIngredient(vazkii.botania.common.item.ModItems.manasteelPick)
                .addIngredient(vazkii.botania.common.item.ModItems.manasteelShovel)
                .addIngredient(Registration.manasteel_hoe.get())
                .addCriterion("has_sword", hasItem(vazkii.botania.common.item.ModItems.manasteelSword))
                .addCriterion("has_axe", hasItem(vazkii.botania.common.item.ModItems.manasteelAxe))
                .addCriterion("has_pickaxe", hasItem(vazkii.botania.common.item.ModItems.manasteelPick))
                .addCriterion("has_shovel", hasItem(vazkii.botania.common.item.ModItems.manasteelShovel))
                .addCriterion("has_hoe", hasItem(Registration.manasteel_hoe.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Registration.elementium_aiot.get())
                .addIngredient(vazkii.botania.common.item.ModItems.elementiumSword)
                .addIngredient(vazkii.botania.common.item.ModItems.elementiumAxe)
                .addIngredient(vazkii.botania.common.item.ModItems.elementiumPick)
                .addIngredient(vazkii.botania.common.item.ModItems.elementiumShovel)
                .addIngredient(Registration.elementium_hoe.get())
                .addCriterion("has_sword", hasItem(vazkii.botania.common.item.ModItems.elementiumSword))
                .addCriterion("has_axe", hasItem(vazkii.botania.common.item.ModItems.elementiumAxe))
                .addCriterion("has_pickaxe", hasItem(vazkii.botania.common.item.ModItems.elementiumPick))
                .addCriterion("has_shovel", hasItem(vazkii.botania.common.item.ModItems.elementiumShovel))
                .addCriterion("has_hoe", hasItem(Registration.elementium_hoe.get()))
                .build(consumer);
    }

    private ShapedRecipeBuilder getShears(IItemProvider result, IItemProvider material) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .patternLine(" M")
                .patternLine("M ")
                .addCriterion("has_material", hasItem(material));
    }

    private ShapedRecipeBuilder getSwords(IItemProvider result, IItemProvider material) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', vazkii.botania.common.item.ModItems.livingwoodTwig)
                .patternLine("M")
                .patternLine("M")
                .patternLine("T")
                .addCriterion("has_material", hasItem(material));
    }

    private ShapedRecipeBuilder getAxes(IItemProvider result, IItemProvider material) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', vazkii.botania.common.item.ModItems.livingwoodTwig)
                .patternLine("MM")
                .patternLine("MT")
                .patternLine(" T")
                .addCriterion("has_material", hasItem(material));
    }

    private ShapedRecipeBuilder getPickaxes(IItemProvider result, IItemProvider material) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', vazkii.botania.common.item.ModItems.livingwoodTwig)
                .patternLine("MMM")
                .patternLine(" T ")
                .patternLine(" T ")
                .addCriterion("has_material", hasItem(material));
    }

    private ShapedRecipeBuilder getShovels(IItemProvider result, IItemProvider material) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', vazkii.botania.common.item.ModItems.livingwoodTwig)
                .patternLine("M")
                .patternLine("T")
                .patternLine("T")
                .addCriterion("has_material", hasItem(material));
    }

    private ShapedRecipeBuilder getHoes(IItemProvider result, IItemProvider material, IItemProvider twig) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', twig)
                .patternLine("MM")
                .patternLine(" T")
                .patternLine(" T")
                .addCriterion("has_material", hasItem(material));
    }
}

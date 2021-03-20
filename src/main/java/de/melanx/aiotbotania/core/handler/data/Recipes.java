package de.melanx.aiotbotania.core.handler.data;

import com.google.gson.JsonArray;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.crafting.MythicBotanyCondition;
import de.melanx.aiotbotania.core.crafting.TerrasteelCondition;
import net.minecraft.data.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.data.recipes.WrapperResult;

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
        getShovels(Registration.terrasteel_shovel.get(), ModItems.terrasteel).build(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(TerrasteelCondition.SERIALIZER.getJson(new TerrasteelCondition(true)));
            json.add("conditions", array);
        }));
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Registration.terrasteel_shovel.get()),
                Ingredient.fromItems(mythicbotany.ModItems.alfsteelIngot),
                Registration.alfsteel_shovel.get())
                .addCriterion("has_item", hasItem(Registration.terrasteel_hoe.get()))
                .build(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(MythicBotanyCondition.SERIALIZER.getJson(new MythicBotanyCondition(true)));
            json.add("conditions", array);
        }), new ResourceLocation(AIOTBotania.MODID, "smithing/alfsteel_shovel"));

        getHoes(Registration.livingwood_hoe.get(), ModBlocks.livingwood).build(consumer);
        getHoes(Registration.livingrock_hoe.get(), ModBlocks.livingrock).build(consumer);
        getHoes(Registration.terrasteel_hoe.get(), ModItems.terrasteel).build(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(TerrasteelCondition.SERIALIZER.getJson(new TerrasteelCondition(true)));
            json.add("conditions", array);
        }));
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Registration.terrasteel_hoe.get()),
                Ingredient.fromItems(mythicbotany.ModItems.alfsteelIngot),
                Registration.alfsteel_hoe.get())
                .addCriterion("has_item", hasItem(Registration.terrasteel_hoe.get()))
                .build(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(MythicBotanyCondition.SERIALIZER.getJson(new MythicBotanyCondition(true)));
            json.add("conditions", array);
        }), new ResourceLocation(AIOTBotania.MODID, "smithing/alfsteel_hoe"));

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
                .addIngredient(ModItems.manasteelSword)
                .addIngredient(ModItems.manasteelAxe)
                .addIngredient(ModItems.manasteelPick)
                .addIngredient(ModItems.manasteelShovel)
                .addIngredient(ModItems.manasteelHoe)
                .addCriterion("has_sword", hasItem(ModItems.manasteelSword))
                .addCriterion("has_axe", hasItem(ModItems.manasteelAxe))
                .addCriterion("has_pickaxe", hasItem(ModItems.manasteelPick))
                .addCriterion("has_shovel", hasItem(ModItems.manasteelShovel))
                .addCriterion("has_hoe", hasItem(ModItems.manasteelHoe))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Registration.elementium_aiot.get())
                .addIngredient(ModItems.elementiumSword)
                .addIngredient(ModItems.elementiumAxe)
                .addIngredient(ModItems.elementiumPick)
                .addIngredient(ModItems.elementiumShovel)
                .addIngredient(ModItems.elementiumHoe)
                .addCriterion("has_sword", hasItem(ModItems.elementiumSword))
                .addCriterion("has_axe", hasItem(ModItems.elementiumAxe))
                .addCriterion("has_pickaxe", hasItem(ModItems.elementiumPick))
                .addCriterion("has_shovel", hasItem(ModItems.elementiumShovel))
                .addCriterion("has_hoe", hasItem(ModItems.elementiumHoe))
                .build(consumer);
    }

    private static ShapedRecipeBuilder getShears(IItemProvider result, IItemProvider material) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .patternLine(" M")
                .patternLine("M ")
                .addCriterion("has_material", hasItem(material));
    }

    private static ShapedRecipeBuilder getSwords(IItemProvider result, IItemProvider material) {
        return getSwords(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getSwords(IItemProvider result, IItemProvider material, IItemProvider twig) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', twig)
                .patternLine("M")
                .patternLine("M")
                .patternLine("T")
                .addCriterion("has_material", hasItem(material));
    }

    private static ShapedRecipeBuilder getAxes(IItemProvider result, IItemProvider material) {
        return getAxes(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getAxes(IItemProvider result, IItemProvider material, IItemProvider twig) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', twig)
                .patternLine("MM")
                .patternLine("MT")
                .patternLine(" T")
                .addCriterion("has_material", hasItem(material));
    }

    private static ShapedRecipeBuilder getPickaxes(IItemProvider result, IItemProvider material) {
        return getPickaxes(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getPickaxes(IItemProvider result, IItemProvider material, IItemProvider twig) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', twig)
                .patternLine("MMM")
                .patternLine(" T ")
                .patternLine(" T ")
                .addCriterion("has_material", hasItem(material));
    }

    private static ShapedRecipeBuilder getShovels(IItemProvider result, IItemProvider material) {
        return getShovels(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getShovels(IItemProvider result, IItemProvider material, IItemProvider twig) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', twig)
                .patternLine("M")
                .patternLine("T")
                .patternLine("T")
                .addCriterion("has_material", hasItem(material));
    }

    private static ShapedRecipeBuilder getHoes(IItemProvider result, IItemProvider material) {
        return getHoes(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getHoes(IItemProvider result, IItemProvider material, IItemProvider twig) {
        return ShapedRecipeBuilder.shapedRecipe(result)
                .key('M', material)
                .key('T', twig)
                .patternLine("MM")
                .patternLine(" T")
                .patternLine(" T")
                .addCriterion("has_material", hasItem(material));
    }
}

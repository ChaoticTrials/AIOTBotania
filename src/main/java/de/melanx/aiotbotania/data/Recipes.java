package de.melanx.aiotbotania.data;

import com.google.gson.JsonArray;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.crafting.TerrasteelCondition;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.data.recipes.WrapperResult;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

@SuppressWarnings("SameParameterValue")
public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        getShears(Registration.livingwood_shears.get(), ModBlocks.livingwood).save(consumer);
        getShears(Registration.livingrock_shears.get(), ModBlocks.livingrock).save(consumer);

        getSwords(Registration.livingwood_sword.get(), ModBlocks.livingwood).save(consumer);
        getSwords(Registration.livingrock_sword.get(), ModBlocks.livingrock).save(consumer);

        getAxes(Registration.livingwood_axe.get(), ModBlocks.livingwood).save(consumer);
        getAxes(Registration.livingrock_axe.get(), ModBlocks.livingrock).save(consumer);

        getPickaxes(Registration.livingwood_pickaxe.get(), ModBlocks.livingwood).save(consumer);
        getPickaxes(Registration.livingrock_pickaxe.get(), ModBlocks.livingrock).save(consumer);

        getShovels(Registration.livingwood_shovel.get(), ModBlocks.livingwood).save(consumer);
        getShovels(Registration.livingrock_shovel.get(), ModBlocks.livingrock).save(consumer);
        getShovels(Registration.terrasteel_shovel.get(), ModItems.terrasteel).save(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(TerrasteelCondition.SERIALIZER.getJson(new TerrasteelCondition()));
            json.add("conditions", array);
        }));
//        UpgradeRecipeBuilder.smithing(Ingredient.of(Registration.terrasteel_shovel.get()),
//                Ingredient.of(mythicbotany.ModItems.alfsteelIngot),
//                Registration.alfsteel_shovel.get())
//                .unlocks("has_item", has(Registration.terrasteel_hoe.get()))
//                .save(WrapperResult.transformJson(consumer, json -> {
//            JsonArray array = new JsonArray();
//            array.add(MythicBotanyCondition.SERIALIZER.getJson(new MythicBotanyCondition(true)));
//            json.add("conditions", array);
//        }), new ResourceLocation(AIOTBotania.MODID, "smithing/alfsteel_shovel"));

        getHoes(Registration.livingwood_hoe.get(), ModBlocks.livingwood).save(consumer);
        getHoes(Registration.livingrock_hoe.get(), ModBlocks.livingrock).save(consumer);
        getHoes(Registration.terrasteel_hoe.get(), ModItems.terrasteel).save(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(TerrasteelCondition.SERIALIZER.getJson(new TerrasteelCondition()));
            json.add("conditions", array);
        }));
//        UpgradeRecipeBuilder.smithing(Ingredient.of(Registration.terrasteel_hoe.get()),
//                Ingredient.of(mythicbotany.ModItems.alfsteelIngot),
//                Registration.alfsteel_hoe.get())
//                .unlocks("has_item", has(Registration.terrasteel_hoe.get()))
//                .save(WrapperResult.transformJson(consumer, json -> {
//            JsonArray array = new JsonArray();
//            array.add(MythicBotanyCondition.SERIALIZER.getJson(new MythicBotanyCondition(true)));
//            json.add("conditions", array);
//        }), new ResourceLocation(AIOTBotania.MODID, "smithing/alfsteel_hoe"));

        ShapelessRecipeBuilder.shapeless(Registration.livingwood_aiot.get())
                .requires(Registration.livingwood_sword.get())
                .requires(Registration.livingwood_axe.get())
                .requires(Registration.livingwood_pickaxe.get())
                .requires(Registration.livingwood_shovel.get())
                .requires(Registration.livingwood_hoe.get())
                .unlockedBy("has_sword", has(Registration.livingwood_sword.get()))
                .unlockedBy("has_axe", has(Registration.livingwood_axe.get()))
                .unlockedBy("has_shovel", has(Registration.livingwood_shovel.get()))
                .unlockedBy("has_pickaxe", has(Registration.livingwood_pickaxe.get()))
                .unlockedBy("has_hoe", has(Registration.livingwood_hoe.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Registration.livingrock_aiot.get())
                .requires(Registration.livingrock_sword.get())
                .requires(Registration.livingrock_axe.get())
                .requires(Registration.livingrock_pickaxe.get())
                .requires(Registration.livingrock_shovel.get())
                .requires(Registration.livingrock_hoe.get())
                .unlockedBy("has_sword", has(Registration.livingrock_sword.get()))
                .unlockedBy("has_axe", has(Registration.livingrock_axe.get()))
                .unlockedBy("has_pickaxe", has(Registration.livingrock_pickaxe.get()))
                .unlockedBy("has_shovel", has(Registration.livingrock_shovel.get()))
                .unlockedBy("has_hoe", has(Registration.livingrock_hoe.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Registration.manasteel_aiot.get())
                .requires(ModItems.manasteelSword)
                .requires(ModItems.manasteelAxe)
                .requires(ModItems.manasteelPick)
                .requires(ModItems.manasteelShovel)
                .requires(ModItems.manasteelHoe)
                .unlockedBy("has_sword", has(ModItems.manasteelSword))
                .unlockedBy("has_axe", has(ModItems.manasteelAxe))
                .unlockedBy("has_pickaxe", has(ModItems.manasteelPick))
                .unlockedBy("has_shovel", has(ModItems.manasteelShovel))
                .unlockedBy("has_hoe", has(ModItems.manasteelHoe))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Registration.elementium_aiot.get())
                .requires(ModItems.elementiumSword)
                .requires(ModItems.elementiumAxe)
                .requires(ModItems.elementiumPick)
                .requires(ModItems.elementiumShovel)
                .requires(ModItems.elementiumHoe)
                .unlockedBy("has_sword", has(ModItems.elementiumSword))
                .unlockedBy("has_axe", has(ModItems.elementiumAxe))
                .unlockedBy("has_pickaxe", has(ModItems.elementiumPick))
                .unlockedBy("has_shovel", has(ModItems.elementiumShovel))
                .unlockedBy("has_hoe", has(ModItems.elementiumHoe))
                .save(consumer);
    }

    private static ShapedRecipeBuilder getShears(ItemLike result, ItemLike material) {
        return ShapedRecipeBuilder.shaped(result)
                .define('M', material)
                .pattern(" M")
                .pattern("M ")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getSwords(ItemLike result, ItemLike material) {
        return getSwords(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getSwords(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(result)
                .define('M', material)
                .define('T', twig)
                .pattern("M")
                .pattern("M")
                .pattern("T")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getAxes(ItemLike result, ItemLike material) {
        return getAxes(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getAxes(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(result)
                .define('M', material)
                .define('T', twig)
                .pattern("MM")
                .pattern("MT")
                .pattern(" T")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getPickaxes(ItemLike result, ItemLike material) {
        return getPickaxes(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getPickaxes(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(result)
                .define('M', material)
                .define('T', twig)
                .pattern("MMM")
                .pattern(" T ")
                .pattern(" T ")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getShovels(ItemLike result, ItemLike material) {
        return getShovels(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getShovels(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(result)
                .define('M', material)
                .define('T', twig)
                .pattern("M")
                .pattern("T")
                .pattern("T")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getHoes(ItemLike result, ItemLike material) {
        return getHoes(result, material, ModItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getHoes(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(result)
                .define('M', material)
                .define('T', twig)
                .pattern("MM")
                .pattern(" T")
                .pattern(" T")
                .unlockedBy("has_material", has(material));
    }
}

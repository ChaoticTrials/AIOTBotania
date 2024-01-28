package de.melanx.aiotbotania.data;

import com.google.gson.JsonArray;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.crafting.MythicBotanyCondition;
import de.melanx.aiotbotania.core.crafting.TerrasteelCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.data.recipes.WrapperResult;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

@SuppressWarnings("SameParameterValue")
public class Recipes extends RecipeProvider {

    public Recipes(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        getShears(Registration.livingwood_shears.get(), BotaniaBlocks.livingwood).save(consumer);
        getShears(Registration.livingrock_shears.get(), BotaniaBlocks.livingrock).save(consumer);

        getSwords(Registration.livingwood_sword.get(), BotaniaBlocks.livingwood).save(consumer);
        getSwords(Registration.livingrock_sword.get(), BotaniaBlocks.livingrock).save(consumer);

        getAxes(Registration.livingwood_axe.get(), BotaniaBlocks.livingwood).save(consumer);
        getAxes(Registration.livingrock_axe.get(), BotaniaBlocks.livingrock).save(consumer);

        getPickaxes(Registration.livingwood_pickaxe.get(), BotaniaBlocks.livingwood).save(consumer);
        getPickaxes(Registration.livingrock_pickaxe.get(), BotaniaBlocks.livingrock).save(consumer);

        getShovels(Registration.livingwood_shovel.get(), BotaniaBlocks.livingwood).save(consumer);
        getShovels(Registration.livingrock_shovel.get(), BotaniaBlocks.livingrock).save(consumer);
        getShovels(Registration.terrasteel_shovel.get(), BotaniaItems.terrasteel).save(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(TerrasteelCondition.SERIALIZER.getJson(new TerrasteelCondition()));
            json.add("conditions", array);
        }));
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY,
                        Ingredient.of(mythicbotany.register.ModItems.alfsteelIngot),
                        Ingredient.of(Registration.alfsteel_shovel.get()), RecipeCategory.TOOLS, Registration.alfsteel_shovel.get())
                .unlocks("has_item", has(Registration.terrasteel_hoe.get()))
                .save(WrapperResult.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(MythicBotanyCondition.SERIALIZER.getJson(new MythicBotanyCondition()));
                    json.add("conditions", array);
                }), new ResourceLocation(AIOTBotania.MODID, "smithing/alfsteel_shovel"));

        getHoes(Registration.livingwood_hoe.get(), BotaniaBlocks.livingwood).save(consumer);
        getHoes(Registration.livingrock_hoe.get(), BotaniaBlocks.livingrock).save(consumer);
        getHoes(Registration.terrasteel_hoe.get(), BotaniaItems.terrasteel).save(WrapperResult.transformJson(consumer, json -> {
            JsonArray array = new JsonArray();
            array.add(TerrasteelCondition.SERIALIZER.getJson(new TerrasteelCondition()));
            json.add("conditions", array);
        }));
        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY,
                        Ingredient.of(mythicbotany.register.ModItems.alfsteelIngot),
                        Ingredient.of(Registration.alfsteel_hoe.get()), RecipeCategory.TOOLS, Registration.alfsteel_hoe.get())
                .unlocks("has_item", has(Registration.terrasteel_hoe.get()))
                .save(WrapperResult.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(MythicBotanyCondition.SERIALIZER.getJson(new MythicBotanyCondition()));
                    json.add("conditions", array);
                }), new ResourceLocation(AIOTBotania.MODID, "smithing/alfsteel_hoe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registration.livingwood_aiot.get())
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registration.livingrock_aiot.get())
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registration.manasteel_aiot.get())
                .requires(BotaniaItems.manasteelSword)
                .requires(BotaniaItems.manasteelAxe)
                .requires(BotaniaItems.manasteelPick)
                .requires(BotaniaItems.manasteelShovel)
                .requires(BotaniaItems.manasteelHoe)
                .unlockedBy("has_sword", has(BotaniaItems.manasteelSword))
                .unlockedBy("has_axe", has(BotaniaItems.manasteelAxe))
                .unlockedBy("has_pickaxe", has(BotaniaItems.manasteelPick))
                .unlockedBy("has_shovel", has(BotaniaItems.manasteelShovel))
                .unlockedBy("has_hoe", has(BotaniaItems.manasteelHoe))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registration.elementium_aiot.get())
                .requires(BotaniaItems.elementiumSword)
                .requires(BotaniaItems.elementiumAxe)
                .requires(BotaniaItems.elementiumPick)
                .requires(BotaniaItems.elementiumShovel)
                .requires(BotaniaItems.elementiumHoe)
                .unlockedBy("has_sword", has(BotaniaItems.elementiumSword))
                .unlockedBy("has_axe", has(BotaniaItems.elementiumAxe))
                .unlockedBy("has_pickaxe", has(BotaniaItems.elementiumPick))
                .unlockedBy("has_shovel", has(BotaniaItems.elementiumShovel))
                .unlockedBy("has_hoe", has(BotaniaItems.elementiumHoe))
                .save(consumer);
    }

    private static ShapedRecipeBuilder getShears(ItemLike result, ItemLike material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('M', material)
                .pattern(" M")
                .pattern("M ")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getSwords(ItemLike result, ItemLike material) {
        return getSwords(result, material, BotaniaItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getSwords(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('M', material)
                .define('T', twig)
                .pattern("M")
                .pattern("M")
                .pattern("T")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getAxes(ItemLike result, ItemLike material) {
        return getAxes(result, material, BotaniaItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getAxes(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('M', material)
                .define('T', twig)
                .pattern("MM")
                .pattern("MT")
                .pattern(" T")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getPickaxes(ItemLike result, ItemLike material) {
        return getPickaxes(result, material, BotaniaItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getPickaxes(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('M', material)
                .define('T', twig)
                .pattern("MMM")
                .pattern(" T ")
                .pattern(" T ")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getShovels(ItemLike result, ItemLike material) {
        return getShovels(result, material, BotaniaItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getShovels(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('M', material)
                .define('T', twig)
                .pattern("M")
                .pattern("T")
                .pattern("T")
                .unlockedBy("has_material", has(material));
    }

    private static ShapedRecipeBuilder getHoes(ItemLike result, ItemLike material) {
        return getHoes(result, material, BotaniaItems.livingwoodTwig);
    }

    private static ShapedRecipeBuilder getHoes(ItemLike result, ItemLike material, ItemLike twig) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .define('M', material)
                .define('T', twig)
                .pattern("MM")
                .pattern(" T")
                .pattern(" T")
                .unlockedBy("has_material", has(material));
    }
}

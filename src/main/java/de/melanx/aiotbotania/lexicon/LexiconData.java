package de.melanx.aiotbotania.lexicon;

import de.melanx.aiotbotania.config.values.ConfigBoolValues;
import de.melanx.aiotbotania.crafting.CraftingRecipes;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public final class LexiconData {

    public static LexiconEntry livingwoodTools;
    public static LexiconEntry livingrockTools;

    public static void init() {

        vazkii.botania.common.lexicon.LexiconData.manasteelGear.setLexiconPages(new PageText("11"), new PageCraftingRecipe("12", CraftingRecipes.recipeManaHoe));
        if(ConfigBoolValues.MANASTEEL_AIOT.isEnabled())
            vazkii.botania.common.lexicon.LexiconData.manasteelGear.setLexiconPages(new PageCraftingRecipe("13", CraftingRecipes.recipeManaAIOT));
        vazkii.botania.common.lexicon.LexiconData.elfGear.setLexiconPages(new PageText("16"), new PageCraftingRecipe("17", CraftingRecipes.recipeElementiumHoe), new PageText("18"));
        if(ConfigBoolValues.ELEMENTIUM_AIOT.isEnabled())
            vazkii.botania.common.lexicon.LexiconData.elfGear.setLexiconPages(new PageCraftingRecipe("19", CraftingRecipes.recipeElementiumAIOT));

        if(ConfigBoolValues.LIVINGWOOD_TOOLS.isEnabled()) {
            livingwoodTools = new BasicLexiconEntry("livingwoodTools", BotaniaAPI.categoryTools);
            livingwoodTools.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.recipeWoodSword), new PageCraftingRecipe("2", CraftingRecipes.recipeWoodAxe), new PageCraftingRecipe("3", CraftingRecipes.recipeWoodPickaxe), new PageCraftingRecipe("4", CraftingRecipes.recipeWoodShovel), new PageCraftingRecipe("5", CraftingRecipes.recipeWoodHoe));
            if(ConfigBoolValues.LIVINGWOOD_AIOT.isEnabled())
                livingwoodTools.setLexiconPages(new PageCraftingRecipe("6", CraftingRecipes.recipeWoodAIOT));
        }

        if(ConfigBoolValues.LIVINGROCK_TOOLS.isEnabled()) {
            livingrockTools = new BasicLexiconEntry("livingrockTools", BotaniaAPI.categoryTools);
            livingrockTools.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", CraftingRecipes.recipeRockSword), new PageCraftingRecipe("2", CraftingRecipes.recipeRockAxe), new PageCraftingRecipe("3", CraftingRecipes.recipeRockPickaxe), new PageCraftingRecipe("4", CraftingRecipes.recipeRockShovel), new PageCraftingRecipe("5", CraftingRecipes.recipeRockHoe));
            if(ConfigBoolValues.LIVINGROCK_AIOT.isEnabled())
                livingrockTools.setLexiconPages(new PageCraftingRecipe("6", CraftingRecipes.recipeRockAIOT));
        }
    }

}

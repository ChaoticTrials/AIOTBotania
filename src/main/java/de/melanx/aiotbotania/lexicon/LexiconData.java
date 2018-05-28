package de.melanx.aiotbotania.lexicon;

import de.melanx.aiotbotania.crafting.CraftingRecipes;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconData {

    public static void init() {

        vazkii.botania.common.lexicon.LexiconData.manasteelGear.setLexiconPages(new PageText("11"), new PageText("12"), new PageCraftingRecipe("13", CraftingRecipes.recipeElementiumAIOT));

    }

}

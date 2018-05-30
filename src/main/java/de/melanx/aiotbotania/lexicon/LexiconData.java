package de.melanx.aiotbotania.lexicon;

import de.melanx.aiotbotania.crafting.CraftingRecipes;
import vazkii.botania.common.crafting.ModCraftingRecipes;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public final class LexiconData {

    public static void init() {

        vazkii.botania.common.lexicon.LexiconData.manasteelGear.setLexiconPages(new PageText("11"), new PageCraftingRecipe("12", CraftingRecipes.recipeManaHoe), new PageCraftingRecipe("13", CraftingRecipes.recipeManaAIOT));
        vazkii.botania.common.lexicon.LexiconData.elfGear.setLexiconPages(new PageText("16"), new PageCraftingRecipe("17", CraftingRecipes.recipeElementiumHoe), new PageText("18"), new PageCraftingRecipe("19", CraftingRecipes.recipeElementiumAIOT));

    }

}

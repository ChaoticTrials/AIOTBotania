package de.melanx.aiotbotania.lexicon;

import de.melanx.aiotbotania.items.ModItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconData {

    private static LexiconEntry livingwoodTools;
    private static LexiconEntry livingrockTools;

    public static void init() {

        vazkii.botania.common.lexicon.LexiconData.manasteelGear.setLexiconPages(new PageText("11"), new PageCraftingRecipe("12", ModItems.manasteel_hoe));
//        if(ConfigBoolValues.MANASTEEL_AIOT.isEnabled())
            vazkii.botania.common.lexicon.LexiconData.manasteelGear.setLexiconPages(new PageCraftingRecipe("13", ModItems.manasteel_aiot));
        vazkii.botania.common.lexicon.LexiconData.elfGear.setLexiconPages(new PageText("16"), new PageCraftingRecipe("17", ModItems.elementium_hoe), new PageText("18"));
//        if(ConfigBoolValues.ELEMENTIUM_AIOT.isEnabled())
            vazkii.botania.common.lexicon.LexiconData.elfGear.setLexiconPages(new PageCraftingRecipe("19", ModItems.elementium_aiot));

//        if(ConfigBoolValues.LIVINGWOOD_TOOLS.isEnabled()) {
            livingwoodTools = new BasicLexiconEntry("livingwoodTools", BotaniaAPI.categoryTools);
            livingwoodTools.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModItems.livingwood_sword), new PageCraftingRecipe("2", ModItems.livingwood_axe), new PageCraftingRecipe("3", ModItems.livingwood_pickaxe), new PageCraftingRecipe("4", ModItems.livingwood_shovel), new PageCraftingRecipe("5", ModItems.livingwood_shears), new PageCraftingRecipe("6", ModItems.livingwood_hoe));
//            if(ConfigBoolValues.LIVINGWOOD_AIOT.isEnabled())
                livingwoodTools.setLexiconPages(new PageCraftingRecipe("6", ModItems.livingwood_aiot));
//        }

//        if(ConfigBoolValues.LIVINGROCK_TOOLS.isEnabled()) {
            livingrockTools = new BasicLexiconEntry("livingrockTools", BotaniaAPI.categoryTools);
            livingrockTools.setLexiconPages(new PageText("0"), new PageCraftingRecipe("1", ModItems.livingrock_sword), new PageCraftingRecipe("2", ModItems.livingrock_axe), new PageCraftingRecipe("3", ModItems.livingrock_pickaxe), new PageCraftingRecipe("4", ModItems.livingrock_shovel), new PageCraftingRecipe("5", ModItems.livingrock_shears), new PageCraftingRecipe("6", ModItems.livingrock_hoe));
//            if(ConfigBoolValues.LIVINGROCK_AIOT.isEnabled())
                livingrockTools.setLexiconPages(new PageCraftingRecipe("6", ModItems.livingrock_aiot));
//        }
    }


}

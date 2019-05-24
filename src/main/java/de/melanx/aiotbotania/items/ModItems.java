package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.elementium.ItemElementiumHoe;
import de.melanx.aiotbotania.items.livingrock.*;
import de.melanx.aiotbotania.items.livingwood.*;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.item.Item;

public class ModItems {

//    // Livingwood
    public static Item livingwood_sword;
    public static Item livingwood_axe;
    public static Item livingwood_pickaxe;
    public static Item livingwood_shovel;
    public static Item livingwood_hoe;
    public static Item livingwood_aiot;
//
//    // Livingrock
    public static Item livingrock_sword;
    public static Item livingrock_axe;
    public static Item livingrock_pickaxe;
    public static Item livingrock_shovel;
    public static Item livingrock_hoe;
    public static Item livingrock_aiot;
//
//    // Manasteel
    public static Item manasteel_hoe;
    public static Item manasteel_aiot;
//
//    // Elementium
    public static Item elementium_hoe;
    public static Item elementium_aiot;

    public static void init() {
//        // Livingwood
//        if(ConfigBoolValues.LIVINGWOOD_TOOLS.isEnabled()) {
            livingwood_sword = new ItemLivingwoodSword();
            livingwood_axe = new ItemLivingwoodAxe();
            livingwood_pickaxe = new ItemLivingwoodPickaxe();
            livingwood_shovel = new ItemLivingwoodShovel();
            livingwood_hoe = new ItemLivingwoodHoe();
//            if(ConfigBoolValues.LIVINGWOOD_AIOT.isEnabled())
//                livingwood_aiot = new ItemLivingwoodAIOT();
//        }
//
//        // Livingrock
//        if(ConfigBoolValues.LIVINGROCK_TOOLS.isEnabled()) {
            livingrock_sword = new ItemLivingrockSword();
            livingrock_axe = new ItemLivingrockAxe();
            livingrock_pickaxe = new ItemLivingrockPickaxe();
            livingrock_shovel = new ItemLivingrockShovel();
            livingrock_hoe = new ItemLivingrockHoe();
//            if(ConfigBoolValues.LIVINGROCK_AIOT.isEnabled())
//                livingrock_aiot = new ItemLivingrockAIOT();
//        }
        // Manasteel
        manasteel_hoe = new ItemManasteelHoe();
//        if(ConfigBoolValues.MANASTEEL_AIOT.isEnabled())
            manasteel_aiot = new ItemManasteelAIOT();
//
//        // Elementium
        elementium_hoe = new ItemElementiumHoe();
//        if(ConfigBoolValues.ELEMENTIUM_AIOT.isEnabled())
//            elementium_aiot = new ItemElementiumAIOT();
    }

}

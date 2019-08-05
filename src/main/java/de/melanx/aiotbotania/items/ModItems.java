package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.items.elementium.*;
import de.melanx.aiotbotania.items.manasteel.*;
import de.melanx.aiotbotania.items.livingrock.*;
import de.melanx.aiotbotania.items.livingwood.*;
import net.minecraft.item.Item;

public class ModItems {

    // Livingwood
    public static Item livingwood_sword;
    public static Item livingwood_axe;
    public static Item livingwood_pickaxe;
    public static Item livingwood_shovel;
    public static Item livingwood_hoe;
    public static Item livingwood_aiot;

    // Livingrock
    public static Item livingrock_sword;
    public static Item livingrock_axe;
    public static Item livingrock_pickaxe;
    public static Item livingrock_shovel;
    public static Item livingrock_hoe;
    public static Item livingrock_aiot;

    // Manasteel
    public static Item manasteel_hoe;
    public static Item manasteel_aiot;

    // Elementium
    public static Item elementium_hoe;
    public static Item elementium_aiot;

    public static void init() {
        // Livingwood
        livingwood_sword = new ItemLivingwoodSword();
        livingwood_axe = new ItemLivingwoodAxe();
        livingwood_pickaxe = new ItemLivingwoodPickaxe();
        livingwood_shovel = new ItemLivingwoodShovel();
        livingwood_hoe = new ItemLivingwoodHoe();
        livingwood_aiot = new ItemLivingwoodAIOT();

        // Livingrock
        livingrock_sword = new ItemLivingrockSword();
        livingrock_axe = new ItemLivingrockAxe();
        livingrock_pickaxe = new ItemLivingrockPickaxe();
        livingrock_shovel = new ItemLivingrockShovel();
        livingrock_hoe = new ItemLivingrockHoe();
        livingrock_aiot = new ItemLivingrockAIOT();

        // Manasteel
        manasteel_hoe = new ItemManasteelHoe();
        manasteel_aiot = new ItemManasteelAIOT();

        // Elementium
        elementium_hoe = new ItemElementiumHoe();
        elementium_aiot = new ItemElementiumAIOT();
    }

}

package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.config.values.ConfigBoolValues;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.elementium.ItemElementiumHoe;
import de.melanx.aiotbotania.items.livingwood.*;
import de.melanx.aiotbotania.items.livingrock.*;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.item.Item;

public class ModItems {
    // Livingwood
    public static ItemLivingwoodSword livingwoodsword;
    public static ItemLivingwoodAxe livingwoodaxe;
    public static ItemLivingwoodPickaxe livingwoodpickaxe;
    public static ItemLivingwoodShovel livingwoodshovel;
    public static ItemLivingwoodHoe livingwoodhoe;
    public static ItemLivingwoodAIOT livingwoodaiot;

    // Livingrock
    public static ItemLivingrockSword livingrocksword;
    public static ItemLivingrockAxe livingrockaxe;
    public static ItemLivingrockPickaxe livingrockpickaxe;
    public static ItemLivingrockShovel livingrockshovel;
    public static ItemLivingrockHoe livingrockhoe;
    public static ItemLivingrockAIOT livingrockaiot;

    // Manasteel
    public static Item manahoe;
    public static Item manaaiot;

    // Elementium
    public static Item elementiumhoe;
    public static Item elementiumaiot;

    public static void init() {
        // Livingwood
        if(ConfigBoolValues.LIVINGWOOD_TOOLS.isEnabled()) {
            livingwoodsword = new ItemLivingwoodSword();
            livingwoodaxe = new ItemLivingwoodAxe();
            livingwoodpickaxe = new ItemLivingwoodPickaxe();
            livingwoodshovel = new ItemLivingwoodShovel();
            livingwoodhoe = new ItemLivingwoodHoe();
            if(ConfigBoolValues.LIVINGWOOD_AIOT.isEnabled())
                livingwoodaiot = new ItemLivingwoodAIOT();
        }

        // Livingrock
        if(ConfigBoolValues.LIVINGROCK_TOOLS.isEnabled()) {
            livingrocksword = new ItemLivingrockSword();
            livingrockaxe = new ItemLivingrockAxe();
            livingrockpickaxe = new ItemLivingrockPickaxe();
            livingrockshovel = new ItemLivingrockShovel();
            livingrockhoe = new ItemLivingrockHoe();
            if(ConfigBoolValues.LIVINGROCK_AIOT.isEnabled())
                livingrockaiot = new ItemLivingrockAIOT();
        }
        // Manasteel
        manahoe = new ItemManasteelHoe();
        if(ConfigBoolValues.MANASTEEL_AIOT.isEnabled())
            manaaiot = new ItemManasteelAIOT();

        // Elementium
        elementiumhoe = new ItemElementiumHoe();
        if(ConfigBoolValues.ELEMENTIUM_AIOT.isEnabled())
            elementiumaiot = new ItemElementiumAIOT();
    }
}

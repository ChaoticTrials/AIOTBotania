package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.config.values.ConfigBoolValues;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.elementium.ItemElementiumHoe;
import de.melanx.aiotbotania.items.livingwood.*;
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
    public static ItemLivingwoodAIOT livingwoodAIOT;

    // Livingrock

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
            if (ConfigBoolValues.LIVINGWOOD_AIOT.isEnabled())
                livingwoodAIOT = new ItemLivingwoodAIOT();
        }

        // Livingrock

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

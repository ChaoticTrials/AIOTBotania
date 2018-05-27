package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.elementium.ItemElementiumHoe;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.item.Item;

public class ModItems {
    public static Item manahoe;
    public static Item manaaiot;

    public static Item elementiumhoe;
    public static Item elementiumaiot;

    public static void init() {
        manahoe = new ItemManasteelHoe();
        manaaiot = new ItemManasteelAIOT();

        elementiumhoe = new ItemElementiumHoe();
        elementiumaiot = new ItemElementiumAIOT();
    }
}

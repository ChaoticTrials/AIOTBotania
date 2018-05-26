package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemManasteelHoe manahoe = new ItemManasteelHoe();
    public static ItemManasteelAIOT manaaiot = new ItemManasteelAIOT();

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                manahoe,
                manaaiot
        );
    }

    public static void registerModels() {
        manahoe.registerItemModel();
        manaaiot.registerItemModel();
    }

}

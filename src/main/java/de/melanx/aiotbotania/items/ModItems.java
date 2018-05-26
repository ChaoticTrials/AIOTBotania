package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.items.manasteel.*;
import de.melanx.aiotbotania.items.elementium.*;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemManasteelHoe manahoe = new ItemManasteelHoe();

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                manahoe
        );
    }

    public static void registerModels() {
        manahoe.registerItemModel();
    }

}

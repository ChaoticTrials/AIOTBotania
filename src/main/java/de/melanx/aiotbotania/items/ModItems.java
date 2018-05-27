package de.melanx.aiotbotania.items;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

public class ModItems {
    public static final List<Item> ITEMS_TO_REGISTER = new ArrayList<>();
    public static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS = new HashMap<>();

    public static Item manahoe;
    public static Item manaaiot;

    public static void init() {
        manahoe = new ItemManasteelHoe();
        manaaiot = new ItemManasteelAIOT();
    }

    public static void registerItem(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setRegistryName(AIOTBotania.MODID, name);
        item.setCreativeTab(AIOTBotania.creativeTab);

        ITEMS_TO_REGISTER.add(item);
    }

    public static void registerModel(Item item) {
        MODEL_LOCATIONS.put(new ItemStack(item), new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }
}

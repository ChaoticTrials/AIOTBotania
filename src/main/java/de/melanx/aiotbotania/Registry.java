package de.melanx.aiotbotania;

import de.melanx.aiotbotania.blocks.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class Registry {
    public static final List<Item> ITEMS_TO_REGISTER = new ArrayList<>();
    public static final List<Block> BLOCKS_TO_REGISTER = new ArrayList<>();
    public static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS = new HashMap<>();

    public static void registerItem(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setRegistryName(AIOTBotania.MODID, name);
        item.setCreativeTab(AIOTBotania.creativeTab);

        ITEMS_TO_REGISTER.add(item);
    }

    public static void registerBlock(Block block, String name, @Nullable ItemBlockBase itemBlock) {
        block.setUnlocalizedName(name);
        block.setRegistryName(AIOTBotania.MODID, name);
        BLOCKS_TO_REGISTER.add(block);

        if(itemBlock != null) {
            itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
            block.setCreativeTab(AIOTBotania.creativeTab);
            ITEMS_TO_REGISTER.add(itemBlock);
        }
    }

    public static void registerModel(Object item) {
        if(item instanceof Item) {
            MODEL_LOCATIONS.put(new ItemStack((Item)item), new ModelResourceLocation(Objects.requireNonNull(((Item)item).getRegistryName()), "inventory"));
        } else if(item instanceof Block) {
            MODEL_LOCATIONS.put(new ItemStack((Block)item), new ModelResourceLocation(Objects.requireNonNull(((Block)item).getRegistryName()), "inventory"));
        } else {
            throw new IllegalArgumentException("item should be of type Item or Block");
        }
    }
}

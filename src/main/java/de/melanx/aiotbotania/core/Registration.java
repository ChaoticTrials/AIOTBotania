package de.melanx.aiotbotania.core;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.blocks.BlockCustomFarmland;
import de.melanx.aiotbotania.core.config.ConfigHandler;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemSwordBase;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.elementium.ItemElementiumHoe;
import de.melanx.aiotbotania.items.livingrock.*;
import de.melanx.aiotbotania.items.livingwood.*;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> e) {
        IForgeRegistry<Item> registry = e.getRegistry();

        // Livingwood
        if (ConfigHandler.COMMON.LIVINGWOOD_TOOLS.get()) {
            registry.register(new ItemLivingwoodShears());
            registry.register(new ItemSwordBase("livingwood_sword", ItemTiers.LIVINGROCK_ITEM_TIER, 3, -2.5F, 30));
            registry.register(new ItemLivingwoodAxe());
            registry.register(new ItemLivingwoodPickaxe());
            registry.register(new ItemLivingwoodShovel());
            registry.register(new ItemLivingwoodHoe());
            registry.register(new ItemLivingwoodAIOT());
        }

        // Livingrock
        if (ConfigHandler.COMMON.LIVINGROCK_TOOLS.get()) {
            registry.register(new ItemLivingrockShears());
            registry.register(new ItemSwordBase("livingrock_sword", ItemTiers.LIVINGROCK_ITEM_TIER, 2, -2.4F, 40));
            registry.register(new ItemLivingrockAxe());
            registry.register(new ItemLivingrockPickaxe());
            registry.register(new ItemLivingrockShovel());
            registry.register(new ItemLivingrockHoe());
            registry.register(new ItemLivingrockAIOT());
        }

        // Manasteel
        registry.register(new ItemManasteelHoe());
        registry.register(new ItemManasteelAIOT());

        // Elementium
        registry.register(new ItemElementiumHoe());
        registry.register(new ItemElementiumAIOT());

        AIOTBotania.instance.getLogger().info("Items registered.");
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> e) {
        e.getRegistry().register(new BlockCustomFarmland(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));

        AIOTBotania.instance.getLogger().info("Blocks registered.");
    }

}

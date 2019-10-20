package de.melanx.aiotbotania.core;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemSwordBase;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.elementium.ItemElementiumHoe;
import de.melanx.aiotbotania.items.livingrock.*;
import de.melanx.aiotbotania.items.livingwood.*;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(
                // AIOTs
                new ItemElementiumAIOT(),
                new ItemManasteelAIOT(),
                new ItemLivingrockAIOT(),
                new ItemLivingwoodAIOT(),

                // Axes
                new ItemLivingrockAxe(),
                new ItemLivingwoodAxe(),

                // Hoes
                new ItemElementiumHoe(),
                new ItemLivingrockHoe(),
                new ItemLivingwoodHoe(),
                new ItemManasteelHoe(),

                // Pickaxes
                new ItemLivingrockPickaxe(),
                new ItemLivingwoodPickaxe(),

                // Shears
                new ItemLivingrockShovel(),
                new ItemLivingwoodShears(),

                // Shovels
                new ItemLivingrockShears(),
                new ItemLivingwoodShovel(),

                // Swords
                new ItemSwordBase("livingrock_sword", ItemTiers.LIVINGROCK_ITEM_TIER, 2, -2.4F, 40),
                new ItemSwordBase("livingwood_sword", ItemTiers.LIVINGROCK_ITEM_TIER, 3, -2.5F, 30)
        );

        AIOTBotania.instance.getLogger().info("Items registered.");
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> e) {
        e.getRegistry().registerAll(
//                new BlockCustomFarmland(Block.Properties.from(Blocks.FARMLAND))
        );

        AIOTBotania.instance.getLogger().info("Blocks registered.");
    }

}

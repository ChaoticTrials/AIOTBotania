package de.melanx.aiotbotania.core;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.blocks.BlockCustomFarmland;
import de.melanx.aiotbotania.core.config.ConfigHandler;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.*;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAxe;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockPickaxe;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockShovel;
import de.melanx.aiotbotania.items.livingwood.ItemLivingwoodAIOT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.botania.api.BotaniaAPI;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> e) {
        IForgeRegistry<Item> registry = e.getRegistry();

        // Livingwood
        if (ConfigHandler.COMMON.LIVINGWOOD_TOOLS.get()) {
            registry.register(new ItemShearsBase("livingwood_shears", 10, 60));
            registry.register(new ItemSwordBase("livingwood_sword", ItemTiers.LIVINGROCK_ITEM_TIER, 3, -2.5F, 30));
            registry.register(new ItemAxeBase("livingwood_axe", ItemTiers.LIVINGWOOD_ITEM_TIER, 30, 6.0F, -3.2F));
            registry.register(new ItemPickaxeBase("livingwood_pickaxe", ItemTiers.LIVINGWOOD_ITEM_TIER, 1, -2.8F, 30));
            registry.register(new ItemShovelBase("livingwood_shovel", ItemTiers.LIVINGWOOD_ITEM_TIER, 1, -3.0F, 30));
            registry.register(new ItemHoeBase("livingwood_hoe", ItemTiers.LIVINGWOOD_ITEM_TIER, -3, 30, false, true));
            registry.register(new ItemLivingwoodAIOT());
        }

        // Livingrock
        if (ConfigHandler.COMMON.LIVINGROCK_TOOLS.get()) {
            registry.register(new ItemShearsBase("livingrock_shears", 10, 119));
            registry.register(new ItemSwordBase("livingrock_sword", ItemTiers.LIVINGROCK_ITEM_TIER, 2, -2.4F, 40));
            registry.register(new ItemLivingrockAxe());
            registry.register(new ItemLivingrockPickaxe());
            registry.register(new ItemLivingrockShovel());
            registry.register(new ItemHoeBase("livingrock_hoe", ItemTiers.LIVINGROCK_ITEM_TIER, -2, 40, false, true));
            registry.register(new ItemLivingrockAIOT());
        }

        // Manasteel
        registry.register(new ItemHoeBase("manasteel_hoe", BotaniaAPI.MANASTEEL_ITEM_TIER, -1, 60, false, false));
        registry.register(new ItemAIOTBase("manasteel_aiot", ItemTiers.MANASTEEL_AIOT_ITEM_TIER, 6.0F, -2.2F, 66, false));

        // Elementium
        registry.register(new ItemHoeBase("elementium_hoe", BotaniaAPI.ELEMENTIUM_ITEM_TIER, -1, 60, true, false));
        registry.register(new ItemElementiumAIOT());

        AIOTBotania.instance.getLogger().info("Items registered.");
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> e) {
        e.getRegistry().register(new BlockCustomFarmland());

        AIOTBotania.instance.getLogger().info("Blocks registered.");
    }

}

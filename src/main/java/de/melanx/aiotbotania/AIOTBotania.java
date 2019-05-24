package de.melanx.aiotbotania;

import de.melanx.aiotbotania.blocks.ModBlocks;
import de.melanx.aiotbotania.crafting.CraftingRecipes;
import de.melanx.aiotbotania.lib.LibMisc;
import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.util.CreativeTab;
import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LibMisc.MODID)
public class AIOTBotania {

    public static AIOTBotania instance;
    private static final Logger LOGGER = LogManager.getLogger(LibMisc.MODID);

    public static final ItemGroup aiotItemGroup = new CreativeTab();

    public AIOTBotania() {

        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        CraftingRecipes.init();
        // waiting for Botania LexiconData.init()
        LOGGER.info("Setup method registered.");
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        LOGGER.info("clientRegistries method registered.");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            ModItems.init();
            for(Item item : Registry.ITEMS_TO_REGISTER) {
                event.getRegistry().register(item);
                LOGGER.info(item.getRegistryName());
            }

            LOGGER.info("Items registered.");
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event)
        {
            ModBlocks.init();
            for(Block block : Registry.BLOCKS_TO_REGISTER) {
                event.getRegistry().register(block);
                LOGGER.info(block.getRegistryName());
            }

            LOGGER.info("Blocks registered.");
        }

    }

    private static ResourceLocation location(String name) {
        return new ResourceLocation(LibMisc.MODID, name);
    }

}

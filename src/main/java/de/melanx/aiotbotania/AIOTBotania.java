package de.melanx.aiotbotania;

import de.melanx.aiotbotania.capabilities.FarmlandData;
import de.melanx.aiotbotania.capabilities.FarmlandDataProvider;
import de.melanx.aiotbotania.capabilities.FarmlandDataStorage;
import de.melanx.aiotbotania.config.ConfigHandler;
import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.lib.LibMisc;
import de.melanx.aiotbotania.util.CreativeTab;
import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LibMisc.MODID)
public class AIOTBotania {

    public static AIOTBotania instance;
    public static final Logger LOGGER = LogManager.getLogger(LibMisc.MODID);

    public static final ItemGroup aiotItemGroup = new CreativeTab();

    public AIOTBotania() {

        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

    }

    private void setup(final FMLCommonSetupEvent event) {
        // waiting for Botania LexiconData.init()
        CapabilityManager.INSTANCE.register(FarmlandData.class, new FarmlandDataStorage(), FarmlandData::new);
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
            for (Item item : Registry.ITEMS_TO_REGISTER) {
                event.getRegistry().register(item);
                LOGGER.info(item.getRegistryName());
            }

            LOGGER.info("Items registered.");
        }
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<World> event) {
        if (event.getObject() != null && !event.getObject().isRemote()) {
            event.addCapability(location("farmland_data"), new FarmlandDataProvider());
        }
    }

    private static ResourceLocation location(String name) {
        return new ResourceLocation(LibMisc.MODID, name);
    }

}

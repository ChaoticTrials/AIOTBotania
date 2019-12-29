package de.melanx.aiotbotania.core.proxy;

import de.melanx.aiotbotania.capabilities.chunkmarker.ChunkMarker;
import de.melanx.aiotbotania.capabilities.chunkmarker.ChunkMarkerStorage;
import de.melanx.aiotbotania.capabilities.farmlanddata.FarmlandData;
import de.melanx.aiotbotania.capabilities.farmlanddata.FarmlandDataStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {
    public void start() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, de.melanx.aiotbotania.config.ConfigHandler.COMMON_SPEC);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        registerListeners(bus);
    }

    public void registerListeners(IEventBus bus) {
        bus.addListener(this::setup);
    }

    public void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ChunkMarker.class, new ChunkMarkerStorage(), ChunkMarker::new);
        CapabilityManager.INSTANCE.register(FarmlandData.class, new FarmlandDataStorage(), FarmlandData::new);
    }
}

package de.melanx.aiotbotania.handlers;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.capabilities.chunkmarker.ChunkMarkerProvider;
import de.melanx.aiotbotania.capabilities.farmlanddata.FarmlandDataProvider;
import de.melanx.aiotbotania.util.Util;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapabilitiesHandler {
    @SubscribeEvent
    public static void attachChunkCapabilities(AttachCapabilitiesEvent<Chunk> e) {
        if (e.getObject() != null && !e.getObject().getWorld().isRemote()) {
            e.addCapability(Util.resourceOf("farmland_data"), new FarmlandDataProvider());
        }
    }

    @SubscribeEvent
    public static void attachWorldCapabilities(AttachCapabilitiesEvent<World> e) {
        if (e.getObject() != null && !e.getObject().isRemote()) {
            e.addCapability(Util.resourceOf("chunk_marker"), new ChunkMarkerProvider());
        }
    }
}

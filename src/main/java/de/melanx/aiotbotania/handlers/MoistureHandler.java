package de.melanx.aiotbotania.handlers;

import de.melanx.aiotbotania.capabilities.FarmlandDataProvider;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoistureHandler {
    private static int ticks = 0;

    // Maybe remove the tick check to reduce flickering when tilting soil (performance penalty?)
    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (ticks % 2 == 0) {
            if (!event.world.isRemote()) {
                event.world.getCapability(FarmlandDataProvider.FARMLAND_DATA_CAP).ifPresent(data -> {
                    data.moistenAll(event.world);
                });
            }
        }
        ticks++;
        if (ticks == 3) {
            ticks = 0;
        }
    }

    @SubscribeEvent
    public static void preventFarmlandDestroy(BlockEvent.FarmlandTrampleEvent event) {
        World world = event.getWorld().getWorld();
        if (!world.isRemote()) {
            world.getCapability(FarmlandDataProvider.FARMLAND_DATA_CAP).ifPresent(data -> {
                if (data.getAll().contains(event.getPos())) {
                    System.out.println(event.isCancelable());
                    event.setCanceled(true);
                }
            });
        }
    }
}

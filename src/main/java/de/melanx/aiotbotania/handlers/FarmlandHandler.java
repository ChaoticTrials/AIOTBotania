package de.melanx.aiotbotania.handlers;

import de.melanx.aiotbotania.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FarmlandHandler {

    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (!event.world.isRemote()) {
            List<BlockPos> blocks = Util.getAllFarmlandBlocksToBeMoistened(event.world);
            blocks.forEach(blockPos -> Util.moistenFarmland(event.world, blockPos));
        }
    }

    @SubscribeEvent
    public static void preventFarmlandDestroy(BlockEvent.FarmlandTrampleEvent event) {
        World world = event.getWorld().getWorld();
        if (!world.isRemote()) {
            List<BlockPos> blocks = Util.getAllFarmlandBlocksToBeMoistened(world);
            if (blocks.contains(event.getPos())) {
                event.setCanceled(true);
            }
        }
    }
}

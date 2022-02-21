package de.melanx.aiotbotania.core.network;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class AIOTBotaniaNetwork {

    private static final String PROTOCOL_VERSION = "1";
    private static int discriminator = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AIOTBotania.MODID, "netchannel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        INSTANCE.registerMessage(discriminator++, TerrasteelCreateBurstMesssage.class, (msg, buffer) -> {
        }, buffer -> new TerrasteelCreateBurstMesssage(), (msg, ctx) -> ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null) {
                if (hasItemInHand(sender, Registration.terrasteel_aiot.get())) {
                    ((ItemTerraSteelAIOT) Registration.terrasteel_aiot.get()).trySpawnBurst(sender);
                }
//                else if (ModList.get().isLoaded("mythicbotany") && hasItemInHand(sender, Registration.alfsteel_aiot.get())) {
//                    ((ItemAlfsteelAIOT) Registration.alfsteel_aiot.get()).trySpawnBurst(sender);
//                } TODO Alfsteel
            }
        }), Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    private static boolean hasItemInHand(ServerPlayer sender, ItemLike item) {
        return sender.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == item
                || sender.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == item;
    }
}

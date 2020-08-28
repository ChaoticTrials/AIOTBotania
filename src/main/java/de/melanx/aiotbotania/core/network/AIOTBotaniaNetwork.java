package de.melanx.aiotbotania.core.network;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
        INSTANCE.registerMessage(discriminator++, TerrasteelCreateBurstMesssage.class, (msg, buffer) -> {}, buffer -> new TerrasteelCreateBurstMesssage(), (msg, ctx) -> ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            if (sender != null && (sender.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == Registration.terrasteel_aiot.get()
                    || sender.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() == Registration.terrasteel_aiot.get()))
                ((ItemTerraSteelAIOT) Registration.terrasteel_aiot.get()).trySpawnBurst(sender);
        }), Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}

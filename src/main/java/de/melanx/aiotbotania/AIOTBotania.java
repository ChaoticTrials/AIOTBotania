package de.melanx.aiotbotania;

import de.melanx.aiotbotania.client.OreDict;
import de.melanx.aiotbotania.client.aiotbotaniaTab;
import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = AIOTBotania.MODID, version = "0.1", name = "AIOT Botania", dependencies = "required-after:botania")

public class AIOTBotania {

    public static final String MODID = "aiotbotania";
    public static final aiotbotaniaTab creativeTab = new aiotbotaniaTab();

    @SidedProxy(clientSide = "de.melanx.aiotbotania.proxy.ClientProxy", serverSide = "de.melanx.aiotbotania.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(MODID + " is loading");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDict.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println(MODID + " is finished.");
    }

}

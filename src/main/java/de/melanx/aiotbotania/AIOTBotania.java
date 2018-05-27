package de.melanx.aiotbotania;

import de.melanx.aiotbotania.client.OreDict;
import de.melanx.aiotbotania.client.aiotbotaniaTab;
import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

@Mod(modid = AIOTBotania.MODID, name = AIOTBotania.NAME, version = AIOTBotania.VERSION, dependencies = AIOTBotania.DEPS)

public class AIOTBotania {

    public static final String MODID = "aiotbotania";
    public static final String NAME = "AIOT Botania";
    public static final String VERSION = "@VERSION@";
    public static final String DEPS = "required-after:botania";
    public static final aiotbotaniaTab creativeTab = new aiotbotaniaTab();



    @SidedProxy(clientSide = "de.melanx.aiotbotania.proxy.ClientProxy", serverSide = "de.melanx.aiotbotania.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.init();

            for(Item item : ModItems.ITEMS_TO_REGISTER) {
                System.out.println(item.getUnlocalizedName());
                event.getRegistry().register(item);
            }
        }

        @SubscribeEvent
        public static  void registerModels(RegistryEvent evemt) {
            for(Map.Entry<ItemStack, ModelResourceLocation> entry : ModItems.MODEL_LOCATIONS.entrySet()) {
                ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
            }
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

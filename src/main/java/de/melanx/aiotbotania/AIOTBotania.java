package de.melanx.aiotbotania;

import de.melanx.aiotbotania.blocks.ModBlocks;
import de.melanx.aiotbotania.client.aiotbotaniaTab;
import de.melanx.aiotbotania.crafting.CraftingRecipes;
import de.melanx.aiotbotania.items.ModItems;
import de.melanx.aiotbotania.lexicon.LexiconData;
import de.melanx.aiotbotania.proxy.CommonProxy;
import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
    static final String NAME = "AIOT Botania";
    static final String VERSION = "@VERSION@";
    static final String DEPS = "required-after:botania";
    public static final aiotbotaniaTab creativeTab = new aiotbotaniaTab();

    @SidedProxy(clientSide = "de.melanx.aiotbotania.proxy.ClientProxy", serverSide = "de.melanx.aiotbotania.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.init();

            for(Item item : Registry.ITEMS_TO_REGISTER) {
                event.getRegistry().register(item);
                System.out.println(item.getRegistryName());
            }
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.init();

            for(Block block : Registry.BLOCKS_TO_REGISTER) {
                event.getRegistry().register(block);
                System.out.println(block.getRegistryName());
            }
        }

        @SubscribeEvent
        public static  void registerModels(ModelRegistryEvent event) {
            for(Map.Entry<ItemStack, ModelResourceLocation> entry : Registry.MODEL_LOCATIONS.entrySet()) {
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
        LexiconData.init();
        CraftingRecipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println(MODID + " is finished.");
    }

}

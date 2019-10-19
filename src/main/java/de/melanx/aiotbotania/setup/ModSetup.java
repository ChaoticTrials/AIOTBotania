package de.melanx.aiotbotania.setup;

import de.melanx.aiotbotania.capabilities.FarmlandData;
import de.melanx.aiotbotania.capabilities.FarmlandDataStorage;
import de.melanx.aiotbotania.util.CreativeTab;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ModSetup {
    private Logger logger;
    private ItemGroup creativeTab;

    public ModSetup() {
        // TODO no external CreativeTab class but a simple anon class
        creativeTab = new CreativeTab();
//        creativeTab = new ItemGroup(AIOTBotania.MODID) {
//            @Override
//            public ItemStack createIcon() {
//                return new ItemStack(ModItems.elementium_aiot);
//            }
//        };

        logger = LogManager.getLogger();
    }

    public void init(FMLCommonSetupEvent e) {
        // waiting for Botania LexiconData.init()
        CapabilityManager.INSTANCE.register(FarmlandData.class, new FarmlandDataStorage(), FarmlandData::new);
        getLogger().info("Setup method registered.");
    }

    public Logger getLogger() {
        return logger;
    }

    public ItemGroup getTab() {
        return creativeTab;
    }
}

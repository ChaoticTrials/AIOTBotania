package de.melanx.aiotbotania.config;

import de.melanx.aiotbotania.lib.LibMisc;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler {

    public static Configuration config;

    public ConfigurationHandler(File configFile) {
        MinecraftForge.EVENT_BUS.register(this);

        config = new Configuration(configFile);
        config.load();

        redefineConfigs();
    }

    public static void redefineConfigs() {
        ConfigValues.defineConfigValues(config);

        if(config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equalsIgnoreCase(LibMisc.MODID)){
            redefineConfigs();
        }
    }

}

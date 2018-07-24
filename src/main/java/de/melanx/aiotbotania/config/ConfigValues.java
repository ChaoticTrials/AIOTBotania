package de.melanx.aiotbotania.config;

import de.melanx.aiotbotania.config.values.*;
import net.minecraftforge.common.config.Configuration;

public final class ConfigValues {

    public static void defineConfigValues(Configuration config){
        for(ConfigBoolValues currConf : ConfigBoolValues.values()){
            currConf.currentValue = config.get(currConf.category, currConf.name, currConf.defaultValue, currConf.desc).getBoolean();
        }
    }

}

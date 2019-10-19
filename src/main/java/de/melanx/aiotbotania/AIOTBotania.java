package de.melanx.aiotbotania;

import de.melanx.aiotbotania.config.ConfigHandler;
import de.melanx.aiotbotania.setup.ModSetup;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AIOTBotania.MODID)
public class AIOTBotania {
    public static final String MODID = "aiotbotania";

    public static ModSetup setup = new ModSetup();

    public static AIOTBotania instance;

    public AIOTBotania() {
        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> setup.init(event));
    }
}

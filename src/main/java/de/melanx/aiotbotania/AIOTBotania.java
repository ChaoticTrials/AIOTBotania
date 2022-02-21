package de.melanx.aiotbotania;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.config.ConfigHandler;
import de.melanx.aiotbotania.core.proxy.ClientProxy;
import de.melanx.aiotbotania.core.proxy.CommonProxy;
import de.melanx.aiotbotania.core.proxy.IProxy;
import de.melanx.aiotbotania.util.CreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AIOTBotania.MODID)
public class AIOTBotania {
    public static final String MODID = "aiotbotania";
    public static AIOTBotania instance;
    public static IProxy proxy;
    private final Logger logger;
    private final CreativeModeTab creativeTab;

    public AIOTBotania() {
        instance = this;

        this.logger = LogManager.getLogger();
        this.creativeTab = new CreativeTab();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        proxy.registerHandlers();
        Registration.init();
    }

    public Logger getLogger() {
        return this.logger;
    }

    public CreativeModeTab getTab() {
        return this.creativeTab;
    }
}

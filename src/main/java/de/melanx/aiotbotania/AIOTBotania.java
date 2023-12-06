package de.melanx.aiotbotania;

import de.melanx.aiotbotania.config.ClientConfig;
import de.melanx.aiotbotania.config.CommonConfig;
import de.melanx.aiotbotania.core.CreativeTab;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.proxy.ClientProxy;
import de.melanx.aiotbotania.core.proxy.CommonProxy;
import de.melanx.aiotbotania.core.proxy.IProxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.common.item.equipment.tool.terrasteel.TerraShattererItem;
import vazkii.botania.forge.CapabilityUtil;

@Mod(AIOTBotania.MODID)
public class AIOTBotania {

    public static final String MODID = "aiotbotania";
    public static AIOTBotania instance;
    public static IProxy proxy;
    private final Logger logger;

    public AIOTBotania() {
        instance = this;

        this.logger = LoggerFactory.getLogger(AIOTBotania.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        proxy.registerHandlers();
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachItemCaps);
        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CreativeTab::createModeTab);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void attachItemCaps(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.is(Registration.terrasteel_aiot.get()) || stack.is(Registration.alfsteel_aiot.get())) {
            event.addCapability(new ResourceLocation("botania", "mana_item"), CapabilityUtil.makeProvider(BotaniaForgeCapabilities.MANA_ITEM, new TerraShattererItem.ManaItemImpl(stack)));
        }
    }
}

package de.melanx.aiotbotania.core.proxy;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.handler.ContributorHandler;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerHandlers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
    }

    private void initAuxiliaryRender() {
        Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
        PlayerRenderer render;
        render = skinMap.get("default");
        render.addLayer(new ContributorHandler(render));

        render = skinMap.get("slim");
        render.addLayer(new ContributorHandler(render));
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ItemModelsProperties.func_239418_a_(Registration.terrasteel_hoe.get(), new ResourceLocation(AIOTBotania.MODID, "active"), (stack, world, entity) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
        ItemModelsProperties.func_239418_a_(Registration.terrasteel_shovel.get(), new ResourceLocation(AIOTBotania.MODID, "active"), (stack, world, entity) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
        ItemModelsProperties.func_239418_a_(Registration.terrasteel_aiot.get(), new ResourceLocation(AIOTBotania.MODID, "tipped"), (stack, world, entity) -> ItemTerraSteelAIOT.isTipped(stack) ? 1.0F : 0.0F);
        ItemModelsProperties.func_239418_a_(Registration.terrasteel_aiot.get(), new ResourceLocation(AIOTBotania.MODID, "active"), (stack, world, entity) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
    }


    private void loadComplete(FMLLoadCompleteEvent event) {
        DeferredWorkQueue.runLater(this::initAuxiliaryRender);
    }

}

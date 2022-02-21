package de.melanx.aiotbotania.core.proxy;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.handler.ContributorHandler;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public class ClientProxy extends CommonProxy {
    private static final ResourceLocation active = new ResourceLocation(AIOTBotania.MODID, "active");
    private static final ResourceLocation tipped = new ResourceLocation(AIOTBotania.MODID, "tipped");

    @Override
    public void registerHandlers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
    }

    private void initAuxiliaryRender(PlayerRenderer renderer, Consumer<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> consumer) {
        consumer.accept(new ContributorHandler(renderer));
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(Registration.terrasteel_hoe.get(), active, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
        ItemProperties.register(Registration.terrasteel_shovel.get(), active, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
        ItemProperties.register(Registration.terrasteel_aiot.get(), tipped, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isTipped(stack) ? 1.0F : 0.0F);
        ItemProperties.register(Registration.terrasteel_aiot.get(), active, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
        // TODO ALfsteel
//        ItemProperties.register(Registration.alfsteel_hoe.get(), active, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
//        ItemProperties.register(Registration.alfsteel_shovel.get(), active, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
//        ItemProperties.register(Registration.alfsteel_aiot.get(), tipped, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isTipped(stack) ? 1.0F : 0.0F);
//        ItemProperties.register(Registration.alfsteel_aiot.get(), active, (stack, level, entity, seed) -> ItemTerraSteelAIOT.isEnabled(stack) ? 1.0F : 0.0F);
    }

    private void loadComplete(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(modelType -> {
            if (event.getSkin(modelType) instanceof PlayerRenderer renderer) {
                renderer.addLayer(new ContributorHandler(renderer));
            }
        });
    }

}

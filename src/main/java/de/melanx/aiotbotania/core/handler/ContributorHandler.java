package de.melanx.aiotbotania.core.handler;

import com.mojang.blaze3d.platform.GlStateManager;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.IItemProvider;
import vazkii.botania.api.item.AccessoryRenderHelper;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContributorHandler extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public static final Map<String, ItemStack> contributorMap = new HashMap<>();
    private static boolean startedLoading = false;

    public ContributorHandler(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (player.isInvisible()) return;

        String name = player.getDisplayName().getString();

        GlStateManager.pushMatrix();
        AccessoryRenderHelper.translateToChest();

        firstStart();

        name = name.toLowerCase();
        if (player.isWearing(PlayerModelPart.JACKET) && contributorMap.containsKey(name))
            renderIcon(player, contributorMap.get(name));

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public static void firstStart() {
        if (!startedLoading) {
            new ThreadContributorListLoader();
            startedLoading = true;
        }
    }

    public static void load(Properties props) {
        contributorMap.clear();
        for (String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);

            try {
                int i = Integer.parseInt(value);
                if (i < 0 || i >= 4)
                    throw new NumberFormatException();
                ItemStack stack = getIcon(i);
                contributorMap.put(key, stack);
            } catch (NumberFormatException e) {
                AIOTBotania.instance.getLogger().info("Oops, a wrong number at {}. Please report on GitHub. https://www.github.com/MelanX/aiotbotania/issues", key);
            }
        }
    }

    private static ItemStack getIcon(int i) {
        switch (i) {
            case 0:
                return getItem(Registration.livingwood_aiot.get());
            case 1:
                return getItem(Registration.livingrock_aiot.get());
            case 2:
                return getItem(Registration.manasteel_aiot.get());
            case 3:
                return getItem(Registration.elementium_aiot.get());
            default:
                return getItem(vazkii.botania.common.item.ModItems.grassSeeds);
        }
    }

    private static ItemStack getItem(IItemProvider item) {
        return new ItemStack(item);
    }

    private static void renderIcon(PlayerEntity player, ItemStack stack) {
        GlStateManager.pushMatrix();

        Minecraft.getInstance().textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.translated(0.15, 1.35, 0.98);
        GlStateManager.translated(0, 1.68, 0);
        GlStateManager.scaled(0.15, 0.15, 0.15);

        if (player.isSneaking() && player.onGround) {
            GlStateManager.rotated(20, 1, 0, 0);
            GlStateManager.translated(0, -1.76, 0);
        }

        Minecraft.getInstance().getItemRenderer().renderItem(stack, player, ItemCameraTransforms.TransformType.NONE, false);
        GlStateManager.popMatrix();
    }

    private static class ThreadContributorListLoader extends Thread {

        public ThreadContributorListLoader() {
            setName("AIOT Botania Contributor Thread");
            setDaemon(true);
            setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(AIOTBotania.instance.getLogger()));
            start();
        }

        @Override
        public void run() {
            try {
                URL url = new URL("https://raw.githubusercontent.com/MelanX/aiotbotania/master/contributors.properties");
                Properties props = new Properties();
                try (InputStreamReader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
                    props.load(reader);
                    load(props);
                }
            } catch (IOException e) {
                AIOTBotania.instance.getLogger().info("Could not load contributors list. Either you're offline or github is down. Nothing to worry about, carry on~");
            }
        }

    }
}

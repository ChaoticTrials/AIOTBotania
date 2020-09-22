package de.melanx.aiotbotania.core.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.vector.Vector3f;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.item.ModItems;

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
                if (i < 0 || i >= 5)
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
            case 4:
                return getItem(Registration.terrasteel_aiot.get());
            default:
                return getItem(ModItems.grassSeeds);
        }
    }

    private static ItemStack getItem(IItemProvider item) {
        return new ItemStack(item);
    }

    private void renderIcon(MatrixStack ms, IRenderTypeBuffer buffers, PlayerEntity player, ItemStack stack) {
        ms.push();

        //noinspection deprecation
        Minecraft.getInstance().textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        ms.rotate(Vector3f.XP.rotationDegrees(180));
        ms.translate(0.15F, -0.17F, 0.13F);
        ms.scale(0.15F, 0.15F, 0.15F);

        if (player.isCrouching()) {
            ms.rotate(Vector3f.XP.rotationDegrees(30));
            ms.translate(0, -1.76, 0.2);
        }

        RenderHelper.renderItemCustomColor(player, stack, -1, ms, buffers, 15728880, OverlayTexture.NO_OVERLAY);
        ms.pop();
    }

    @Override
    public void render(MatrixStack ms, IRenderTypeBuffer buffers, int light, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player.isInvisible()) return;

        String name = player.getDisplayName().getString();

        firstStart();

        name = name.toLowerCase();
        if (player.isWearing(PlayerModelPart.JACKET) && contributorMap.containsKey(name))
            renderIcon(ms, buffers, player, contributorMap.get(name));
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

package de.melanx.aiotbotania.core.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.melanx.aiotbotania.AIOTBotania;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.client.core.helper.RenderHelper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class ContributorHandler extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public static final Map<UUID, ItemStack> contributorMap = new HashMap<>();
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

            ItemStack stack = getItem(value);
            if (stack.isEmpty()) {
                AIOTBotania.instance.getLogger().info("Oops, a wrong item at {}. Please report on GitHub. https://www.github.com/MelanX/aiotbotania/issues", key);
                continue;
            }
            UUID uuid = UUID.fromString(key);
            contributorMap.put(uuid, stack);
        }
    }

    private static ItemStack getItem(String id) {
        ResourceLocation location = new ResourceLocation(AIOTBotania.MODID, id);
        Item item = ForgeRegistries.ITEMS.getValue(location);
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

        firstStart();

        UUID uuid = player.getGameProfile().getId();
        if (player.isWearing(PlayerModelPart.JACKET) && contributorMap.containsKey(uuid))
            this.renderIcon(ms, buffers, player, contributorMap.get(uuid));
    }

    private static class ThreadContributorListLoader extends Thread {

        public ThreadContributorListLoader() {
            this.setName("AIOT Botania Contributor Thread");
            this.setDaemon(true);
            this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(AIOTBotania.instance.getLogger()));
            this.start();
        }

        @Override
        public void run() {
            try {
                URL url = new URL("https://github.com/MelanX/mod-updatechecker-files/raw/master/contributors/aiotbotania.properties");
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

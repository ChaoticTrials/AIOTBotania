package de.melanx.aiotbotania.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.client.core.helper.RenderHelper;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class ContributorHandler extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public static final Map<UUID, ItemStack> contributorMap = new HashMap<>();
    private static boolean startedLoading = false;

    public ContributorHandler(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
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

            if (value.contains("alfsteel")) { // TODO remove when alfsteel
                continue;
            }

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

    private void renderIcon(PoseStack poseStack, MultiBufferSource buffers, Player player, ItemStack stack) {
        poseStack.pushPose();

        poseStack.mulPose(Vector3f.XP.rotationDegrees(180));
        poseStack.translate(0.15F, -0.17F, 0.13F);
        poseStack.scale(0.15F, 0.15F, 0.15F);

        if (player.isCrouching()) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(30));
            poseStack.translate(0, -1.76, 0.2);
        }

        RenderHelper.renderItemCustomColor(player, stack, -1, poseStack, buffers, 15728880, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity.isInvisible()) return;

        firstStart();

        UUID uuid = livingEntity.getGameProfile().getId();
        if (livingEntity.isModelPartShown(PlayerModelPart.JACKET) && contributorMap.containsKey(uuid))
            this.renderIcon(poseStack, buffer, livingEntity, contributorMap.get(uuid));
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

package de.melanx.aiotbotania.core.proxy;

import com.google.common.collect.ImmutableMap;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.config.CommonConfig;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.core.network.AIOTBotaniaNetwork;
import de.melanx.aiotbotania.items.alfsteel.CustomPylonRepairable;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOT;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOTTipped;
import de.melanx.aiotbotania.items.terrasteel.RecipeTerraSteelAIOT;
import de.melanx.aiotbotania.items.terrasteel.RecipeTerraSteelAIOTTipped;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.commons.lang3.tuple.Pair;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.Nonnull;
import java.util.Map;

public class CommonProxy implements IProxy {

    public static final ResourceLocation TERRA_RECIPE_ID = new ResourceLocation(AIOTBotania.MODID, "recipe_terrasteel_aiot");
    public static final ResourceLocation TERRA_RECIPE_ID_TIPPED = new ResourceLocation(AIOTBotania.MODID, "recipe_terrasteel_aiot_tipped");
    public static final ResourceLocation ALFSTEEL_RECIPE_ID = new ResourceLocation(AIOTBotania.MODID, "recipe_alfsteel_aiot");
    public static final ResourceLocation ALFSTEEL_RECIPE_ID_TIPPED = new ResourceLocation(AIOTBotania.MODID, "recipe_alfsteel_aiot_tipped");

    public CommonProxy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onReload);
        MinecraftForge.EVENT_BUS.addListener(this::onTilt);
    }

    public void setup(FMLCommonSetupEvent event) {
        AIOTBotaniaNetwork.registerPackets();

        if (ModList.get().isLoaded("mythicbotany")) {
            CustomPylonRepairable.pylonRepairable().run();
        }

        Registration.registerDispenseBehavior();
    }

    public void onReload(AddReloadListenerEvent event) {
        event.addListener(new SimplePreparableReloadListener<>() {

            @Nonnull
            @Override
            protected Object prepare(@Nonnull ResourceManager resourceManager, @Nonnull ProfilerFiller profiler) {
                return new Object();
            }

            @Override
            protected void apply(@Nonnull Object object, @Nonnull ResourceManager resourceManager, @Nonnull ProfilerFiller profiler) {
                if (CommonConfig.terraAiot.get() || ModList.get().isLoaded("mythicbotany")) {
                    RecipeManager recipeManager = event.getServerResources().getRecipeManager();
                    Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, recipeManager, "f_44007_");
                    //noinspection ConstantConditions
                    Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipesNew = recipes.entrySet().stream().map(entry -> {
                        if (entry.getKey() == RecipeType.CRAFTING) {
                            return Pair.of(entry.getKey(), this.insertRecipe(entry.getValue()));
                        } else {
                            return entry;
                        }
                    }).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
                    ObfuscationReflectionHelper.setPrivateValue(RecipeManager.class, recipeManager, recipesNew, "f_44007_");
                }
            }

            private Map<ResourceLocation, Recipe<?>> insertRecipe(Map<ResourceLocation, Recipe<?>> recipeMap) {
                ImmutableMap.Builder<ResourceLocation, Recipe<?>> builder = ImmutableMap.<ResourceLocation, Recipe<?>>builder().putAll(recipeMap);
                if (CommonConfig.terraAiot.get()) {
                    builder.put(TERRA_RECIPE_ID, new RecipeTerraSteelAIOT(TERRA_RECIPE_ID, "terrasteel_aiot"))
                            .put(TERRA_RECIPE_ID_TIPPED, new RecipeTerraSteelAIOTTipped(TERRA_RECIPE_ID_TIPPED, "recipe_terrasteel_aiot_tipped"));
                }
                if (ModList.get().isLoaded("mythicbotany")) {
                    builder.put(ALFSTEEL_RECIPE_ID, new RecipeAlfsteelAIOT(ALFSTEEL_RECIPE_ID, "recipe_alfsteel_aiot"))
                            .put(ALFSTEEL_RECIPE_ID_TIPPED, new RecipeAlfsteelAIOTTipped(ALFSTEEL_RECIPE_ID_TIPPED, "recipe_alfsteel_aiot_tipped"));
                }
                return builder.build();
            }
        });
    }

    public void onTilt(BlockEvent.BlockToolModificationEvent event) {
        UseOnContext context = event.getContext();

        if (event.getToolAction() != ToolActions.HOE_TILL || context.getLevel().isClientSide) {
            return;
        }

        ItemStack stack = context.getItemInHand();
        if (stack.getItem() == BotaniaItems.elementiumHoe || stack.getItem() == BotaniaItems.manasteelHoe) {
            InteractionResult resultType = ToolUtil.hoeUse(context, stack.getItem() == BotaniaItems.elementiumHoe, false);
            if (resultType != InteractionResult.PASS) {
                event.setCanceled(true);
            }
        }
    }
}

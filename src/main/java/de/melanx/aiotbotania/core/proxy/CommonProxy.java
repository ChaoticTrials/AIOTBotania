package de.melanx.aiotbotania.core.proxy;

import com.google.common.collect.ImmutableMap;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.config.ConfigHandler;
import de.melanx.aiotbotania.core.network.AIOTBotaniaNetwork;
import de.melanx.aiotbotania.items.alfsteel.CustomPylonRepairable;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOT;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOTTipped;
import de.melanx.aiotbotania.items.terrasteel.RecipeTerraSteelAIOT;
import de.melanx.aiotbotania.items.terrasteel.RecipeTerraSteelAIOTTipped;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class CommonProxy implements IProxy {

    public static final ResourceLocation TERRA_RECIPE_ID = new ResourceLocation(AIOTBotania.MODID, "recipe_terrasteel_aiot");
    public static final ResourceLocation TERRA_RECIPE_ID_TIPPED = new ResourceLocation(AIOTBotania.MODID, "recipe_terrasteel_aiot_tipped");
    public static final ResourceLocation ALFSTEEL_RECIPE_ID = new ResourceLocation(AIOTBotania.MODID, "recipe_alfsteel_aiot");
    public static final ResourceLocation ALFSTEEL_RECIPE_ID_TIPPED = new ResourceLocation(AIOTBotania.MODID, "recipe_alfsteel_aiot_tipped");

    public CommonProxy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::startServer);
    }

    public void setup(FMLCommonSetupEvent event) {
        AIOTBotaniaNetwork.registerPackets();

        if (ModList.get().isLoaded("mythicbotany")) {
            CustomPylonRepairable.pylonRepairable().run();
        }
    }

    public void startServer(FMLServerStartingEvent event) {
        if (ConfigHandler.COMMON.TERRA_AIOT.get() || ModList.get().isLoaded("mythicbotany")) {
            RecipeManager rm = event.getServer().getRecipeManager();
            Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, rm, "field_199522_d");
            @SuppressWarnings({"UnstableApiUsage", "ConstantConditions"})
            Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesNew = recipes.entrySet().stream().map(entry -> {
                if (entry.getKey() == IRecipeType.CRAFTING) {
                    return Pair.of(entry.getKey(), insertRecipe(entry.getValue()));
                } else {
                    return entry;
                }
            }).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
            ObfuscationReflectionHelper.setPrivateValue(RecipeManager.class, rm, recipesNew, "field_199522_d");
        }
    }

    private Map<ResourceLocation, IRecipe<?>> insertRecipe(Map<ResourceLocation, IRecipe<?>> recipeMap) {
        ImmutableMap.Builder<ResourceLocation, IRecipe<?>> builder = ImmutableMap.<ResourceLocation, IRecipe<?>>builder().putAll(recipeMap);
        if (ConfigHandler.COMMON.TERRA_AIOT.get()) {
            builder.put(TERRA_RECIPE_ID, new RecipeTerraSteelAIOT(TERRA_RECIPE_ID, "terrasteel_aiot"))
                    .put(TERRA_RECIPE_ID_TIPPED, new RecipeTerraSteelAIOTTipped(TERRA_RECIPE_ID_TIPPED, "recipe_terrasteel_aiot_tipped"));
        }
        if (ModList.get().isLoaded("mythicbotany")) {
            builder.put(ALFSTEEL_RECIPE_ID, new RecipeAlfsteelAIOT(ALFSTEEL_RECIPE_ID, "recipe_alfsteel_aiot"))
                    .put(ALFSTEEL_RECIPE_ID_TIPPED, new RecipeAlfsteelAIOTTipped(ALFSTEEL_RECIPE_ID_TIPPED, "recipe_alfsteel_aiot_tipped"));
        }
        return builder.build();
    }
}

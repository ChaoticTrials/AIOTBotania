package de.melanx.aiotbotania.crafting;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID)
public final class CraftingRecipes {

    public static ResourceLocation recipeManaHoe;
    public static ResourceLocation recipeManaAIOT;
    public static ResourceLocation recipeElementiumHoe;
    public static ResourceLocation recipeElementiumAIOT;

    public static void init() {
        recipeManaHoe = path("manahoe");
        recipeManaAIOT = path("manaaiot");
        recipeElementiumHoe = path("elementiumhoe");
        recipeElementiumAIOT = path("elementiumaiot");
    }

    private static ResourceLocation path(String path) {
        return new ResourceLocation(AIOTBotania.MODID, path);
    }

}
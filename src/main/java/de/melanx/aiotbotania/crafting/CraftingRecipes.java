package de.melanx.aiotbotania.crafting;

import de.melanx.aiotbotania.lib.LibMisc;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LibMisc.MODID)
public final class CraftingRecipes {

    // Livingwood
    public static ResourceLocation recipeWoodSword;
    public static ResourceLocation recipeWoodAxe;
    public static ResourceLocation recipeWoodPickaxe;
    public static ResourceLocation recipeWoodShovel;
    public static ResourceLocation recipeWoodHoe;
    public static ResourceLocation recipeWoodAIOT;

    // Livingrock


    // Manasteel
    public static ResourceLocation recipeManaHoe;
    public static ResourceLocation recipeManaAIOT;

    // Elementium
    public static ResourceLocation recipeElementiumHoe;
    public static ResourceLocation recipeElementiumAIOT;

    public static void init() {
        recipeWoodSword = path("woodsword");
        recipeWoodAxe = path("woodaxe");
        recipeWoodPickaxe = path("woodpickaxe");
        recipeWoodShovel = path("woodshovel");
        recipeWoodHoe = path("woodhoe");
        recipeWoodAIOT = path("woodaiot");

        recipeManaHoe = path("manahoe");
        recipeManaAIOT = path("manaaiot");
        recipeElementiumHoe = path("elementiumhoe");
        recipeElementiumAIOT = path("elementiumaiot");
    }

    private static ResourceLocation path(String path) {
        return new ResourceLocation(LibMisc.MODID, path);
    }

}
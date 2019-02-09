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
    public static ResourceLocation recipeRockSword;
    public static ResourceLocation recipeRockAxe;
    public static ResourceLocation recipeRockPickaxe;
    public static ResourceLocation recipeRockShovel;
    public static ResourceLocation recipeRockHoe;
    public static ResourceLocation recipeRockAIOT;

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

        recipeRockSword = path("rocksword");
        recipeRockAxe = path("rockaxe");
        recipeRockPickaxe = path("rockpickaxe");
        recipeRockShovel = path("rockshovel");
        recipeRockHoe = path("rockhoe");
        recipeRockAIOT = path("rockaiot");

        recipeManaHoe = path("manahoe");
        recipeManaAIOT = path("manaaiot");

        recipeElementiumHoe = path("elementiumhoe");
        recipeElementiumAIOT = path("elementiumaiot");
    }

    private static ResourceLocation path(String path) {
        return new ResourceLocation(LibMisc.MODID, path);
    }

}
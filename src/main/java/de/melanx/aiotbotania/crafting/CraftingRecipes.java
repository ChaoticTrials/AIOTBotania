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
        recipeWoodSword = path("livingwood_sword");
        recipeWoodAxe = path("livingwood_axe");
        recipeWoodPickaxe = path("livingwood_pickaxe");
        recipeWoodShovel = path("livingwood_shovel");
        recipeWoodHoe = path("livingwood_hoe");
        recipeWoodAIOT = path("livingwood_aiot");

        recipeRockSword = path("livingrock_sword");
        recipeRockAxe = path("livingrock_axe");
        recipeRockPickaxe = path("livingrock_pickaxe");
        recipeRockShovel = path("livingrock_shovel");
        recipeRockHoe = path("livingrock_hoe");
        recipeRockAIOT = path("livingrock_aiot");

        recipeManaHoe = path("manasteel_hoe");
        recipeManaAIOT = path("manasteel_aiot");

        recipeElementiumHoe = path("elementium_hoe");
        recipeElementiumAIOT = path("elementium_aiot");
    }

    private static ResourceLocation path(String path) {
        return new ResourceLocation(LibMisc.MODID, path);
    }
}

package de.melanx.aiotbotania.compat;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.recipes.AlfsteelAIOTTippedWrapper;
import de.melanx.aiotbotania.compat.recipes.AlfsteelAIOTWrapper;
import de.melanx.aiotbotania.compat.recipes.TerraSteelAIOTWrapper;
import de.melanx.aiotbotania.compat.recipes.TerrasteelAIOTTippedWrapper;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOT;
import de.melanx.aiotbotania.items.alfsteel.RecipeAlfsteelAIOTTipped;
import de.melanx.aiotbotania.items.terrasteel.RecipeTerraSteelAIOT;
import de.melanx.aiotbotania.items.terrasteel.RecipeTerraSteelAIOTTipped;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AIOTBotania.MODID, "jei_plugin");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(RecipeAlfsteelAIOT.class, AlfsteelAIOTWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(RecipeAlfsteelAIOTTipped.class, AlfsteelAIOTTippedWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(RecipeTerraSteelAIOT.class, TerraSteelAIOTWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(RecipeTerraSteelAIOTTipped.class, TerrasteelAIOTTippedWrapper::new);
    }
}

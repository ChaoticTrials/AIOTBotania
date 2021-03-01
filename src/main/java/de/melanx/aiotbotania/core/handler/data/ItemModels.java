package de.melanx.aiotbotania.core.handler.data;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModels extends ItemModelProvider {
    public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, AIOTBotania.MODID, helper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : Registration.ITEMS.getEntries())
            if (item.get() != Registration.terrasteel_aiot.get()
                    && item.get() != Registration.terrasteel_shovel.get()
                    && item.get() != Registration.terrasteel_hoe.get()
                    && !(item.get() instanceof MythicBotany))
                // The terra tools have a custom model to display it's tipped and AOE-active state.
                this.generateItem(item.get());
    }

    private void generateItem(Item item) {
        String path = item.getRegistryName().getPath();
        this.getBuilder(path).parent(this.getExistingFile(this.mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }

    @Override
    public String getName() {
        return "Item Models";
    }
}

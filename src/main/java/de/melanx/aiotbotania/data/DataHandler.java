package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new ModLootTables(generator));
            ModTags.Blocks blockTagsProvider = new ModTags.Blocks(generator, helper);
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new ModTags.Items(generator, blockTagsProvider, helper));
        }
        if (event.includeClient()) {
            generator.addProvider(new BlockStates(generator, helper));
            generator.addProvider(new ItemModels(generator, helper));
        }
    }

}

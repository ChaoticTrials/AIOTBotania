package de.melanx.aiotbotania.core.handler;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.handler.data.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            gen.addProvider(new Recipes(gen));
            gen.addProvider(new ModLootTables(gen));
            ModTags.Blocks blockTagsProvider = new ModTags.Blocks(gen, helper);
            gen.addProvider(blockTagsProvider);
            gen.addProvider(new ModTags.Items(gen, blockTagsProvider, helper));
        }
        if (event.includeClient()) {
            gen.addProvider(new BlockStates(gen, helper));
            gen.addProvider(new ItemModels(gen, helper));
        }
    }

}

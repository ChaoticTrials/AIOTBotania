package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new LootModifierProvider(generator));
        generator.addProvider(event.includeServer(), new Recipes(generator));
        generator.addProvider(event.includeServer(), new ModLootTables(generator));
        ModTags.Blocks blockTagsProvider = new ModTags.Blocks(generator, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModTags.Items(generator, blockTagsProvider, helper));

        generator.addProvider(event.includeClient(), new BlockStates(generator, helper));
        generator.addProvider(event.includeClient(), new ItemModels(generator, helper));
    }
}

package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(event.includeServer(), new LootModifierProvider(packOutput));
        generator.addProvider(event.includeServer(), new Recipes(packOutput));
        ModTags.Blocks blockTagsProvider = new ModTags.Blocks(packOutput, event.getLookupProvider(), helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModTags.Items(packOutput, event.getLookupProvider(), blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModLootTables::new, LootContextParamSets.BLOCK)
        )));

        generator.addProvider(event.includeClient(), new BlockStates(packOutput, helper));
        generator.addProvider(event.includeClient(), new ItemModels(packOutput, helper));
    }
}

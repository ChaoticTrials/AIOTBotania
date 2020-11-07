package de.melanx.aiotbotania.core.handler;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.handler.data.BlockStates;
import de.melanx.aiotbotania.core.handler.data.ItemModels;
import de.melanx.aiotbotania.core.handler.data.LootTables;
import de.melanx.aiotbotania.core.handler.data.Recipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AIOTBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {
        DataGenerator gen = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();

        if (e.includeServer()) {
            gen.addProvider(new Recipes(gen));
            gen.addProvider(new LootTables(gen));
        }
        if (e.includeClient()) {
            gen.addProvider(new BlockStates(gen, helper));
            gen.addProvider(new ItemModels(gen, helper));
        }
    }

}

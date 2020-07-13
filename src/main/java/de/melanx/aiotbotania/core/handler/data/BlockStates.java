package de.melanx.aiotbotania.core.handler.data;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, AIOTBotania.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Block block = Registration.custom_farmland.get();

        ModelFile model = models().singleTexture(block.getRegistryName().getPath(), mcLoc("block/template_farmland"), "top", mcLoc("block/farmland_moist"))
                .texture("dirt", mcLoc("block/dirt"))
                .texture("particle", mcLoc("block/farmland_moist"));
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }
}

package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockStates extends BlockStateProvider {

    public BlockStates(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, AIOTBotania.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Block block = Registration.custom_farmland.get();

        //noinspection ConstantConditions
        ModelFile model = this.models().singleTexture(ForgeRegistries.BLOCKS.getKey(block).getPath(),
                        this.mcLoc("block/template_farmland"),
                        "top", this.mcLoc("block/farmland_moist"))
                .texture("dirt", this.mcLoc("block/dirt"))
                .texture("particle", this.mcLoc("block/farmland_moist"));
        this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }
}

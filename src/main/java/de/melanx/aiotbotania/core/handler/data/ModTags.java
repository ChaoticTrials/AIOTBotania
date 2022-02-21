package de.melanx.aiotbotania.core.handler.data;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModTags {

    public static class Blocks extends BlockTagsProvider {

        public static final Tag.Named<Block> MINEABLE_WITH_AIOT = BlockTags.bind("forge:mineable/aiot");

        public Blocks(DataGenerator generator, @Nullable ExistingFileHelper helper) {
            super(generator, AIOTBotania.MODID, helper);
        }

        @Override
        protected void addTags() {
            this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(Registration.custom_farmland.get());
            //noinspection unchecked
            this.tag(MINEABLE_WITH_AIOT).addTags(
                    BlockTags.MINEABLE_WITH_AXE,
                    BlockTags.MINEABLE_WITH_HOE,
                    BlockTags.MINEABLE_WITH_PICKAXE,
                    BlockTags.MINEABLE_WITH_SHOVEL
            );
        }
    }

    public static class Items extends ItemTagsProvider {

        public Items(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper helper) {
            super(generator, blockTagsProvider, AIOTBotania.MODID, helper);
        }

        @Override
        protected void addTags() {
            this.tag(Tags.Items.SHEARS).add(Registration.livingwood_shears.get(), Registration.livingrock_shears.get());
        }
    }
}

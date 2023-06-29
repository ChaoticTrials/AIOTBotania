package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModTags {

    public static class Blocks extends BlockTagsProvider {

        public static final TagKey<Block> MINEABLE_WITH_AIOT = BlockTags.create(new ResourceLocation("forge", "mineable/aiot"));

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

        public static final TagKey<Item> TOOLS_AIOTS = ItemTags.create(new ResourceLocation("forge", "tools/aiots"));

        public Items(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper helper) {
            super(generator, blockTagsProvider, AIOTBotania.MODID, helper);
        }

        @Override
        protected void addTags() {
            this.tag(Tags.Items.SHEARS).add(Registration.livingwood_shears.get(), Registration.livingrock_shears.get());
            this.tag(Tags.Items.TOOLS_SWORDS).add(Registration.livingwood_sword.get(), Registration.livingrock_sword.get());
            this.tag(Tags.Items.TOOLS_AXES).add(Registration.livingwood_axe.get(), Registration.livingrock_axe.get());
            this.tag(Tags.Items.TOOLS_PICKAXES).add(Registration.livingwood_pickaxe.get(), Registration.livingrock_pickaxe.get());
            this.tag(Tags.Items.TOOLS_SHOVELS).add(Registration.livingwood_shovel.get(), Registration.livingrock_shovel.get(), Registration.terrasteel_shovel.get(), Registration.alfsteel_shovel.get());
            this.tag(Tags.Items.TOOLS_HOES).add(Registration.livingwood_hoe.get(), Registration.livingrock_hoe.get(), Registration.terrasteel_hoe.get(), Registration.alfsteel_hoe.get());
            this.tag(TOOLS_AIOTS).add(
                    Registration.livingwood_aiot.get(),
                    Registration.livingrock_aiot.get(),
                    Registration.manasteel_aiot.get(),
                    Registration.elementium_aiot.get(),
                    Registration.terrasteel_aiot.get(),
                    Registration.alfsteel_aiot.get()
            );
            this.tag(Tags.Items.TOOLS).addTag(TOOLS_AIOTS);
        }
    }
}

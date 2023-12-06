package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModTags {

    public static class Blocks extends BlockTagsProvider {

        public static final TagKey<Block> MINEABLE_WITH_AIOT = BlockTags.create(new ResourceLocation("forge", "mineable/aiot"));

        public Blocks(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper helper) {
            super(packOutput, lookupProvider, AIOTBotania.MODID, helper);
        }

        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(Registration.custom_farmland.get());
            //noinspection unchecked
            this.tag(MINEABLE_WITH_AIOT).addTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.MINEABLE_WITH_HOE, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL);
        }
    }

    public static class Items extends ItemTagsProvider {

        public static final TagKey<Item> TOOLS_AIOTS = ItemTags.create(new ResourceLocation("forge", "tools/aiots"));

        public Items(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagsProvider, @Nullable ExistingFileHelper helper) {
            super(packOutput, lookupProvider, blockTagsProvider, AIOTBotania.MODID, helper);
        }

        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            this.tag(Tags.Items.SHEARS).add(Registration.livingwood_shears.get(), Registration.livingrock_shears.get());
            this.tag(ItemTags.SWORDS).add(Registration.livingwood_sword.get(), Registration.livingrock_sword.get());
            this.tag(ItemTags.AXES).add(Registration.livingwood_axe.get(), Registration.livingrock_axe.get());
            this.tag(ItemTags.PICKAXES).add(Registration.livingwood_pickaxe.get(), Registration.livingrock_pickaxe.get());
            this.tag(ItemTags.SHOVELS).add(Registration.livingwood_shovel.get(), Registration.livingrock_shovel.get(), Registration.terrasteel_shovel.get(), Registration.alfsteel_shovel.get());
            this.tag(ItemTags.HOES).add(Registration.livingwood_hoe.get(), Registration.livingrock_hoe.get(), Registration.terrasteel_hoe.get(), Registration.alfsteel_hoe.get());
            this.tag(TOOLS_AIOTS).add(Registration.livingwood_aiot.get(), Registration.livingrock_aiot.get(), Registration.manasteel_aiot.get(), Registration.elementium_aiot.get(), Registration.terrasteel_aiot.get(), Registration.alfsteel_aiot.get());
            this.tag(Tags.Items.TOOLS).addTag(TOOLS_AIOTS);
        }
    }
}

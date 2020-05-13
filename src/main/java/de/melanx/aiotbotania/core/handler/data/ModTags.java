package de.melanx.aiotbotania.core.handler.data;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static class Items {
        public static final Tag<Item> SHEARS = forgeTag("shears");

        private static Tag<Item> forgeTag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
        }
    }

    public static class ModItemTags extends ItemTagsProvider {
        public ModItemTags(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            getBuilder(Items.SHEARS).add(Registration.livingwood_shears.get(), Registration.livingrock_shears.get());
        }
    }
}

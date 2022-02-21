package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.CompatItem;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;

public class CreativeTab extends CreativeModeTab {

    public CreativeTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    @Nonnull
    public ItemStack makeIcon() {
        return new ItemStack(Registration.elementium_aiot.get());
    }

    @Override
    public void fillItemList(@Nonnull NonNullList<ItemStack> items) {
        Registration.ITEMS.getEntries().stream().filter(object -> {
            Item item = object.get();
            return !(item instanceof CompatItem) || (ModList.get().isLoaded("mythicbotany") && item instanceof MythicBotany);
        }).forEach(entry -> entry.get().fillItemCategory(this, items));
    }
}

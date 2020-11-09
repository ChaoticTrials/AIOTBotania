package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.CompatItem;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {

    public CreativeTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    @Nonnull
    public ItemStack createIcon() {
        return new ItemStack(Registration.elementium_aiot.get());
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        Registration.ITEMS.getEntries().stream().filter(object -> {
            Item item = object.get();
            return !(item instanceof CompatItem) || (ModList.get().isLoaded("mythicbotany") && item instanceof MythicBotany);
        }).forEach(entry -> entry.get().fillItemGroup(this, list));
    }
}

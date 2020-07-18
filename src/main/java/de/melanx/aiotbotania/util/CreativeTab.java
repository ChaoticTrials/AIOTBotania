package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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
        Registration.ITEMS.getEntries().forEach(entry -> entry.get().fillItemGroup(this, list));
    }
}

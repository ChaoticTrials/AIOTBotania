package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {

    private NonNullList<ItemStack> list;

    public CreativeTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Registration.elementium_aiot.get());
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        addItem(Registration.livingwood_shovel.get());
        addItem(Registration.livingwood_pickaxe.get());
        addItem(Registration.livingwood_axe.get());
        addItem(Registration.livingwood_sword.get());
        addItem(Registration.livingwood_hoe.get());
        addItem(Registration.livingwood_aiot.get());
        addItem(Registration.livingwood_shears.get());

        addItem(Registration.livingrock_shovel.get());
        addItem(Registration.livingrock_pickaxe.get());
        addItem(Registration.livingrock_axe.get());
        addItem(Registration.livingrock_sword.get());
        addItem(Registration.livingrock_hoe.get());
        addItem(Registration.livingrock_aiot.get());
        addItem(Registration.livingrock_shears.get());

        addItem(Registration.manasteel_hoe.get());
        addItem(Registration.manasteel_aiot.get());

        addItem(Registration.elementium_hoe.get());
        addItem(Registration.elementium_aiot.get());
    }

    private void addItem(IItemProvider item) {
        item.asItem().fillItemGroup(this, list);
    }

}

package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

import static de.melanx.aiotbotania.config.ConfigHandler.*;

public class CreativeTab extends ItemGroup {

    private NonNullList<ItemStack> list;

    public CreativeTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    public ItemStack createIcon() {
        if (COMMON.MANASTEEL_AIOT.get())
            return new ItemStack(ModItems.elementium_aiot);
        else {
            if (COMMON.ELEMENTIUM_AIOT.get())
                return new ItemStack(ModItems.manasteel_aiot);
            else
                return new ItemStack(ModItems.elementium_hoe);
        }
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        if (COMMON.LIVINGWOOD_TOOLS.get()) {
            addItem(ModItems.livingwood_shovel);
            addItem(ModItems.livingwood_pickaxe);
            addItem(ModItems.livingwood_axe);
            addItem(ModItems.livingwood_sword);
            addItem(ModItems.livingwood_hoe);
            if (COMMON.LIVINGWOOD_AIOT.get())
                addItem(ModItems.livingwood_aiot);
            addItem(ModItems.livingwood_shears);
        }

        if (COMMON.LIVINGROCK_TOOLS.get()) {
            addItem(ModItems.livingrock_shovel);
            addItem(ModItems.livingrock_pickaxe);
            addItem(ModItems.livingrock_axe);
            addItem(ModItems.livingrock_sword);
            addItem(ModItems.livingrock_hoe);
            if (COMMON.LIVINGROCK_AIOT.get())
                addItem(ModItems.livingrock_aiot);
            addItem(ModItems.livingrock_shears);
        }

        addItem(ModItems.manasteel_hoe);
        if (COMMON.MANASTEEL_AIOT.get())
            addItem(ModItems.manasteel_aiot);

        addItem(ModItems.elementium_hoe);
        if (COMMON.ELEMENTIUM_AIOT.get())
            addItem(ModItems.elementium_aiot);
    }

    private void addItem(IItemProvider item) {
        item.asItem().fillItemGroup(this, list);
    }

}

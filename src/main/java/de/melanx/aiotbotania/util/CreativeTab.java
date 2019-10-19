package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.config.ConfigHandler;
import de.melanx.aiotbotania.items.ModItems;
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
        if (ConfigHandler.COMMON.MANASTEEL_AIOT.get())
            return new ItemStack(ModItems.elementium_aiot);
        else {
            if (ConfigHandler.COMMON.ELEMENTIUM_AIOT.get())
                return new ItemStack(ModItems.manasteel_aiot);
            else
                return new ItemStack(ModItems.elementium_hoe);
        }
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        if (ConfigHandler.COMMON.LIVINGWOOD_TOOLS.get()) {
            addItem(ModItems.livingwood_shovel);
            addItem(ModItems.livingwood_pickaxe);
            addItem(ModItems.livingwood_axe);
            addItem(ModItems.livingwood_sword);
            addItem(ModItems.livingwood_hoe);
            if (ConfigHandler.COMMON.LIVINGWOOD_AIOT.get())
                addItem(ModItems.livingwood_aiot);
        }

        if (ConfigHandler.COMMON.LIVINGROCK_TOOLS.get()) {
            addItem(ModItems.livingrock_shovel);
            addItem(ModItems.livingrock_pickaxe);
            addItem(ModItems.livingrock_axe);
            addItem(ModItems.livingrock_sword);
            addItem(ModItems.livingrock_hoe);
            if (ConfigHandler.COMMON.LIVINGROCK_AIOT.get())
                addItem(ModItems.livingrock_aiot);
        }

        addItem(ModItems.manasteel_hoe);
        if (ConfigHandler.COMMON.MANASTEEL_AIOT.get())
            addItem(ModItems.manasteel_aiot);

        addItem(ModItems.elementium_hoe);
        if (ConfigHandler.COMMON.ELEMENTIUM_AIOT.get())
            addItem(ModItems.elementium_aiot);
    }

    private void addItem(IItemProvider item) {
        item.asItem().fillItemGroup(this, list);
    }

}

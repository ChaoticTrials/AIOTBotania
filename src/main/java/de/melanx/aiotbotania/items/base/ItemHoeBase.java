package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class ItemHoeBase extends ItemHoe {

    public ItemHoeBase(String name, ToolMaterial mat) {
        super(mat);
        ModItems.registerItem(this, name);
        registerItemModel();
    }

    private void registerItemModel() {
        ModItems.MODEL_LOCATIONS.put(new ItemStack(this), new ModelResourceLocation(Objects.requireNonNull(this.getRegistryName()), "inventory"));
    }
}

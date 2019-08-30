package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ItemBlockBase extends BlockItem {

    public ItemBlockBase(Block block) {
        super(block, new Item.Properties().group(AIOTBotania.aiotItemGroup));
    }

}

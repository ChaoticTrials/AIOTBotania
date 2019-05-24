package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block, new Item.Properties().group(AIOTBotania.aiotItemGroup));
    }

}

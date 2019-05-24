package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;

public class BlockSuperFarmland extends BlockFarmland {

    BlockSuperFarmland() {
        super(Block.Properties.create(Material.GROUND).hardnessAndResistance(0.6F));
        Registry.registerBlock(this, "super_farmland", null);
    }

    private static boolean hasWater() {
        return true;
    }

}

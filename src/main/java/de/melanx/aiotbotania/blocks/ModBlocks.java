package de.melanx.aiotbotania.blocks;

import net.minecraft.block.Block;

public class ModBlocks {

    public static Block superFarmland;

    public static void init() {
        superFarmland = new BlockSuperFarmland();
    }

}

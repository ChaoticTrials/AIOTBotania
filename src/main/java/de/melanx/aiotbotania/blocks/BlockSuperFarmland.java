package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

import java.util.Random;

public class BlockSuperFarmland extends BlockFarmland implements ITickable {

    BlockSuperFarmland() {
        super(Block.Properties.create(Material.GROUND).hardnessAndResistance(0.6F));
        Registry.registerBlock(this, "super_farmland", null);
    }

    private static boolean hasWater() {
        return true;
    }

    private int ticks = 0;
    BlockPos pos;

    @Override
    public void tick() {
        ++ticks;
        double d0 = (double)pos.getX() + 0.5D + (Math.random() - 0.5D) * 0.9D;
        double d1 = (double)((float)pos.getY() + 1.0625F);
        double d2 = (double)pos.getZ() + 0.5D + (Math.random() - 0.5D) * 0.9D;
        float f1 = 1.0F;
        float f2 = 0.15F;
        float f3 = 0.9F;
        Botania.proxy.sparkleFX(d0 + (Math.random() - 0.5) * 0.5, d1, d2 + (Math.random() - 0.5) * 0.5, f1, f2, f3, (float) Math.random(), 8);

    }

}

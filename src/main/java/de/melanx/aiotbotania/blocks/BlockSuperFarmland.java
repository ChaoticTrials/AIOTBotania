package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

import java.util.Random;

public class BlockSuperFarmland extends FarmlandBlock implements ITickable {

    BlockSuperFarmland() {
        super(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.6F));
        Registry.registerBlock(this, "super_farmland", null);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.fall(fallDistance, 1.0F);
    }

    private int ticks = 0;
    BlockPos pos;

    @Override
    public void tick() {
        Vector3 centerBlock = new Vector3((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.75D + (Math.random() - 0.125D), (double) this.pos.getZ() + 0.5D);
        double worldTime = (double) this.ticks;
        worldTime += (double) (new Random((long) this.pos.hashCode())).nextInt(1000);
        worldTime /= 5.0D;
        float r = 0.75F + (float) Math.random() * 0.05F;
        double x = (double) this.pos.getX() + 0.5D + Math.cos(worldTime) * (double) r;
        double z = (double) this.pos.getZ() + 0.5D + Math.sin(worldTime) * (double) r;
        Vector3 ourCoords = new Vector3(x, (double) this.pos.getY() + 0.25D, z);
        centerBlock = centerBlock.subtract(new Vector3(0.0D, 0.5D, 0.0D));
        Vector3 movementVector = centerBlock.subtract(ourCoords).normalize().multiply(0.2D);
        Botania.proxy.wispFX(x, (double) this.pos.getY() + 0.25D, z, (float) Math.random() * 0.25F, 0.75F + (float) Math.random() * 0.25F, (float) Math.random() * 0.25F, 0.25F + (float) Math.random() * 0.1F, -0.075F - (float) Math.random() * 0.015F);
        Botania.proxy.wispFX(x, (double) this.pos.getY() + 0.25D, z, (float) Math.random() * 0.25F, 0.75F + (float) Math.random() * 0.25F, (float) Math.random() * 0.25F, 0.25F + (float) Math.random() * 0.1F, (float) movementVector.x, (float) movementVector.y, (float) movementVector.z);
//        ++ticks;
//        double d0 = pos.getX() + 0.5D + (Math.random() - 0.5D) * 0.9D;
//        double d1 = (float) pos.getY() + 1.0625F;
//        double d2 = pos.getZ() + 0.5D + (Math.random() - 0.5D) * 0.9D;
//        float f1 = 1.0F;
//        float f2 = 0.15F;
//        float f3 = 0.9F;
//        Botania.proxy.sparkleFX(d0 + (Math.random() - 0.5) * 0.5, d1, d2 + (Math.random() - 0.5) * 0.5, f1, f2, f3, (float) Math.random(), 8);
    }
}

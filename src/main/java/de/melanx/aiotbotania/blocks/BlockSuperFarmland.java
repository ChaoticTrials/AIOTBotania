package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockSuperFarmland extends Block {

    BlockSuperFarmland() {
        super(Material.GROUND);
        Registry.registerBlock(this, "superfarmland", null);
        Registry.registerModel(this);

        this.setLightOpacity(255);
        this.setSoundType(SoundType.GROUND);
        this.setHardness(0.6F);
        this.useNeighborBrightness = true;
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nullable IBlockState state, RayTraceResult target, @Nullable World world, @Nullable BlockPos pos, EntityPlayer player) {
        return new ItemStack(Blocks.FARMLAND, 1, 0);
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.DIRT);
    }

    @Override
    public boolean isFertile(@Nullable World world, @Nullable BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSustainPlant(@Nullable IBlockState state, @Nonnull IBlockAccess world, BlockPos pos, @Nullable EnumFacing direction, IPlantable plantable) {
        EnumPlantType plantType = plantable.getPlantType(world, pos.up());

        switch (plantType) {
            case Desert:
                return true;
            case Nether:
                return false;
            case Crop:
                return true;
            case Cave:
                return true;
            case Plains:
                return true;
            case Water:
                return false;
            case Beach:
                return true;
        }

        return false;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.9D;
        double d1 = (double)((float)pos.getY() + 1.0625F);
        double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.9D;
        float f1 = 1.0F;
        float f2 = 0.15F;
        float f3 = 0.9F;
        Botania.proxy.sparkleFX(d0 + (Math.random() - 0.5) * 0.5, d1, d2 + (Math.random() - 0.5) * 0.5, f1, f2, f3, (float) Math.random(), 8);
    }
}

package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class BlockCustomFarmland extends FarmlandBlock {
    public BlockCustomFarmland(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(MOISTURE, 7)
        );
        setRegistryName(AIOTBotania.MODID, "super_farmland");
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random r) {
        // TODO add particle effects
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.fall(fallDistance, 1.0F);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(world, pos.up());
        if (plantType == PlantType.Crop) {
            return true;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }
}

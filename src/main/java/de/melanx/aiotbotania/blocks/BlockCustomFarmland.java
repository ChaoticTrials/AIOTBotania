package de.melanx.aiotbotania.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCustomFarmland extends FarmlandBlock {
    public static final BooleanProperty MANA_INFUSED = BooleanProperty.create("mana_infused");

    public BlockCustomFarmland(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(MOISTURE, 0)
                .with(MANA_INFUSED, false)
        );
        setRegistryName("minecraft", "farmland");
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random r) {
        if (!state.get(MANA_INFUSED)) {
            super.tick(state, world, pos, r);
        } else {
            // TODO add particle effects
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if(!worldIn.getBlockState(pos).get(MANA_INFUSED)) {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        } else {
            entityIn.fall(fallDistance, 1.0F);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
        builder.add(MANA_INFUSED);
    }
}

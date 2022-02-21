package de.melanx.aiotbotania.blocks;

import de.melanx.aiotbotania.core.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import vazkii.botania.client.fx.SparkleParticleData;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockCustomFarmland extends FarmBlock {
    public BlockCustomFarmland() {
        super(BlockBehaviour.Properties.copy(Blocks.FARMLAND));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(MOISTURE, 7)
        );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (ConfigHandler.CLIENT.PARTICLES.get()) {
            int r = 1;
            float g = 0.078F;
            float b = 0.576F;
            SparkleParticleData data = SparkleParticleData.sparkle((float) Math.random(), r, g, b, 3);
            level.addParticle(data, pos.getX() + Math.random(), pos.getY() + Math.random() * 1.5, pos.getZ() + Math.random(), 0, 0, 0);
        }
    }

    @Override
    public void fallOn(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockGetter level, BlockPos pos, @Nonnull Direction facing, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(level, pos.above());
        if (plantType == PlantType.CROP || plantType == PlantType.PLAINS) {
            return true;
        }
        return super.canSustainPlant(state, level, pos, facing, plantable);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public ItemStack getCloneItemStack(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ItemStack(Blocks.FARMLAND.asItem());
    }

    @Override
    public void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random rand) {
        BlockState above = level.getBlockState(pos.above());
        if (above.getBlock() instanceof CropBlock crop) {
            if (rand.nextInt(30) <= 1)
                crop.growCrops(level, pos.above(), above);
        }
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
        // no, thank you
    }
}

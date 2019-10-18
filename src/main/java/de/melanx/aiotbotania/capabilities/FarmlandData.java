package de.melanx.aiotbotania.capabilities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FarmlandData {
    private List<BlockPos> farmlandBlocks;

    public FarmlandData() {
        farmlandBlocks = new ArrayList<>();
    }

    public List<BlockPos> getAll() {
        return farmlandBlocks;
    }

    public void add(BlockPos pos) {
        farmlandBlocks.add(pos);
    }

    public void set(List<BlockPos> newPos) {
        farmlandBlocks.clear();
        farmlandBlocks.addAll(newPos);
    }

    public void moistenAll(World world) {
        List<BlockPos> kept = new ArrayList<>();
        farmlandBlocks.forEach(pos -> {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.FARMLAND) {
                kept.add(pos);
                if ((Integer) state.getValues().get(FarmlandBlock.MOISTURE) < 7) {
                    world.getWorld().setBlockState(pos, state.with(FarmlandBlock.MOISTURE, 7));
                }
            }
        });
        set(kept);
    }
}

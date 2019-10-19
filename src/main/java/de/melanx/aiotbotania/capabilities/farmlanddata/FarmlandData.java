package de.melanx.aiotbotania.capabilities.farmlanddata;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

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

    public void clean(IChunk chunk) {
        List<BlockPos> kept = new ArrayList<>();
        farmlandBlocks.forEach(pos -> {
            BlockState state = chunk.getBlockState(pos);
            if (state.getBlock() == Blocks.FARMLAND) {
                kept.add(pos);
            }
        });
        set(kept);
    }

    public List<BlockPos> cleanAndGetRest(IChunk chunk) {
        clean(chunk);
        return farmlandBlocks;
    }
}

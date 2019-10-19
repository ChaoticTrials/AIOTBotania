package de.melanx.aiotbotania.capabilities.chunkmarker;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChunkMarkerStorage implements Capability.IStorage<ChunkMarker> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<ChunkMarker> capability, ChunkMarker data, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        AtomicInteger idx = new AtomicInteger();
        data.getAll().forEach(it -> {
            nbt.putIntArray(Integer.toString(idx.getAndIncrement()), new int[]{it.x, it.z});
        });
        return nbt;
    }

    // Unchecked; may crash when format is unexpectedly wrong/different
    @Override
    public void readNBT(Capability<ChunkMarker> capability, ChunkMarker data, Direction side, INBT inbt) {
        if (inbt instanceof CompoundNBT) {
            CompoundNBT nbt = (CompoundNBT) inbt;
            List<ChunkPos> positions = new ArrayList<>();
            for (int i = 0; i < nbt.size(); i++) {
                int[] coords = nbt.getIntArray(Integer.toString(i));
                positions.add(new ChunkPos(coords[0], coords[1]));
            }
            data.set(positions);
        }
    }
}

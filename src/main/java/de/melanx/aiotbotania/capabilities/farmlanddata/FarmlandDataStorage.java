package de.melanx.aiotbotania.capabilities.farmlanddata;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FarmlandDataStorage implements Capability.IStorage<FarmlandData> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<FarmlandData> capability, FarmlandData data, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        AtomicInteger idx = new AtomicInteger();
        data.getAll().forEach(it -> {
            nbt.putIntArray(Integer.toString(idx.getAndIncrement()), new int[]{it.getX(), it.getY(), it.getZ()});
        });
        return nbt;
    }

    // Unchecked; may crash when format is unexpectedly wrong/different
    @Override
    public void readNBT(Capability<FarmlandData> capability, FarmlandData data, Direction side, INBT inbt) {
        if (inbt instanceof CompoundNBT) {
            CompoundNBT nbt = (CompoundNBT) inbt;
            List<BlockPos> positions = new ArrayList<>();
            for (int i = 0; i < nbt.size(); i++) {
                int[] coords = nbt.getIntArray(Integer.toString(i));
                positions.add(new BlockPos(coords[0], coords[1], coords[2]));
            }
            data.set(positions);
        }
    }
}

package de.melanx.aiotbotania.capabilities.chunkmarker;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class ChunkMarkerProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(ChunkMarker.class)
    public static Capability<ChunkMarker> CHUNK_MARKER_CAP;

    @SuppressWarnings("NullableProblems")
    private LazyOptional<ChunkMarker> instance = LazyOptional.of(CHUNK_MARKER_CAP::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CHUNK_MARKER_CAP ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        AtomicReference<INBT> nbt = new AtomicReference<>(null);
        instance.ifPresent(ins ->
                nbt.set(CHUNK_MARKER_CAP.getStorage().writeNBT(
                        CHUNK_MARKER_CAP,
                ins,
                null)));
        return nbt.get();
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        instance.ifPresent(ins ->
                CHUNK_MARKER_CAP.getStorage().readNBT(CHUNK_MARKER_CAP,
                ins,
                null,
                nbt
        ));
    }
}

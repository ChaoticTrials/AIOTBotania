package de.melanx.aiotbotania.capabilities.farmlanddata;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class FarmlandDataProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(FarmlandData.class)
    public static Capability<FarmlandData> FARMLAND_DATA_CAP;

    @SuppressWarnings("NullableProblems")
    private LazyOptional<FarmlandData> instance = LazyOptional.of(FARMLAND_DATA_CAP::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == FARMLAND_DATA_CAP ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        AtomicReference<INBT> nbt = new AtomicReference<>(null);
        instance.ifPresent(ins ->
                nbt.set(FARMLAND_DATA_CAP.getStorage().writeNBT(
                FARMLAND_DATA_CAP,
                ins,
                null)));
        return nbt.get();
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        instance.ifPresent(ins ->
                FARMLAND_DATA_CAP.getStorage().readNBT(FARMLAND_DATA_CAP,
                ins,
                null,
                nbt
        ));
    }
}

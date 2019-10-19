package de.melanx.aiotbotania.capabilities.chunkmarker;

import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;

public class ChunkMarker {
    private List<ChunkPos> chunks;

    public ChunkMarker() {
        chunks = new ArrayList<>();
    }

    public List<ChunkPos> getAll() {
        return chunks;
    }

    public void addIfNotExists(ChunkPos newPos) {
        if(!chunks.contains(newPos)) {
            chunks.add(newPos);
        }
    }

    public void set(List<ChunkPos> newPos) {
        chunks.clear();
        chunks.addAll(newPos);
    }

    public void remove(ChunkPos pos) {
        chunks.remove(pos);
    }
}

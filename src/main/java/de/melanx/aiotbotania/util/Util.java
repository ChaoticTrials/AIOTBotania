package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.capabilities.chunkmarker.ChunkMarkerProvider;
import de.melanx.aiotbotania.capabilities.farmlanddata.FarmlandDataProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static ResourceLocation resourceOf(String s) {
        return new ResourceLocation(AIOTBotania.MODID, s);
    }

    public static void addFarmlandBlockToBeMoistened(World world, BlockPos pos) {
        Chunk chunk = world.getChunkAt(pos);
        world.getCapability(ChunkMarkerProvider.CHUNK_MARKER_CAP).ifPresent(chunkMarker -> {
            chunkMarker.addIfNotExists(chunk.getPos());
        });
        chunk.getCapability(FarmlandDataProvider.FARMLAND_DATA_CAP).ifPresent(farmlandData -> {
            farmlandData.add(pos);
            chunk.markDirty();
        });
    }

    public static List<BlockPos> getAllFarmlandBlocksToBeMoistened(World world) {
        List<BlockPos> farmlandBlocks = new ArrayList<>();

        world.getCapability(ChunkMarkerProvider.CHUNK_MARKER_CAP).ifPresent(chunkMarker -> {
            List<ChunkPos> keptChunkPos = new ArrayList<>(); // Cant use chunkMarker.remove bc of ConcurrentModificationException
            chunkMarker.getAll().forEach(chunkPos -> {
                if (world.getChunkProvider().isChunkLoaded(chunkPos)) {

                    Chunk chunk = world.getChunkAt(chunkPos.asBlockPos());
                    chunk.getCapability(FarmlandDataProvider.FARMLAND_DATA_CAP).ifPresent(farmlandData -> {
                        List<BlockPos> newBlockPos = farmlandData.cleanAndGetRest(chunk);
                        if(!newBlockPos.isEmpty()) {
                            keptChunkPos.add(chunkPos);
                        }
                        farmlandBlocks.addAll(
                                newBlockPos
                        );
                        chunk.markDirty();
                    });
                }
            });
            chunkMarker.set(keptChunkPos);
        });

        return farmlandBlocks;
    }

    public static void moistenFarmland(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.FARMLAND) {
            if ((Integer) state.getValues().get(FarmlandBlock.MOISTURE) < 7) {
                world.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, 7));
            }
        }
    }
}

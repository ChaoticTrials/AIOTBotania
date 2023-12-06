package de.melanx.aiotbotania.data;

import de.melanx.aiotbotania.core.Registration;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ModLootTables extends BlockLootSubProvider {

    private final Set<Block> generatedLootTables = new HashSet<>();

    public ModLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropOther(Registration.custom_farmland.get(), Blocks.DIRT);
    }

    @Override
    protected void add(@Nonnull Block block, @Nonnull LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.generatedLootTables;
    }
}

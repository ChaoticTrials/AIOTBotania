package de.melanx.aiotbotania.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

public class ToolBreakContext {
    @Nullable
    private final PlayerEntity player;
    private final BlockPos pos;
    private final World world;
    private final ItemStack item;
    private final IItemTier mat;

    public ToolBreakContext(PlayerEntity player, BlockPos pos, IItemTier mat) {
        this(player.world, player, player.getHeldItemMainhand(), pos, mat);
    }

    protected ToolBreakContext(World worldIn, @Nullable PlayerEntity player, ItemStack heldItem, BlockPos pos, IItemTier mat) {
        this.world = worldIn;
        this.player = player;
        this.item = heldItem;
        this.pos = pos;
        this.mat = mat;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public ItemStack getItem() {
        return this.item;
    }

    @Nullable
    public Item getToolItem() {
        return this.item.getItem() instanceof ToolItem ? this.item.getItem() : null;
    }

    @Nullable
    public PlayerEntity getPlayer() {
        return this.player;
    }

    public World getWorld() {
        return this.world;
    }

    public IItemTier getMaterial() {
        return this.mat;
    }

    @Nullable
    public Set<ToolType> getToolTypes() {
        return this.getToolItem() != null ? this.getToolItem().getToolTypes(this.item) : null;
    }

    public boolean isEffectiveOn(BlockState state) {
        return Objects.requireNonNull(this.getToolTypes()).stream().anyMatch(state::isToolEffective);
    }
}

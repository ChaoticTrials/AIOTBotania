package de.melanx.aiotbotania.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ToolBreakContext {

    @Nullable
    private final Player player;
    private final BlockPos pos;
    private final Level level;
    private final ItemStack item;
    private final Tier tier;

    public ToolBreakContext(Player player, BlockPos pos, Tier tier) {
        this(player.level, player, player.getMainHandItem(), pos, tier);
    }

    protected ToolBreakContext(Level level, @Nullable Player player, ItemStack heldItem, BlockPos pos, Tier tier) {
        this.level = level;
        this.player = player;
        this.item = heldItem;
        this.pos = pos;
        this.tier = tier;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public ItemStack getItem() {
        return this.item;
    }

    @Nullable
    public Item getToolItem() {
        return this.item.getItem() instanceof DiggerItem ? this.item.getItem() : null;
    }

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    public Level getLevel() {
        return this.level;
    }

    public Tier getMaterial() {
        return this.tier;
    }
}

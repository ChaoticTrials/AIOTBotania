package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.gui.ItemsRemainingRenderHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static net.minecraftforge.common.ToolActions.*;

public class ToolUtil {
    private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");
    private static final Pattern SAPLING_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)sapling)|(?:(?:[a-z-_.:]|^)Sapling))(?:[A-Z-_.:]|$)");

    public static final ToolAction HOE_TILL = ToolAction.get("till");
    public static final Set<ToolAction> DEFAULT_HOE_ACTIONS = Set.of(HOE_DIG, HOE_TILL);
    public static final Set<ToolAction> DEFAULT_AIOT_ACTIONS = Set.of(
            AXE_DIG, AXE_STRIP, AXE_SCRAPE, AXE_WAX_OFF,
            HOE_DIG, HOE_TILL,
            SHOVEL_DIG, SHOVEL_FLATTEN,
            PICKAXE_DIG,
            SWORD_DIG
    );

    public static void inventoryTick(ItemStack stack, Level level, Entity player, int MPD) {
        if (!level.isClientSide && player instanceof Player && stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, (Player) player, MPD * 2, true)) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    public static void toggleMode(Player player, ItemStack stack) {
        Style dark_blue = Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true);
        Style aqua = Style.EMPTY.withColor(ChatFormatting.AQUA).withItalic(true);
        MutableComponent text = new TranslatableComponent(AIOTBotania.MODID + ".toggleMode").append(" ").withStyle(dark_blue);
        MutableComponent utilityMode = new TranslatableComponent(AIOTBotania.MODID + ".utilityMode").withStyle(aqua);
        MutableComponent hoeMode = new TranslatableComponent(AIOTBotania.MODID + ".hoeMode").withStyle(aqua);
        MutableComponent hoeModePath = new TranslatableComponent(AIOTBotania.MODID + ".hoeModePath").withStyle(aqua);

        if (ItemNBTHelper.getBoolean(stack, "hoemode", true)) {
            ItemNBTHelper.setBoolean(stack, "hoemode", false);
            text.append(utilityMode);
        } else {
            ItemNBTHelper.setBoolean(stack, "hoemode", true);
            if (stack.getItem() instanceof ItemLivingrockAIOT)
                text = text.append(hoeMode);
            else
                text.append(hoeModePath);
        }

        player.displayClientMessage(text, true);
    }

    public static InteractionResult hoemodeUse(@Nonnull UseOnContext ctx, Player player, Level level, BlockPos pos, Direction side) {
        if (!player.isCrouching()) {
            return ToolUtil.hoeUse(ctx, false, true);
        } else {
            if (side != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                return ToolUtil.shovelUse(ctx);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static InteractionResult hoeUse(UseOnContext ctx, boolean special, boolean low_tier) {
        ItemStack stack = ctx.getItemInHand();
        Player player = ctx.getPlayer();
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction side = ctx.getClickedFace();

        if (player != null && player.mayUseItemAt(pos, side, stack)) {
            if (ctx.getClickedFace() != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                BlockState blockstate = ToolUtil.getHoeTillingState(level.getBlockState(pos), ctx);
                if (blockstate != null) {
                    if (blockstate.getBlock() == Blocks.FARMLAND && special) {
                        blockstate = Registration.custom_farmland.get().defaultBlockState();
                    } else if (blockstate.getBlock() == Blocks.ROOTED_DIRT) {
                        Block.popResourceFromFace(level, pos, side, new ItemStack(Items.HANGING_ROOTS));
                    }
                    level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!level.isClientSide) {
                        level.setBlock(pos, blockstate, 11);
                        ctx.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> {
                            playerEntity.broadcastBreakEvent(ctx.getHand());
                        });
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                } else if (level.getBlockState(pos).getBlock() instanceof FarmBlock) {
                    Block block = null;
                    if (special) {
                        block = Blocks.GRASS_BLOCK;
                    } else if (!low_tier) {
                        block = Blocks.DIRT;
                    }
                    if (block != null) {
                        level.setBlockAndUpdate(pos, block.defaultBlockState());
                        if (level.getBlockState(player.blockPosition()).getBlock() == Registration.custom_farmland.get()) {
                            player.setPos(player.getX(), player.getY() + 0.0625, player.getZ());
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult hoeUseAOE(UseOnContext ctx, boolean special, boolean low_tier, int radius) {
        ItemStack stack = ctx.getItemInHand();
        Player player = ctx.getPlayer();
        Level level = ctx.getLevel();
        Direction side = ctx.getClickedFace();
        BlockPos basePos = ctx.getClickedPos().immutable();
        if (player == null || !player.mayUseItemAt(basePos, side, stack))
            return InteractionResult.PASS;

        BlockState baseStateResult = ToolUtil.getHoeTillingState(level.getBlockState(basePos), ctx);
        InteractionResult toReturn = hoeUse(ctx, special, low_tier);

        if (toReturn.consumesAction()) {
            boolean soundPlayed = false;
            for (int xd = -radius; xd <= radius; xd++) {
                for (int zd = -radius; zd <= radius; zd++) {
                    if (xd == 0 && zd == 0)
                        continue;
                    BlockPos pos = basePos.offset(xd, 0, zd);
                    if (ctx.getClickedFace() != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                        BlockState blockstate = ToolUtil.getHoeTillingState(level.getBlockState(pos), ctx);
                        if (baseStateResult == blockstate) {
                            if (blockstate != null) {
                                if (blockstate.getBlock() == Blocks.FARMLAND && special)
                                    blockstate = Registration.custom_farmland.get().defaultBlockState();
                                if (!soundPlayed) {
                                    level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                                    soundPlayed = true;
                                }
                                if (!level.isClientSide) {
                                    level.setBlock(pos, blockstate, 11);
                                    ctx.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> {
                                        playerEntity.broadcastBreakEvent(ctx.getHand());
                                    });
                                }
                            } else if (level.getBlockState(pos).getBlock() instanceof FarmBlock) {
                                Block block = null;
                                if (special) {
                                    block = Blocks.GRASS_BLOCK;
                                } else if (!low_tier) {
                                    block = Blocks.DIRT;
                                }
                                if (block != null) {
                                    level.setBlockAndUpdate(pos, block.defaultBlockState());
                                }
                            }
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    @Nonnull
    public static InteractionResult pickUse(UseOnContext ctx) {
        Player player = ctx.getPlayer();

        if (player != null) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stackAt = player.getInventory().getItem(i);
                if (!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getDescriptionId()).find()) {
                    InteractionResult did = PlayerHelper.substituteUse(ctx, stackAt);
                    if (did == InteractionResult.SUCCESS && !ctx.getLevel().isClientSide())
                        ItemsRemainingRenderHandler.send(player, new ItemStack(Blocks.TORCH), TORCH_PATTERN);
                    return did;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Nonnull
    public static InteractionResult axeUse(UseOnContext ctx) {
        Player player = ctx.getPlayer();
        if (player != null) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stackAt = player.getInventory().getItem(i);
                if (!stackAt.isEmpty() && SAPLING_PATTERN.matcher(stackAt.getItem().getDescriptionId()).find()) {
                    InteractionResult did = PlayerHelper.substituteUse(ctx, stackAt);
                    if (did == InteractionResult.SUCCESS && !ctx.getLevel().isClientSide())
                        ItemsRemainingRenderHandler.send(player, new ItemStack(Blocks.OAK_SAPLING), SAPLING_PATTERN);
                    return did;
                }
            }
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult shovelUse(UseOnContext ctx) {
        // vanilla copy
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (ctx.getClickedFace() != Direction.DOWN) {
            Player player = ctx.getPlayer();
            BlockState modifiedState = state.getToolModifiedState(level, pos, player, ctx.getItemInHand(), SHOVEL_FLATTEN);
            if (modifiedState != null && level.isEmptyBlock(pos.above())) {
                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
                if (!level.isClientSide()) {
                    level.levelEvent(null, 1009, pos, 0);
                }

                CampfireBlock.dowse(player, level, pos, state);
                modifiedState = state.setValue(CampfireBlock.LIT, false);
            }

            if (modifiedState != null) {
                if (!level.isClientSide) {
                    level.setBlock(pos, modifiedState, 11);
                    if (player != null) {
                        ctx.getItemInHand().hurtAndBreak(1, player, (entity) -> {
                            entity.broadcastBreakEvent(ctx.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    public static BlockState getHoeTillingState(BlockState state, UseOnContext context) {
        Block block = state.getBlock();
        if (block == Blocks.ROOTED_DIRT) {
            return Blocks.DIRT.defaultBlockState();
        }
        if (context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT) {
                return Blocks.FARMLAND.defaultBlockState();
            }

            if (block == Blocks.COARSE_DIRT) {
                return Blocks.DIRT.defaultBlockState();
            }
        }

        return null;
    }

    public static InteractionResult stripLog(UseOnContext ctx) {
        // vanilla copy
        Level level = ctx.getLevel();
        BlockPos blockpos = ctx.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState block = blockstate.getToolModifiedState(level, blockpos, ctx.getPlayer(), ctx.getItemInHand(), AXE_STRIP);
        if (block != null) {
            Player playerentity = ctx.getPlayer();
            level.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlock(blockpos, block, 11);
                if (playerentity != null) {
                    ctx.getItemInHand().hurtAndBreak(1, playerentity, (player) -> {
                        player.broadcastBreakEvent(ctx.getHand());
                    });
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public static void removeBlocksInRange(ToolBreakContext context, Direction side, int range, Predicate<BlockState> predicate) {
        boolean doX = side.getStepX() == 0;
        boolean doY = side.getStepY() == 0;
        boolean doZ = side.getStepZ() == 0;
        Vec3i beginDiff = new Vec3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
        Vec3i endDiff = new Vec3i(doX ? range : 0, doY ? range * 2 - 1 : 0, doZ ? range : 0);
        ToolCommons.removeBlocksInIteration(context.getPlayer(), context.getItem(), context.getLevel(), context.getPos(), beginDiff, endDiff, predicate);
    }
}

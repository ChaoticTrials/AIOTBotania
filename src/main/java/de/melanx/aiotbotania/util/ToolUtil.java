package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.gui.ItemsRemainingRenderHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static net.minecraftforge.common.ToolActions.*;

public class ToolUtil {
    private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");
    private static final Pattern SAPLING_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)sapling)|(?:(?:[a-z-_.:]|^)Sapling))(?:[A-Z-_.:]|$)");

    public static final Set<ToolAction> DEFAULT_AIOT_ACTIONS = Set.of(
            AXE_DIG, AXE_STRIP, AXE_SCRAPE, AXE_WAX_OFF,
            HOE_DIG, HOE_TILL,
            SHOVEL_DIG, SHOVEL_FLATTEN,
            PICKAXE_DIG,
            SWORD_DIG
    );

    public static void inventoryTick(ItemStack stack, Level level, Entity player, int manaPerDamage) {
        if (!level.isClientSide && player instanceof Player && stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, (Player) player, manaPerDamage * 2, true)) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    public static void toggleMode(Player player, ItemStack stack) {
        Style dark_blue = Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true);
        Style aqua = Style.EMPTY.withColor(ChatFormatting.AQUA).withItalic(true);
        MutableComponent text = Component.translatable(AIOTBotania.MODID + ".toggleMode").append(" ").withStyle(dark_blue);
        MutableComponent utilityMode = Component.translatable(AIOTBotania.MODID + ".utilityMode").withStyle(aqua);
        MutableComponent hoeMode = Component.translatable(AIOTBotania.MODID + ".hoeMode").withStyle(aqua);
        MutableComponent hoeModePath = Component.translatable(AIOTBotania.MODID + ".hoeModePath").withStyle(aqua);

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

    public static InteractionResult hoemodeUse(@Nonnull UseOnContext context, Player player, Level level, BlockPos pos, Direction side) {
        if (!player.isCrouching()) {
            return ToolUtil.hoeUse(context, false, true);
        } else {
            if (side != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                return ToolUtil.shovelUse(context);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static InteractionResult hoeUse(UseOnContext context, boolean special, boolean lowTier) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction side = context.getClickedFace();

        if (player != null && player.mayUseItemAt(pos, side, context.getItemInHand())) {
            if (context.getClickedFace() != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                BlockState state = level.getBlockState(pos);
                BlockState blockstate = state.getToolModifiedState(context, HOE_TILL, true);
                if (blockstate != null) {
                    if (blockstate.getBlock() == Blocks.FARMLAND && special) {
                        blockstate = Registration.custom_farmland.get().defaultBlockState();
                    } else if (blockstate.getBlock() == Blocks.ROOTED_DIRT) {
                        Block.popResourceFromFace(level, pos, side, new ItemStack(Items.HANGING_ROOTS));
                    }
                    level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!level.isClientSide) {
                        level.setBlock(pos, blockstate, 11);
                        context.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> {
                            playerEntity.broadcastBreakEvent(context.getHand());
                        });
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                } else if (state.getBlock() instanceof FarmBlock) {
                    Block block = null;
                    if (special) {
                        block = Blocks.GRASS_BLOCK;
                    } else if (!lowTier) {
                        block = Blocks.DIRT;
                    }
                    if (block != null) {
                        level.setBlockAndUpdate(pos, block.defaultBlockState());
                        Block.pushEntitiesUp(state, block.defaultBlockState(), level, pos);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult hoeUseAOE(UseOnContext context, boolean special, boolean lowTier, int radius) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos basePos = context.getClickedPos().immutable();
        Direction side = context.getClickedFace();

        if (player == null || !player.mayUseItemAt(basePos, side, context.getItemInHand())) {
            return InteractionResult.PASS;
        }

        BlockState baseStateResult = level.getBlockState(basePos).getToolModifiedState(context, HOE_TILL, true);
        InteractionResult toReturn = hoeUse(context, special, lowTier);

        if (toReturn.consumesAction()) {
            boolean soundPlayed = false;
            for (int xd = -radius; xd <= radius; xd++) {
                for (int zd = -radius; zd <= radius; zd++) {
                    if (xd == 0 && zd == 0) {
                        continue;
                    }

                    BlockPos pos = basePos.offset(xd, 0, zd);
                    if (side != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                        BlockState blockstate = level.getBlockState(pos).getToolModifiedState(context, HOE_TILL, true);
                        if (baseStateResult == blockstate) {
                            if (blockstate != null) {
                                if (blockstate.getBlock() == Blocks.FARMLAND && special) {
                                    blockstate = Registration.custom_farmland.get().defaultBlockState();
                                }

                                if (!soundPlayed) {
                                    level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                                    soundPlayed = true;
                                }

                                if (!level.isClientSide) {
                                    level.setBlock(pos, blockstate, Block.UPDATE_ALL_IMMEDIATE);
                                    context.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> {
                                        playerEntity.broadcastBreakEvent(context.getHand());
                                    });
                                }
                            } else if (level.getBlockState(pos).getBlock() instanceof FarmBlock) {
                                Block block = null;
                                if (special) {
                                    block = Blocks.GRASS_BLOCK;
                                } else if (!lowTier) {
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
    public static InteractionResult pickUse(UseOnContext context) {
        Player player = context.getPlayer();

        if (player != null) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stackAt = player.getInventory().getItem(i);
                if (!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getDescriptionId()).find()) {
                    InteractionResult did = PlayerHelper.substituteUse(context, stackAt);
                    if (did == InteractionResult.SUCCESS && !context.getLevel().isClientSide()) {
                        ItemsRemainingRenderHandler.send(player, new ItemStack(Blocks.TORCH), TORCH_PATTERN);
                    }

                    return did;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Nonnull
    public static InteractionResult axeUse(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stackAt = player.getInventory().getItem(i);
                if (!stackAt.isEmpty() && SAPLING_PATTERN.matcher(stackAt.getItem().getDescriptionId()).find()) {
                    InteractionResult did = PlayerHelper.substituteUse(context, stackAt);
                    if (did == InteractionResult.SUCCESS && !context.getLevel().isClientSide()) {
                        ItemsRemainingRenderHandler.send(player, new ItemStack(Blocks.OAK_SAPLING), SAPLING_PATTERN);
                    }

                    return did;
                }
            }
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult shovelUse(UseOnContext context) {
        // vanilla copy
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (context.getClickedFace() != Direction.DOWN) {
            Player player = context.getPlayer();
            BlockState modifiedState = state.getToolModifiedState(context, SHOVEL_FLATTEN, false);
            if (modifiedState != null && level.isEmptyBlock(pos.above())) {
                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
                if (!level.isClientSide()) {
                    level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
                }

                CampfireBlock.dowse(player, level, pos, state);
                modifiedState = state.setValue(CampfireBlock.LIT, false);
            }

            if (modifiedState != null) {
                if (!level.isClientSide) {
                    level.setBlock(pos, modifiedState, Block.UPDATE_ALL_IMMEDIATE);
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, (entity) -> {
                            entity.broadcastBreakEvent(context.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    public static InteractionResult stripLog(UseOnContext context) {
        // vanilla copy
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        Optional<BlockState> strip = Optional.ofNullable(state.getToolModifiedState(context, ToolActions.AXE_STRIP, false));
        Optional<BlockState> scrape = Optional.ofNullable(state.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false));
        Optional<BlockState> waxOff = Optional.ofNullable(state.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false));
        Optional<BlockState> resultState = Optional.empty();
        if (strip.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            resultState = strip;
        } else if (scrape.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, LevelEvent.PARTICLES_SCRAPE, pos, 0);
            resultState = scrape;
        } else if (waxOff.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, LevelEvent.PARTICLES_WAX_OFF, pos, 0);
            resultState = waxOff;
        }

        if (resultState.isEmpty()) {
            return InteractionResult.PASS;
        }

        ItemStack stack = context.getItemInHand();
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
        }

        level.setBlock(pos, resultState.get(), Block.UPDATE_ALL_IMMEDIATE);
        if (player != null) {
            stack.hurtAndBreak(1, player, playerEntity -> {
                playerEntity.broadcastBreakEvent(context.getHand());
            });
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
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

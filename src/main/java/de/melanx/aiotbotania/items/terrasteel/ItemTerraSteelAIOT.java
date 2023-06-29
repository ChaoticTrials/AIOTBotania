package de.melanx.aiotbotania.items.terrasteel;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.core.network.AIOTBotaniaNetwork;
import de.melanx.aiotbotania.core.network.TerrasteelCreateBurstMesssage;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.alfsteel.ItemAlfsteelAIOT;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.SequentialBreaker;
import vazkii.botania.api.mana.ManaBarTooltip;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.entity.GaiaGuardianEntity;
import vazkii.botania.common.entity.ManaBurstEntity;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.StoneOfTemperanceItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.equipment.tool.terrasteel.TerraBladeItem;
import vazkii.botania.common.item.relic.RingOfThorItem;
import vazkii.botania.common.lib.ResourceLocationHelper;
import vazkii.botania.xplat.XplatAbstractions;

import javax.annotation.Nonnull;
import java.util.*;

public class ItemTerraSteelAIOT extends ItemAIOTBase implements SequentialBreaker {

    public static final int MANA_PER_DAMAGE = 100;
    public static final float DAMAGE = 6.0F;
    public static final float SPEED = -2.2F;
    protected static final Set<Material> MATERIALS = ImmutableSet.of(Material.STONE, Material.METAL, Material.ICE, Material.GLASS, Material.PISTON, Material.HEAVY_METAL, Material.GRASS, Material.DIRT, Material.SAND, Material.TOP_SNOW, Material.SNOW, Material.CLAY);
    private static final Set<Material> AXE_MATERIALS = ImmutableSet.of(Material.WOOD, Material.LEAVES, Material.BAMBOO);
    public static final int[] LEVELS = new int[]{0, 10000, 1000000, 10000000, 100000000, 1000000000};
    private static final int[] CREATIVE_MANA = new int[]{9999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
    private static final Map<ResourceKey<Level>, Set<BlockSwapper>> blockSwappers = new HashMap<>();
    private static boolean tickingSwappers = false;

    // The following code is by Vazkii (https://github.com/Vazkii/Botania/tree/master/src/main/java/vazkii/botania/common/item/equipment/tool/elementium/ <-- Axe, Pick, Shovel and Sword)

    public ItemTerraSteelAIOT() {
        this(ItemTiers.TERRASTEEL_AIOT_ITEM_TIER);
    }

    public ItemTerraSteelAIOT(Tier tier) {
        super(tier, DAMAGE, SPEED, MANA_PER_DAMAGE, true);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityDrops);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
        MinecraftForge.EVENT_BUS.addListener(this::onTickEnd);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(BotaniaItems.terraSword);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            result = HashMultimap.create(result);
            if (isTipped(stack)) {
                result.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "AIOT modifier", 0.1));
            }
        }

        return result;
    }

    @Nonnull
    @Override
    public Optional<TooltipComponent> getTooltipImage(@Nonnull ItemStack stack) {
        int level = getLevel(stack);
        int max = LEVELS[Math.min(LEVELS.length - 1, level + 1)];
        int curr = getMana_(stack);
        float percent = level == 0 ? 0F : (float) curr / (float) max;

        return Optional.of(new ManaBarTooltip(percent, level));
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        BlockHitResult hitResult = ToolCommons.raytraceFromEntity(player, 10.0D, false);
        if (!player.level.isClientSide && hitResult.getType() == HitResult.Type.BLOCK) {
            Direction face = hitResult.getDirection();
            if (AXE_MATERIALS.contains(player.getCommandSenderWorld().getBlockState(hitResult.getBlockPos()).getMaterial())) {
                this.breakOtherBlockAxe(player, stack, pos, pos, face);
            } else {
                this.breakOtherBlock(player, stack, pos, pos, face);
            }
            BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
        }

        return false;
    }

    private void onEntityDrops(LivingDropsEvent event) {
        if (event.isRecentlyHit() && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player) {
            ItemStack weapon = ((Player) event.getSource().getEntity()).getMainHandItem();
            if (!weapon.isEmpty() && weapon.getItem() == this) {
                RandomSource rand = event.getEntity().level.random;
                int looting = weapon.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
                if (event.getEntity() instanceof AbstractSkeleton && rand.nextInt(26) <= 3 + looting) {
                    this.addDrop(event, new ItemStack(event.getEntity() instanceof WitherSkeleton ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL));
                } else if (event.getEntity() instanceof Zombie && !(event.getEntity() instanceof ZombifiedPiglin) && rand.nextInt(26) <= 2 + 2 * looting) {
                    this.addDrop(event, new ItemStack(Items.ZOMBIE_HEAD));
                } else if (event.getEntity() instanceof Creeper && rand.nextInt(26) <= 2 + 2 * looting) {
                    this.addDrop(event, new ItemStack(Items.CREEPER_HEAD));
                } else if (event.getEntity() instanceof Player && rand.nextInt(11) <= 1 + looting) {
                    ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
                    ItemNBTHelper.setString(stack, "SkullOwner", ((Player) event.getEntity()).getGameProfile().getName());
                    this.addDrop(event, stack);
                } else if (event.getEntity() instanceof GaiaGuardianEntity && rand.nextInt(13) < 1 + looting) {
                    this.addDrop(event, new ItemStack(BotaniaBlocks.gaiaHead));
                }
            }
        }
    }

    private void addDrop(LivingDropsEvent event, ItemStack drop) {
        ItemEntity entityitem = new ItemEntity(event.getEntity().level, event.getEntity().xOld,
                event.getEntity().yOld, event.getEntity().zOld, drop);
        entityitem.setPickUpDelay(10);
        event.getDrops().add(entityitem);
    }

    public void trySpawnBurst(Player player) {
        if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == this && player.getAttackStrengthScale(0.0F) == 1) {
            ManaBurstEntity burst = this.getBurst(player, player.getMainHandItem());
            player.level.addFreshEntity(burst);
            player.getMainHandItem().hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), BotaniaSounds.terraBlade, SoundSource.PLAYERS, 1, 1);
        }
    }

    public ManaBurstEntity getBurst(Player player, ItemStack stack) {
        return TerraBladeItem.getBurst(player, stack);
    }

    private void leftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() == this) {
            AIOTBotaniaNetwork.INSTANCE.sendToServer(new TerrasteelCreateBurstMesssage());
        }
    }

    private void attackEntity(AttackEntityEvent event) {
        if (!event.getEntity().level.isClientSide) {
            this.trySpawnBurst(event.getEntity());
        }
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowedIn(group)) {
            for (int mana : CREATIVE_MANA) {
                ItemStack stack = new ItemStack(this);
                setMana(stack, mana);
                items.add(stack);
            }

            ItemStack stack = new ItemStack(this);
            setMana(stack, CREATIVE_MANA[1]);
            setTipped(stack);
            items.add(stack);
        }
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return Integer.MAX_VALUE;
    }

    public static boolean isTipped(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "tipped", false);
    }

    public static void setTipped(ItemStack stack) {
        ItemNBTHelper.setBoolean(stack, "tipped", true);
    }

    public static boolean isEnabled(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "enabled", false);
    }

    public static void setEnabled(ItemStack stack, boolean enabled) {
        ItemNBTHelper.setBoolean(stack, "enabled", enabled);
    }

    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, "mana", mana);
    }

    public static int getMana_(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "mana", 0);
    }

    public static int getLevel(ItemStack stack) {
        int mana = getMana_(stack);
        for (int i = LEVELS.length - 1; i > 0; --i) {
            if (mana >= LEVELS[i]) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack before, @Nonnull ItemStack after, boolean slotChanged) {
        return after.getItem() != this || isEnabled(before) != isEnabled(after);
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            return super.use(level, player, hand);
        } else {
            ItemStack stack = player.getItemInHand(hand);
            int manaLevel = getLevel(stack);
            if (manaLevel != 0) {
                setEnabled(stack, !isEnabled(stack));
                if (!level.isClientSide) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), BotaniaSounds.terraPickMode, SoundSource.PLAYERS, 0.5F, 0.4F);
                }
            }

            return InteractionResultHolder.success(stack);
        }
    }

    @Override
    public void breakOtherBlock(Player player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (isEnabled(stack)) {
            Level level = player.level;
            Material mat = level.getBlockState(pos).getMaterial();
            if (MATERIALS.contains(mat) && !level.isEmptyBlock(pos)) {
                boolean thor = !RingOfThorItem.getThorRing(player).isEmpty();
                boolean doX = thor || side.getStepX() == 0;
                boolean doY = thor || side.getStepY() == 0;
                boolean doZ = thor || side.getStepZ() == 0;
                int origLevel = getLevel(stack);
                int tier = origLevel + (thor ? 1 : 0);
                int rangeDepth = tier / 2;
                if (StoneOfTemperanceItem.hasTemperanceActive(player) && tier > 2) {
                    tier = 2;
                    rangeDepth = 0;
                }

                if (!(stack.getItem() instanceof ItemAlfsteelAIOT)) {
                    rangeDepth = 0;
                }

                int range = tier - 1;
                int rangeY = Math.max(1, range);
                if (range != 0 || tier == 1) {
                    Vec3i beginDiff = new Vec3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
                    Vec3i endDiff = new Vec3i(doX ? range : rangeDepth * -side.getStepX(), doY ? rangeY * 2 - 1 : 0, doZ ? range : rangeDepth * -side.getStepZ());
                    ToolCommons.removeBlocksInIteration(player, stack, level, pos, beginDiff, endDiff,
                            state -> stack.getDestroySpeed(state) > 1.0F || MATERIALS.contains(state.getMaterial()));
                    if (origLevel == 5) {
                        PlayerHelper.grantCriterion((ServerPlayer) player, ResourceLocationHelper.prefix("challenge/rank_ss_pick"), "code_triggered");
                    }
                }
            }
        }
    }

    public void breakOtherBlockAxe(Player player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (!player.isShiftKeyDown() && !tickingSwappers && isEnabled(stack) && !StoneOfTemperanceItem.hasTemperanceActive(player)) {
            addBlockSwapper(player.level, player, stack, pos, 32, true);
        }
    }

    private void onTickEnd(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide) {
            if (event.phase == TickEvent.Phase.END) {
                ResourceKey<Level> dim = event.level.dimension();
                if (blockSwappers.containsKey(dim)) {
                    tickingSwappers = true;
                    Set<BlockSwapper> swappers = blockSwappers.get(dim);
                    swappers.removeIf((next) -> next == null || !next.tick());
                    tickingSwappers = false;
                }
            }
        }
    }

    private static void addBlockSwapper(Level level, Player player, ItemStack stack, BlockPos origCoords, @SuppressWarnings("SameParameterValue") int steps, @SuppressWarnings("SameParameterValue") boolean leaves) {
        BlockSwapper swapper = new BlockSwapper(level, player, stack, origCoords, steps, leaves);
        if (!level.isClientSide) {
            ResourceKey<Level> dim = level.dimension();
            (blockSwappers.computeIfAbsent(dim, (d) -> new HashSet<>())).add(swapper);
        }
    }

    private static class BlockSwapper {
        private final Level level;
        private final Player player;
        private final ItemStack truncator;
        private final boolean treatLeavesSpecial;
        private final PriorityQueue<SwapCandidate> candidateQueue;
        private final Set<BlockPos> completedCoords;

        public BlockSwapper(Level level, Player player, ItemStack truncator, BlockPos origCoords, int range, boolean leaves) {
            this.level = level;
            this.player = player;
            this.truncator = truncator;
            this.treatLeavesSpecial = leaves;
            this.candidateQueue = new PriorityQueue<>();
            this.completedCoords = new HashSet<>();
            this.candidateQueue.offer(new SwapCandidate(origCoords, range));
        }

        public boolean tick() {
            if (this.candidateQueue.isEmpty()) {
                return false;
            } else {
                int remainingSwaps = 10;
                   outerLoop:
                while (remainingSwaps > 0 && !this.candidateQueue.isEmpty()) {
                    SwapCandidate candidate = this.candidateQueue.poll();
                    if (!this.completedCoords.contains(candidate.coordinates) && candidate.range > 0) {
                        ToolCommons.removeBlockWithDrops(this.player, this.truncator, this.level, candidate.coordinates, state -> state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.LEAVES));
                        --remainingSwaps;
                        this.completedCoords.add(candidate.coordinates);
                        Iterator<BlockPos> coords = this.adjacent(candidate.coordinates).iterator();
                        while (true) {
                            BlockPos adj;
                            boolean isWood;
                            boolean isLeaf;
                            do {
                                if (!coords.hasNext()) {
                                    continue outerLoop;
                                }
                                adj = coords.next();
                                BlockState state = this.level.getBlockState(adj);
                                isWood = state.is(BlockTags.LOGS);
                                isLeaf = state.is(BlockTags.LEAVES);
                            } while (!isWood && !isLeaf);
                            int newRange = this.treatLeavesSpecial && isLeaf ? Math.min(3, candidate.range - 1) : candidate.range - 1;
                            this.candidateQueue.offer(new BlockSwapper.SwapCandidate(adj, newRange));
                        }
                    }
                }

                return true;
            }
        }

        public List<BlockPos> adjacent(BlockPos original) {
            List<BlockPos> coords = new ArrayList<>();
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    for (int dz = -1; dz <= 1; ++dz) {
                        if (dx != 0 || dy != 0 || dz != 0) {
                            coords.add(original.offset(dx, dy, dz));
                        }
                    }
                }
            }

            return coords;
        }

        public record SwapCandidate(BlockPos coordinates, int range) implements Comparable<SwapCandidate> {

            public int compareTo(@Nonnull BlockSwapper.SwapCandidate other) {
                return other.range - this.range;
            }
        }
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, List<Component> list, @Nonnull TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        Component rank = Component.translatable("botania.rank" + getLevel(stack));
        Component rankFormat = Component.translatable("botaniamisc.toolRank", rank);
        list.add(rankFormat);
        ManaItem manaItem = XplatAbstractions.INSTANCE.findManaItem(stack);
        if (manaItem != null && manaItem.getMana() == Integer.MAX_VALUE) {
            list.add((Component.translatable("botaniamisc.getALife")).withStyle(ChatFormatting.RED));
        }
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);
        if (hoemode) {
            boolean thor = !RingOfThorItem.getThorRing(player).isEmpty();
            int origLevel = getLevel(stack);
            int tier = origLevel + (thor ? 1 : 0);
            //noinspection ConstantConditions
            if (StoneOfTemperanceItem.hasTemperanceActive(player) && tier > 2) {
                tier = 2;
            }

            int range = tier - 1;
            if (!player.isCrouching()) {
                if (isEnabled(stack)) {
                    return ToolUtil.hoeUseAOE(context, this.special, false, range);
                } else {
                    return ToolUtil.hoeUse(context, this.special, false);
                }
            }

            InteractionResult result = InteractionResult.PASS;
            if (context.getClickedFace() != Direction.DOWN && context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
                result = ToolUtil.shovelUse(context);
            }

            return result == InteractionResult.PASS ? ToolUtil.stripLog(context) : result;
        } else {
            //noinspection ConstantConditions
            if (!player.isCrouching()) {
                return ToolUtil.pickUse(context);
            } else {
                if (context.getClickedFace() == Direction.UP) {
                    return ToolUtil.axeUse(context);
                }

                return InteractionResult.PASS;
            }
        }
    }
}

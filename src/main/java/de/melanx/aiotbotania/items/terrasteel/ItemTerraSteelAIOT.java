package de.melanx.aiotbotania.items.terrasteel;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.core.network.AIOTBotaniaNetwork;
import de.melanx.aiotbotania.core.network.TerrasteelCreateBurstMesssage;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.ManaBarTooltip;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.handler.ModSounds;
import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.ItemTemperanceStone;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.equipment.tool.terrasteel.ItemTerraSword;
import vazkii.botania.common.item.relic.ItemThorRing;
import vazkii.botania.common.lib.ModTags;
import vazkii.botania.common.lib.ResourceLocationHelper;

import javax.annotation.Nonnull;
import java.util.*;

public class ItemTerraSteelAIOT extends ItemAIOTBase implements ISequentialBreaker, IManaItem {

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

    public ItemTerraSteelAIOT(Tier mat) {
        super(mat, DAMAGE, SPEED, MANA_PER_DAMAGE, true);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityDrops);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
        MinecraftForge.EVENT_BUS.addListener(this::onTickEnd);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(ModItems.terraSword);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            if (isTipped(stack)) {
                ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "AIOT modifier", 0.1));
            }
        }
        return ret;
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
        BlockHitResult raycast = ToolCommons.raytraceFromEntity(player, 10.0D, false);
        if (!player.level.isClientSide && raycast.getType() == HitResult.Type.BLOCK) {
            Direction face = raycast.getDirection();
            if (AXE_MATERIALS.contains(player.getCommandSenderWorld().getBlockState(raycast.getBlockPos()).getMaterial())) {
                this.breakOtherBlockAxe(player, stack, pos, pos, face);
            } else {
                this.breakOtherBlock(player, stack, pos, pos, face);
            }
            BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
        }
        return false;
    }

    private void onEntityDrops(LivingDropsEvent e) {
        if (e.isRecentlyHit() && e.getSource().getEntity() != null && e.getSource().getEntity() instanceof Player) {
            ItemStack weapon = ((Player) e.getSource().getEntity()).getMainHandItem();
            if (!weapon.isEmpty() && weapon.getItem() == this) {
                Random rand = e.getEntityLiving().level.random;
                int looting = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, weapon);
                if (e.getEntityLiving() instanceof AbstractSkeleton && rand.nextInt(26) <= 3 + looting)
                    this.addDrop(e, new ItemStack(e.getEntity() instanceof WitherSkeleton ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL));
                else if (e.getEntityLiving() instanceof Zombie && !(e.getEntityLiving() instanceof ZombifiedPiglin) && rand.nextInt(26) <= 2 + 2 * looting)
                    this.addDrop(e, new ItemStack(Items.ZOMBIE_HEAD));
                else if (e.getEntityLiving() instanceof Creeper && rand.nextInt(26) <= 2 + 2 * looting)
                    this.addDrop(e, new ItemStack(Items.CREEPER_HEAD));
                else if (e.getEntityLiving() instanceof Player && rand.nextInt(11) <= 1 + looting) {
                    ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
                    ItemNBTHelper.setString(stack, "SkullOwner", ((Player) e.getEntityLiving()).getGameProfile().getName());
                    this.addDrop(e, stack);
                } else if (e.getEntityLiving() instanceof EntityDoppleganger && rand.nextInt(13) < 1 + looting)
                    this.addDrop(e, new ItemStack(ModBlocks.gaiaHead));
            }
        }
    }

    private void addDrop(LivingDropsEvent e, ItemStack drop) {
        ItemEntity entityitem = new ItemEntity(e.getEntityLiving().level, e.getEntityLiving().xOld,
                e.getEntityLiving().yOld, e.getEntityLiving().zOld, drop);
        entityitem.setPickUpDelay(10);
        e.getDrops().add(entityitem);
    }

    public void trySpawnBurst(Player player) {
        if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == this && player.getAttackStrengthScale(0.0F) == 1.0F) {
            EntityManaBurst burst = this.getBurst(player, new ItemStack(ModItems.terraSword));
            player.level.addFreshEntity(burst);
            player.getMainHandItem().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.terraBlade, SoundSource.PLAYERS, 0.4F, 1.4F);
        }
    }

    public EntityManaBurst getBurst(Player player, ItemStack stack) {
        return ItemTerraSword.getBurst(player, stack);
    }

    private void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            AIOTBotaniaNetwork.INSTANCE.sendToServer(new TerrasteelCreateBurstMesssage());
        }
    }

    private void attackEntity(AttackEntityEvent evt) {
        if (!evt.getPlayer().level.isClientSide) {
            this.trySpawnBurst(evt.getPlayer());
        }
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
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

    @Override
    public int getMana(ItemStack stack) {
        return getMana_(stack);
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
    public int getMaxMana(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(this.getMana(stack) + mana, Integer.MAX_VALUE));
    }

    @Override
    public boolean canReceiveManaFromPool(ItemStack stack, BlockEntity pool) {
        return true;
    }

    @Override
    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return !ModTags.Items.TERRA_PICK_BLACKLIST.contains(otherStack.getItem());
    }

    @Override
    public boolean canExportManaToPool(ItemStack stack, BlockEntity pool) {
        return false;
    }

    @Override
    public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
        return false;
    }

    @Override
    public boolean isNoExport(ItemStack stack) {
        return true;
    }

//    @Override TODO
//    public boolean disposeOfTrashBlocks(ItemStack stack) {
//        return isTipped(stack);
//    }

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
            this.getMana(stack);
            int manaLevel = getLevel(stack);
            if (manaLevel != 0) {
                setEnabled(stack, !isEnabled(stack));
                if (!level.isClientSide) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.terraPickMode, SoundSource.PLAYERS, 0.5F, 0.4F);
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
                boolean thor = !ItemThorRing.getThorRing(player).isEmpty();
                boolean doX = thor || side.getStepX() == 0;
                boolean doY = thor || side.getStepY() == 0;
                boolean doZ = thor || side.getStepZ() == 0;
                int origLevel = getLevel(stack);
                int tier = origLevel + (thor ? 1 : 0);
                int rangeDepth = tier / 2;
                if (ItemTemperanceStone.hasTemperanceActive(player) && tier > 2) {
                    tier = 2;
                    rangeDepth = 0;
                }
//                if (!(stack.getItem() instanceof ItemAlfsteelAIOT)) { TODO Alfsteel
//                    rangeDepth = 0;
//                }

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

    public void breakOtherBlockAxe(Player player, ItemStack stack, BlockPos pos, @SuppressWarnings("unused") BlockPos originPos, @SuppressWarnings("unused") Direction side) {
        if (!player.isShiftKeyDown() && !tickingSwappers && isEnabled(stack) && !ItemTemperanceStone.hasTemperanceActive(player)) {
            addBlockSwapper(player.level, player, stack, pos, 32, true);
        }
    }

    private void onTickEnd(TickEvent.WorldTickEvent event) {
        if (!event.world.isClientSide) {
            if (event.phase == TickEvent.Phase.END) {
                ResourceKey<Level> dim = event.world.dimension();
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
                label51:
                while (remainingSwaps > 0 && !this.candidateQueue.isEmpty()) {
                    SwapCandidate cand = this.candidateQueue.poll();
                    if (!this.completedCoords.contains(cand.coordinates) && cand.range > 0) {
                        ToolCommons.removeBlockWithDrops(this.player, this.truncator, this.level, cand.coordinates, state -> state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.LEAVES));
                        --remainingSwaps;
                        this.completedCoords.add(cand.coordinates);
                        Iterator<BlockPos> var3 = this.adjacent(cand.coordinates).iterator();
                        while (true) {
                            BlockPos adj;
                            boolean isWood;
                            boolean isLeaf;
                            do {
                                if (!var3.hasNext()) {
                                    continue label51;
                                }
                                adj = var3.next();
                                Block block = this.level.getBlockState(adj).getBlock();
                                isWood = BlockTags.LOGS.contains(block);
                                isLeaf = BlockTags.LEAVES.contains(block);
                            } while (!isWood && !isLeaf);
                            int newRange = this.treatLeavesSpecial && isLeaf ? Math.min(3, cand.range - 1) : cand.range - 1;
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

        public static final class SwapCandidate implements Comparable<BlockSwapper.SwapCandidate> {
            public final BlockPos coordinates;
            public final int range;

            public SwapCandidate(BlockPos coordinates, int range) {
                this.coordinates = coordinates;
                this.range = range;
            }

            public int compareTo(@Nonnull BlockSwapper.SwapCandidate other) {
                return other.range - this.range;
            }

            public boolean equals(Object other) {
                if (!(other instanceof BlockSwapper.SwapCandidate)) {
                    return false;
                } else {
                    BlockSwapper.SwapCandidate cand = (BlockSwapper.SwapCandidate) other;
                    return this.coordinates.equals(cand.coordinates) && this.range == cand.range;
                }
            }

            public int hashCode() {
                return Objects.hash(this.coordinates, this.range);
            }
        }
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, List<Component> list, @Nonnull TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        Component rank = new TranslatableComponent("botania.rank" + getLevel(stack));
        Component rankFormat = new TranslatableComponent("botaniamisc.toolRank", rank);
        list.add(rankFormat);
        if (this.getMana(stack) == Integer.MAX_VALUE) {
            list.add((new TranslatableComponent("botaniamisc.getALife")).withStyle(ChatFormatting.RED));
        }
    }

//    @Override TODO
//    public float getManaFractionForDisplay(ItemStack stack) {
//        int level = getLevel(stack);
//        if (level <= 0) {
//            return this.getMana(stack) / (float) LEVELS[1];
//        } else if (level >= LEVELS.length - 1) {
//            return 1f;
//        } else {
//            return (this.getMana(stack) - LEVELS[level]) / (float) LEVELS[level + 1];
//        }
//    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        Direction side = ctx.getClickedFace();
        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);
        if (hoemode) {
            boolean thor = !ItemThorRing.getThorRing(player).isEmpty();
            int origLevel = getLevel(stack);
            int tier = origLevel + (thor ? 1 : 0);
            //noinspection ConstantConditions
            if (ItemTemperanceStone.hasTemperanceActive(player) && tier > 2) {
                tier = 2;
            }
            int range = tier - 1;
            if (!player.isCrouching()) {
                if (isEnabled(stack)) {
                    return ToolUtil.hoeUseAOE(ctx, this.special, false, range);
                } else {
                    return ToolUtil.hoeUse(ctx, this.special, false);
                }
            } else {
                if (side != Direction.DOWN && level.getBlockState(pos.above()).isAir()) {
                    return ToolUtil.shovelUse(ctx);
                } else {
                    return ToolUtil.stripLog(ctx);
                }
            }
        } else {
            //noinspection ConstantConditions
            if (!player.isCrouching()) {
                return ToolUtil.pickUse(ctx);
            } else {
                if (side == Direction.UP) {
                    return ToolUtil.axeUse(ctx);
                }
                return InteractionResult.PASS;
            }
        }
    }
}


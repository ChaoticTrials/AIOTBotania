package de.melanx.aiotbotania.items.terrasteel;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.core.network.AIOTBotaniaNetwork;
import de.melanx.aiotbotania.core.network.TerrasteelCreateBurstMesssage;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.handler.PixieHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.PlayerHelper;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.ItemTemperanceStone;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.equipment.tool.terrasteel.ItemTerraSword;
import vazkii.botania.common.item.relic.ItemThorRing;
import vazkii.botania.common.lib.ResourceLocationHelper;

import javax.annotation.Nonnull;
import java.util.*;

public class ItemTerraSteelAIOT extends ItemAIOTBase implements ISequentialBreaker, IManaItem, IManaTooltipDisplay {

    private static final int MANA_PER_DAMAGE = 100;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.2F;
    private static final List<Material> MATERIALS = Arrays.asList(Material.ROCK, Material.IRON, Material.ICE, Material.GLASS, Material.PISTON, Material.ANVIL, Material.ORGANIC, Material.EARTH, Material.SAND, Material.SNOW, Material.SNOW_BLOCK, Material.CLAY);
    private static final List<Material> AXE_MATERIALS = Arrays.asList(Material.WOOD, Material.LEAVES, Material.BAMBOO);
    public static final int[] LEVELS = new int[]{0, 10000, 1000000, 10000000, 100000000, 1000000000};
    private static final int[] CREATIVE_MANA = new int[]{9999, 999999, 9999999, 99999999, 999999999, 2147483646};
    private static final Map<RegistryKey<World>, Set<BlockSwapper>> blockSwappers = new HashMap<>();

    // The following code is by Vazkii (https://github.com/Vazkii/Botania/tree/master/src/main/java/vazkii/botania/common/item/equipment/tool/elementium/ <-- Axe, Pick, Shovel and Sword)

    public ItemTerraSteelAIOT() {
        super(ItemTiers.ELEMENTIUM_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, true);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityDrops);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
        MinecraftForge.EVENT_BUS.addListener(this::onTickEnd);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlotType.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "AIOT modifier", 0.1));
        }
        return ret;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        BlockRayTraceResult raycast = ToolCommons.raytraceFromEntity(player, 10.0D, false);
        if (!player.world.isRemote && raycast.getType() == RayTraceResult.Type.BLOCK) {
            Direction face = raycast.getFace();
            if (AXE_MATERIALS.contains(player.getEntityWorld().getBlockState(raycast.getPos()).getMaterial())) {
                this.breakOtherBlockAxe(player, stack, pos, pos, face);
            } else {
                this.breakOtherBlock(player, stack, pos, pos, face);
            }
            BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
        }

        return false;
    }

    private void onEntityDrops(LivingDropsEvent e) {
        if (e.isRecentlyHit() && e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof PlayerEntity) {
            ItemStack weapon = ((PlayerEntity) e.getSource().getTrueSource()).getHeldItemMainhand();
            if (!weapon.isEmpty() && weapon.getItem() == this) {
                Random rand = e.getEntityLiving().world.rand;
                int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, weapon);

                if (e.getEntityLiving() instanceof AbstractSkeletonEntity && rand.nextInt(26) <= 3 + looting)
                    addDrop(e, new ItemStack(e.getEntity() instanceof WitherSkeletonEntity ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL));
                else if (e.getEntityLiving() instanceof ZombieEntity && !(e.getEntityLiving() instanceof ZombifiedPiglinEntity) && rand.nextInt(26) <= 2 + 2 * looting)
                    addDrop(e, new ItemStack(Items.ZOMBIE_HEAD));
                else if (e.getEntityLiving() instanceof CreeperEntity && rand.nextInt(26) <= 2 + 2 * looting)
                    addDrop(e, new ItemStack(Items.CREEPER_HEAD));
                else if (e.getEntityLiving() instanceof PlayerEntity && rand.nextInt(11) <= 1 + looting) {
                    ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
                    ItemNBTHelper.setString(stack, "SkullOwner", ((PlayerEntity) e.getEntityLiving()).getGameProfile().getName());
                    addDrop(e, stack);
                } else if (e.getEntityLiving() instanceof EntityDoppleganger && rand.nextInt(13) < 1 + looting)
                    addDrop(e, new ItemStack(ModBlocks.gaiaHead));
            }
        }
    }

    private void addDrop(LivingDropsEvent e, ItemStack drop) {
        ItemEntity entityitem = new ItemEntity(e.getEntityLiving().world, e.getEntityLiving().lastTickPosX,
                e.getEntityLiving().lastTickPosY, e.getEntityLiving().lastTickPosZ, drop);
        entityitem.setPickupDelay(10);
        e.getDrops().add(entityitem);
    }

    public void trySpawnBurst(PlayerEntity player) {
        if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this && player.getCooledAttackStrength(0.0F) == 1.0F) {
            EntityManaBurst burst = ((ItemTerraSword) ModItems.terraSword).getBurst(player, new ItemStack(ModItems.terraSword));
            player.world.addEntity(burst);
            player.getHeldItemMainhand().damageItem(1, player, (p) -> p.sendBreakAnimation(Hand.MAIN_HAND));
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.terraBlade, SoundCategory.PLAYERS, 0.4F, 1.4F);
        }
    }

    private void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            AIOTBotaniaNetwork.INSTANCE.sendToServer(new TerrasteelCreateBurstMesssage());
        }
    }

    private void attackEntity(AttackEntityEvent evt) {
        if (!evt.getPlayer().world.isRemote) {
            this.trySpawnBurst(evt.getPlayer());
        }
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup tab, @Nonnull NonNullList<ItemStack> list) {
        if (this.isInGroup(tab)) {
            for (int mana : CREATIVE_MANA) {
                ItemStack stack = new ItemStack(this);
                setMana(stack, mana);
                list.add(stack);
            }

            ItemStack stack = new ItemStack(this);
            setMana(stack, CREATIVE_MANA[1]);
            setTipped(stack);
            list.add(stack);
        }

    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 2147483647;
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

    void setEnabled(ItemStack stack, boolean enabled) {
        ItemNBTHelper.setBoolean(stack, "enabled", enabled);
        ItemNBTHelper.setBoolean(stack, "hoemode", !enabled);
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
        return 2147483647;
    }

    @Override
    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(this.getMana(stack) + mana, 2147483647));
    }

    @Override
    public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
        return true;
    }

    @Override
    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return !(otherStack.getItem() instanceof IManaGivingItem);
    }

    @Override
    public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
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

    @Override
    public boolean disposeOfTrashBlocks(ItemStack stack) {
        return isTipped(stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack before, @Nonnull ItemStack after, boolean slotChanged) {
        return after.getItem() != this || isEnabled(before) != isEnabled(after);
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        this.getMana(stack);
        int level = getLevel(stack);
        if (level != 0) {
            this.setEnabled(stack, !isEnabled(stack));
            if (!world.isRemote) {
                world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.terraPickMode, SoundCategory.PLAYERS, 0.5F, 0.4F);
            }
        }
        return ActionResult.resultSuccess(stack);
    }

    @Override
    public void breakOtherBlock(PlayerEntity player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (isEnabled(stack)) {
            World world = player.world;
            Material mat = world.getBlockState(pos).getMaterial();
            if (MATERIALS.contains(mat)) {
                if (!world.isAirBlock(pos)) {
                    boolean thor = !ItemThorRing.getThorRing(player).isEmpty();
                    boolean doX = thor || side.getXOffset() == 0;
                    boolean doY = thor || side.getYOffset() == 0;
                    boolean doZ = thor || side.getZOffset() == 0;
                    int origLevel = getLevel(stack);
                    int level = origLevel + (thor ? 1 : 0);
                    if (ItemTemperanceStone.hasTemperanceActive(player) && level > 2) {
                        level = 2;
                    }

                    int range = level - 1;
                    int rangeY = Math.max(1, range);
                    if (range != 0 || level == 1) {
                        Vector3i beginDiff = new Vector3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
                        Vector3i endDiff = new Vector3i(doX ? range : 0, doY ? rangeY * 2 - 1 : 0, doZ ? range : 0);
                        ToolCommons.removeBlocksInIteration(player, stack, world, pos, beginDiff, endDiff, (state) -> MATERIALS.contains(state.getMaterial()), isTipped(stack));
                        if (origLevel == 5) {
                            PlayerHelper.grantCriterion((ServerPlayerEntity) player, ResourceLocationHelper.prefix("challenge/rank_ss_pick"), "code_triggered");
                        }

                    }
                }
            }
        }
    }

    public void breakOtherBlockAxe(PlayerEntity player, ItemStack stack, BlockPos pos, @SuppressWarnings("unused") BlockPos originPos, @SuppressWarnings("unused") Direction side) {
        if (isEnabled(stack)) {
            addBlockSwapper(player.world, player, stack, pos, 32, true);
        }
    }

    private void onTickEnd(TickEvent.WorldTickEvent event) {
        if (!event.world.isRemote) {
            if (event.phase == TickEvent.Phase.END) {
                RegistryKey<World> dim = event.world.func_234923_W_();
                if (blockSwappers.containsKey(dim)) {
                    Set<BlockSwapper> swappers = blockSwappers.get(dim);
                    swappers.removeIf((next) -> next == null || !next.tick());
                }
            }

        }
    }

    private static void addBlockSwapper(World world, PlayerEntity player, ItemStack stack, BlockPos origCoords, @SuppressWarnings("SameParameterValue") int steps, @SuppressWarnings("SameParameterValue") boolean leaves) {
        BlockSwapper swapper = new BlockSwapper(world, player, stack, origCoords, steps, leaves);
        if (!world.isRemote) {
            RegistryKey<World> dim = world.func_234923_W_();
            (blockSwappers.computeIfAbsent(dim, (d) -> new HashSet<>())).add(swapper);
        }
    }

    private static class BlockSwapper {
        private final World world;
        private final PlayerEntity player;
        private final ItemStack truncator;
        private final boolean treatLeavesSpecial;
        private final PriorityQueue<SwapCandidate> candidateQueue;
        private final Set<BlockPos> completedCoords;

        public BlockSwapper(World world, PlayerEntity player, ItemStack truncator, BlockPos origCoords, int range, boolean leaves) {
            this.world = world;
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
                        ToolCommons.removeBlockWithDrops(this.player, this.truncator, this.world, cand.coordinates, (state) -> ToolCommons.materialsAxe.contains(state.getMaterial()), false, this.treatLeavesSpecial);
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
                                Block block = this.world.getBlockState(adj).getBlock();
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
                            coords.add(original.add(dx, dy, dz));
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
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        super.addInformation(stack, world, list, flags);
        ITextComponent rank = new TranslationTextComponent("botania.rank" + getLevel(stack));
        ITextComponent rankFormat = new TranslationTextComponent("botaniamisc.toolRank", rank);
        list.add(rankFormat);
        if (this.getMana(stack) == 2147483647) {
            list.add((new TranslationTextComponent("botaniamisc.getALife")).func_240699_a_(TextFormatting.RED));
        }
    }

    @Override
    public float getManaFractionForDisplay(ItemStack stack) {
        int level = getLevel(stack);
        if (level <= 0) {
            return getMana(stack) / (float) LEVELS[1];
        } else if (level >= LEVELS.length - 1) {
            return 1f;
        } else {
            return (getMana(stack) - LEVELS[level]) / (float) LEVELS[level + 1];
        }
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {

        if (ItemNBTHelper.getBoolean(ctx.getItem(), "hoemode", true)) {
            // Terra AIOT has not hoe mode as there's no terra hoe and right click toggles AOE mining
            ItemNBTHelper.setBoolean(ctx.getItem(), "hoemode", false);
        }
        return super.onItemUse(ctx);
    }
}


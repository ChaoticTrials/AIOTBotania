package de.melanx.aiotbotania.items.elementium;

import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.handler.PixieHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.lib.LibMisc;

import javax.annotation.Nonnull;
import java.util.Random;

public class ItemElementiumAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 66;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.2F;

    public ItemElementiumAIOT() {
        super(ItemTiers.ELEMENTIUM_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, true);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityDrops);
        MinecraftForge.EVENT_BUS.addListener(this::onHarvestDrops);
    }

    // The following code is by Vazkii (https://github.com/Vazkii/Botania/tree/master/src/main/java/vazkii/botania/common/item/equipment/tool/elementium/ <-- Axe, Pick, Shovel and Sword)

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot) {
        Multimap<String, AttributeModifier> ret = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlotType.MAINHAND) {
            ret.put(PixieHandler.PIXIE_SPAWN_CHANCE.getName(), PixieHandler.makeModifier(slot, "AIOT modifier", 0.1));
        }
        return ret;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        World world = player.world;
        Material mat = world.getBlockState(pos).getMaterial();
        if (!ToolCommons.materialsShovel.contains(mat))
            return false;

        Block blk = world.getBlockState(pos).getBlock();
        if (blk instanceof FallingBlock)
            ToolCommons.removeBlocksInIteration(player, stack, world, pos, new Vec3i(0, -12, 0),
                    new Vec3i(1, 12, 1),
                    state -> state.getBlock() == blk,
                    false);

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
                else if (e.getEntityLiving() instanceof ZombieEntity && !(e.getEntityLiving() instanceof ZombiePigmanEntity) && rand.nextInt(26) <= 2 + 2 * looting)
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

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent e) {
        if (e.getHarvester() != null) {
            ItemStack stack = e.getHarvester().getHeldItemMainhand();
            if (!stack.isEmpty() && (stack.getItem() == this)) {
                e.getDrops().removeIf(s -> !s.isEmpty() && ((isDisposable(s)
                        || isSemiDisposable(s)) && !e.getHarvester().isCrouching()));
            }
        }
    }

    private static final Tag<Item> DISPOSABLE = new ItemTags.Wrapper(new ResourceLocation(LibMisc.MOD_ID, "disposable"));
    private static final Tag<Item> SEMI_DISPOSABLE = new ItemTags.Wrapper(new ResourceLocation(LibMisc.MOD_ID, "semi_disposable"));

    public static boolean isDisposable(Block block) {
        return DISPOSABLE.contains(block.asItem());
    }

    private static boolean isDisposable(ItemStack stack) {
        if (stack.isEmpty())
            return false;

        return DISPOSABLE.contains(stack.getItem());
    }

    private static boolean isSemiDisposable(ItemStack stack) {
        return SEMI_DISPOSABLE.contains(stack.getItem());
    }

}


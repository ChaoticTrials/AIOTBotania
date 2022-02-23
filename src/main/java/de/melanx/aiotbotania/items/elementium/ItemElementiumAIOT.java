package de.melanx.aiotbotania.items.elementium;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.Random;

public class ItemElementiumAIOT extends ItemAIOTBase {
    private static final int MANA_PER_DAMAGE = 66;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.2F;

    // The following code is by Vazkii (https://github.com/Vazkii/Botania/tree/master/src/main/java/vazkii/botania/common/item/equipment/tool/elementium/ <-- Axe, Pick, Shovel and Sword)

    public ItemElementiumAIOT() {
        super(ItemTiers.ELEMENTIUM_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, true);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityDrops);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(ModItems.elementiumSword);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "AIOT modifier", 0.1));
        }

        return ret;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        Level level = player.level;
        if (this.getDestroySpeed(stack, level.getBlockState(pos)) <= 1.0F) {
            return false;
        }

        Block block = level.getBlockState(pos).getBlock();
        if (block instanceof FallingBlock) {
            ToolCommons.removeBlocksInIteration(
                    player,
                    stack,
                    level,
                    pos,
                    new Vec3i(0, -12, 0),
                    new Vec3i(1, 12, 1),
                    state -> state.getBlock() == block);
        }

        return false;
    }

    private void onEntityDrops(LivingDropsEvent event) {
        if (event.isRecentlyHit() && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player) {
            ItemStack weapon = ((Player) event.getSource().getEntity()).getMainHandItem();
            if (!weapon.isEmpty() && weapon.getItem() == this) {
                Random rand = event.getEntityLiving().level.random;
                int looting = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, weapon);

                if (event.getEntityLiving() instanceof AbstractSkeleton && rand.nextInt(26) <= 3 + looting) {
                    this.addDrop(event, new ItemStack(event.getEntity() instanceof WitherSkeleton ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL));
                } else if (event.getEntityLiving() instanceof Zombie && !(event.getEntityLiving() instanceof ZombifiedPiglin) && rand.nextInt(26) <= 2 + 2 * looting) {
                    this.addDrop(event, new ItemStack(Items.ZOMBIE_HEAD));
                } else if (event.getEntityLiving() instanceof Creeper && rand.nextInt(26) <= 2 + 2 * looting) {
                    this.addDrop(event, new ItemStack(Items.CREEPER_HEAD));
                } else if (event.getEntityLiving() instanceof Player && rand.nextInt(11) <= 1 + looting) {
                    ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
                    ItemNBTHelper.setString(stack, "SkullOwner", ((Player) event.getEntityLiving()).getGameProfile().getName());
                    this.addDrop(event, stack);
                } else if (event.getEntityLiving() instanceof EntityDoppleganger && rand.nextInt(13) < 1 + looting) {
                    this.addDrop(event, new ItemStack(ModBlocks.gaiaHead));
                }
            }
        }
    }

    private void addDrop(LivingDropsEvent event, ItemStack drop) {
        ItemEntity entity = new ItemEntity(event.getEntityLiving().level, event.getEntityLiving().xOld,
                event.getEntityLiving().yOld, event.getEntityLiving().zOld, drop);
        entity.setPickUpDelay(10);
        event.getDrops().add(entity);
    }
}


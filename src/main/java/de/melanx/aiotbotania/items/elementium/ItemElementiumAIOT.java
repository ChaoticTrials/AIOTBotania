/*
 * This file is part of AIOT Botania.
 *
 * Copyright 2018-2020, MelanX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.melanx.aiotbotania.items.elementium;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.handler.PixieHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.entity.EntityDoppleganger;
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
        World world = player.world;
        Material mat = world.getBlockState(pos).getMaterial();
        if (!ToolCommons.materialsShovel.contains(mat))
            return false;

        Block blk = world.getBlockState(pos).getBlock();
        if (blk instanceof FallingBlock)
            ToolCommons.removeBlocksInIteration(player, stack, world, pos, new Vector3i(0, -12, 0),
                    new Vector3i(1, 12, 1),
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
}


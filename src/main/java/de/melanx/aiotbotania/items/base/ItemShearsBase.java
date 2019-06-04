package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.util.Registry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ItemShearsBase extends ItemShears implements IManaUsingItem {

    private int MANA_PER_DAMAGE;

    public ItemShearsBase(String name, int MANA_PER_DAMAGE, int MAX_DMG) {
        Registry.registerItem(this, name);
        Registry.registerModel(this);

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;

        this.setMaxDamage(MAX_DMG);
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if(entity.world.isRemote)
            return false;

        if(entity instanceof IShearable) {
            IShearable target = (IShearable)entity;
            if(target.isShearable(itemstack, entity.world, new BlockPos(entity))) {
                List<ItemStack> drops = target.onSheared(itemstack, entity.world, new BlockPos(entity), EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

                for(ItemStack stack : drops) {
                    entity.entityDropItem(stack, 1.0F);
                }

                ToolCommons.damageItem(itemstack, 1, player, MANA_PER_DAMAGE);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onBlockStartBreak(@Nonnull ItemStack itemstack, @Nonnull BlockPos pos, EntityPlayer player) {
        if (player.world.isRemote)
            return false;

        Block block = player.world.getBlockState(pos).getBlock();
        if(block instanceof IShearable) {
            IShearable target = (IShearable)block;
            if(target.isShearable(itemstack, player.world, pos)) {
                List<ItemStack> drops = target.onSheared(itemstack, player.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));
                Random rand = new Random();

                for(ItemStack stack : drops) {
                    float f = 0.7F;
                    double d  = rand.nextFloat() * f + (1D - f) * 0.5;
                    double d1 = rand.nextFloat() * f + (1D - f) * 0.5;
                    double d2 = rand.nextFloat() * f + (1D - f) * 0.5;

                    EntityItem entityitem = new EntityItem(player.world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, stack);
                    entityitem.setPickupDelay(10);
                    player.world.spawnEntity(entityitem);
                }

                ToolCommons.damageItem(itemstack, 1, player, MANA_PER_DAMAGE);
                player.addStat(StatList.getBlockStats(block), 1);
                player.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }
}

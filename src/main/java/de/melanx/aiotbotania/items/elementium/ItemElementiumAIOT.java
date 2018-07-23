package de.melanx.aiotbotania.items.elementium;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.Random;

import static vazkii.botania.common.item.equipment.tool.ToolCommons.materialsShovel;

public class ItemElementiumAIOT extends ItemAIOTBase implements IPixieSpawner {

    private static final int MANA_PER_DAMAGE = 66;

    public ItemElementiumAIOT() {
        super("elementiumAIOT", BotaniaAPI.elementiumToolMaterial, 6.0f, -2.2f, MANA_PER_DAMAGE, true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // The following code is by Vazkii (https://github.com/Vazkii/Botania/tree/master/src/main/java/vazkii/botania/common/item/equipment/tool/elementium/ <-- Axe, Pick, Shovel and Sword)

    @Override
    public float getPixieChance(ItemStack stack) {
        return 0.1F;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        World world = player.world;
        Material mat = world.getBlockState(pos).getMaterial();
        if (!materialsShovel.contains(mat))
            return false;

        RayTraceResult block = ToolCommons.raytraceFromEntity(world, player, true, 10);
        if (block == null)
            return false;

        int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;

        Block blk = world.getBlockState(pos).getBlock();
        if(blk instanceof BlockFalling)
            ToolCommons.removeBlocksInIteration(player, stack, world, pos, new Vec3i(0, -12, 0), new Vec3i(1, 12, 1), blk, materialsShovel, silk, fortune, false);

        return false;
    }

    @SubscribeEvent
    public void onEntityDrops(LivingDropsEvent event) {
        if(event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
            ItemStack weapon = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand();
            if(!weapon.isEmpty() && weapon.getItem() == this) {
                Random rand = event.getEntityLiving().world.rand;
                int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, weapon);

                if(event.getEntityLiving() instanceof AbstractSkeleton && rand.nextInt(26) <= 3 + looting)
                    addDrop(event, new ItemStack(Items.SKULL, 1, event.getEntityLiving() instanceof EntityWitherSkeleton ? 1 : 0));
                else if(event.getEntityLiving() instanceof EntityZombie && !(event.getEntityLiving() instanceof EntityPigZombie) && rand.nextInt(26) <= 2 + 2 * looting)
                    addDrop(event, new ItemStack(Items.SKULL, 1, 2));
                else if(event.getEntityLiving() instanceof EntityCreeper && rand.nextInt(26) <= 2 + 2 * looting)
                    addDrop(event, new ItemStack(Items.SKULL, 1, 4));
                else if(event.getEntityLiving() instanceof EntityPlayer && rand.nextInt(11) <= 1 + looting) {
                    ItemStack stack = new ItemStack(Items.SKULL, 1, 3);
                    ItemNBTHelper.setString(stack, "SkullOwner", event.getEntityLiving().getName());
                    addDrop(event, stack);
                } else if(event.getEntityLiving() instanceof EntityDoppleganger && rand.nextInt(13) < 1 + looting)
                    addDrop(event, new ItemStack(ModItems.gaiaHead));
            }
        }
    }

    private void addDrop(LivingDropsEvent event, ItemStack drop) {
        EntityItem entityitem = new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, drop);
        entityitem.setPickupDelay(10);
        event.getDrops().add(entityitem);
    }

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        if(event.getHarvester() != null) {
            ItemStack stack = event.getHarvester().getHeldItemMainhand();
            if(!stack.isEmpty() && (stack.getItem() == this)) {
                event.getDrops().removeIf(s -> !s.isEmpty() && (isDisposable(s)
                        || isSemiDisposable(s) && !event.getHarvester().isSneaking()));
            }
        }
    }

    public static boolean isDisposable(Block block) {
        return isDisposable(new ItemStack(block));
    }

    private static boolean isDisposable(ItemStack stack) {
        if(stack.isEmpty())
            return false;

        for(int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if(BotaniaAPI.disposableBlocks.contains(name))
                return true;
        }
        return false;
    }

    private static boolean isSemiDisposable(ItemStack stack) {
        for(int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if(BotaniaAPI.semiDisposableBlocks.contains(name))
                return true;
        }
        return false;
    }

}

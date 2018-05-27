package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;


public class ItemHoeBase extends ItemHoe implements IManaUsingItem {

    private int MANA_PER_DAMAGE;

    public ItemHoeBase(String name, ToolMaterial mat, int MANA_PER_DAMAGE) {
        super(mat);
        ModItems.registerItem(this, name);
        ModItems.registerModel(this);

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }


    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, @Nonnull World world, BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos, side, stack)) {
            return EnumActionResult.PASS;
        } else {
            UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
            if (MinecraftForge.EVENT_BUS.post(event))
                return EnumActionResult.FAIL;

            if (event.getResult() == Event.Result.ALLOW) {
                ToolCommons.damageItem(stack, 1, player, MANA_PER_DAMAGE);
                return EnumActionResult.SUCCESS;
            }

            Block block = world.getBlockState(pos).getBlock();

            if (side != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH || block == Blocks.DIRT) {

                    Block block1 = Blocks.FARMLAND;

                    SoundType sound = block1.getSoundType(block1.getDefaultState(), world, pos, player);

                    world.playSound(null, pos, sound.getStepSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

                    if (world.isRemote)
                        return EnumActionResult.SUCCESS;
                    else {
                        world.setBlockState(pos, block1.getDefaultState());
                        ToolCommons.damageItem(stack, 1, player, MANA_PER_DAMAGE);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }


    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
        return par2ItemStack.getItem() == vazkii.botania.common.item.ModItems.manaResource && par2ItemStack.getItemDamage() == 0 || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase) {
        ToolCommons.damageItem(par1ItemStack, 1, par3EntityLivingBase, MANA_PER_DAMAGE);
        return true;
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }
}

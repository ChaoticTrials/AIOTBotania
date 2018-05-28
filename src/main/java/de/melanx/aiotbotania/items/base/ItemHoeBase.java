package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.util.Registry;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;


public class ItemHoeBase extends ItemHoe implements IManaUsingItem {

    private int MANA_PER_DAMAGE;
    private boolean special;

    public ItemHoeBase(String name, ToolMaterial mat, int MANA_PER_DAMAGE, boolean special) {
        super(mat);
        Registry.registerItem(this, name);
        Registry.registerModel(this);

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }


    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, @Nonnull World world, BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
        return ToolUtil.hoeUse(player, world, pos, hand, side, special, MANA_PER_DAMAGE);
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

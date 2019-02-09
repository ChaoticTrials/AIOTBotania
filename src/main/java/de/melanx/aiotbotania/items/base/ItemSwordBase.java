package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.util.Registry;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;

import javax.annotation.Nonnull;

public class ItemSwordBase extends ItemSword implements IManaUsingItem {

    private int MANA_PER_DAMAGE;

    public ItemSwordBase(String name, ToolMaterial mat, int MANA_PER_DAMAGE) {
        super(mat);
        Registry.registerItem(this, name);
        Registry.registerModel(this);

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.onUpdate(stack, world, player, MANA_PER_DAMAGE);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase) {
        return ToolUtil.hitEntity(par1ItemStack, par3EntityLivingBase, MANA_PER_DAMAGE);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity) {
        return ToolUtil.onBlockDestroyed(stack, world, state, pos, entity, MANA_PER_DAMAGE);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
        return par2ItemStack.getItem() == vazkii.botania.common.item.ModItems.manaResource && par2ItemStack.getItemDamage() == 0 || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

}

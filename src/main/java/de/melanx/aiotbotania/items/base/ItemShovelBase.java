package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.Registry;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;

import javax.annotation.Nonnull;

public class ItemShovelBase extends ItemSpade implements IManaUsingItem {

    private int MANA_PER_DAMAGE;

    public ItemShovelBase(String name, IItemTier mat, int damage, float speed, int MANA_PER_DAMAGE) {
        super(mat, damage, speed, new Item.Properties().group(AIOTBotania.aiotItemGroup));
        Registry.registerItem(this, name);
        Registry.registerModel(this);

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.inventoryTick(stack, world, player, MANA_PER_DAMAGE);
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
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

}

package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;

import javax.annotation.Nonnull;

public class ItemHoeBase extends HoeItem implements IManaUsingItem {

    protected final int MANA_PER_DAMAGE;
    protected final boolean special;
    protected final boolean low_tier;

    public ItemHoeBase(IItemTier mat, int speed, int MANA_PER_DAMAGE, boolean special, boolean low_tier) {
        super(mat, -mat.getHarvestLevel(), speed, new Item.Properties().group(AIOTBotania.instance.getTab()));

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
        this.low_tier = low_tier;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.inventoryTick(stack, world, player, MANA_PER_DAMAGE);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLivingBase, @Nonnull LivingEntity par3EntityLivingBase) {
        return ToolUtil.hitEntity(par1ItemStack, par3EntityLivingBase, MANA_PER_DAMAGE);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entity) {
        return ToolUtil.onBlockDestroyed(stack, world, state, pos, entity, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        return ToolUtil.hoeUse(ctx, special, low_tier, MANA_PER_DAMAGE);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }
}

package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

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
        ToolUtil.inventoryTick(stack, world, player, this.MANA_PER_DAMAGE);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        return ToolUtil.hoeUse(ctx, this.special, this.low_tier);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }
}

package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import vazkii.botania.common.item.equipment.ICustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ItemHoeBase extends HoeItem implements ICustomDamageItem {

    protected final int MANA_PER_DAMAGE;
    protected final boolean special;
    protected final boolean low_tier;

    public ItemHoeBase(Tier mat, int speed, int MANA_PER_DAMAGE, boolean special, boolean low_tier) {
        super(mat, 0, speed, new Item.Properties().tab(AIOTBotania.instance.getTab()));

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
        this.low_tier = low_tier;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        ToolUtil.inventoryTick(stack, level, entity, this.MANA_PER_DAMAGE);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        return ToolUtil.hoeUse(context, this.special, this.low_tier);
    }
}

package de.melanx.aiotbotania.items.base;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ItemHoeBase extends HoeItem implements CustomDamageItem {

    protected final int manaPerDamage;
    protected final boolean special;
    protected final boolean lowTier;

    public ItemHoeBase(Tier tier, int speed, int manaPerDamage, boolean special, boolean lowTier) {
        super(tier, 0, speed, new Item.Properties());
        this.manaPerDamage = manaPerDamage;
        this.special = special;
        this.lowTier = lowTier;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slot, boolean isSelected) {
        ToolUtil.inventoryTick(stack, level, entity, this.manaPerDamage);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.manaPerDamage);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        BlockState originalState = context.getLevel().getBlockState(context.getClickedPos());
        BlockState modifiedState = ForgeEventFactory.onToolUse(originalState, context, ToolActions.HOE_TILL, false);
        if (originalState == modifiedState || modifiedState == null) {
            return InteractionResult.FAIL;
        }

        return ToolUtil.hoeUse(context, this.special, this.lowTier);
    }
}

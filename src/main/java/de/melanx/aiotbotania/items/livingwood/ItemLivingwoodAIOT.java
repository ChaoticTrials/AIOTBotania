package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class ItemLivingwoodAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 33;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.4F;

    public ItemLivingwoodAIOT() {
        super(ItemTiers.LIVINGWOOD_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        InteractionResult result = ToolUtil.hoemodeUse(context, player, context.getLevel(), context.getClickedPos(), context.getClickedFace());

        return result == InteractionResult.PASS ? ToolUtil.stripLog(context) : result;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Registration.livingwood_sword.get());
    }
}


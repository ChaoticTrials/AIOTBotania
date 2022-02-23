package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class ItemLivingrockAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 44;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.4F;

    public ItemLivingrockAIOT() {
        super(ItemTiers.LIVINGROCK_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        boolean hoemode = ItemNBTHelper.getBoolean(context.getItemInHand(), "hoemode", true);

        if (hoemode) {
            return ToolUtil.hoemodeUse(context, player, context.getLevel(), context.getClickedPos(), context.getClickedFace());
        } else {
            InteractionResult result = InteractionResult.PASS;
            if (!player.isCrouching()) {
                result = ToolUtil.pickUse(context);
            }

            if (result == InteractionResult.PASS && context.getClickedFace() == Direction.UP) {
                result = ToolUtil.axeUse(context);
            }

            return result == InteractionResult.PASS ? ToolUtil.stripLog(context) : result;
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Registration.livingrock_sword.get());
    }
}

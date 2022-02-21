package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class ItemLivingrockAIOT extends ItemAIOTBase {
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Registration.livingrock_sword.get());
    }

    private static final int MANA_PER_DAMAGE = 44;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.4F;

    public ItemLivingrockAIOT() {
        super(ItemTiers.LIVINGROCK_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        Player player = ctx.getPlayer();
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction side = ctx.getClickedFace();

        if (player == null) {
            return InteractionResult.PASS;
        }

        Block block = level.getBlockState(pos).getBlock();

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if (hoemode) {
            return ToolUtil.hoemodeUse(ctx, player, level, pos, side);
        } else {
            if (!player.isCrouching()) {
                return ToolUtil.pickUse(ctx);
            } else {
                if (side == Direction.UP) {
                    return ToolUtil.axeUse(ctx);
                }

                return ToolUtil.stripLog(ctx);
            }
        }
    }
}

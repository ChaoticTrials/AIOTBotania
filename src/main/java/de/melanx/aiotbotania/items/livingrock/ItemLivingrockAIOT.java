package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;

public class ItemLivingrockAIOT extends ItemAIOTBase {
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.type.canEnchantItem(Registration.livingrock_sword.get());
    }

    private static final int MANA_PER_DAMAGE = 44;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.4F;

    public ItemLivingrockAIOT() {
        super(ItemTiers.LIVINGROCK_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, false);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        ItemStack stack = ctx.getItem();
        PlayerEntity player = ctx.getPlayer();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        Direction side = ctx.getFace();

        Block block = world.getBlockState(pos).getBlock();

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if (hoemode) {
            return ToolUtil.hoemodeUse(ctx, player, world, pos, side, block);
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

package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        PlayerEntity player = ctx.getPlayer();
        Direction side = ctx.getFace();

        ActionResultType toReturn = ToolUtil.hoemodeUse(ctx, player, world, pos, side);

        return toReturn == ActionResultType.PASS ? ToolUtil.stripLog(ctx) : toReturn;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        return ActionResult.resultFail(itemStack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.type.canEnchantItem(Registration.livingwood_sword.get());
    }
}


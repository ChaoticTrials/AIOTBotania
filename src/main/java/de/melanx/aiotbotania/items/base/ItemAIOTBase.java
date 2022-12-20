package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.data.ModTags;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import de.melanx.aiotbotania.items.livingwood.ItemLivingwoodAIOT;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class ItemAIOTBase extends DiggerItem implements CustomDamageItem {

    protected final int manaPerDamage;
    protected final boolean special;
    protected final Tier tier;

    public ItemAIOTBase(Tier tier, float attackDamage, float attackSpeed, int manaPerDamage, boolean special) {
        super(attackDamage, attackSpeed, tier, ModTags.Blocks.MINEABLE_WITH_AIOT, new Item.Properties().tab(AIOTBotania.instance.getTab()));
        this.manaPerDamage = manaPerDamage;
        this.special = special;
        this.tier = tier;
    }

    public static boolean getBindMode(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "hoemode", true);
    }

    public static String getModeString(ItemStack stack) {
        Item tool = stack.getItem();
        if (tool instanceof ItemLivingrockAIOT || tool instanceof ItemLivingwoodAIOT) {
            return AIOTBotania.MODID + (getBindMode(stack) ? ".hoeMode" : ".utilityMode");
        }

        return AIOTBotania.MODID + (getBindMode(stack) ? ".hoeModePath" : ".utilityMode");
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        ToolUtil.inventoryTick(stack, level, entity, this.manaPerDamage);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.manaPerDamage);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        boolean hoemode = ItemNBTHelper.getBoolean(context.getItemInHand(), "hoemode", true);

        InteractionResult result = InteractionResult.PASS;
        if (hoemode) {
            if (!player.isCrouching()) {
                result = ToolUtil.hoeUse(context, this.special, false);
            } else if (context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
                result = ToolUtil.shovelUse(context);
            }

            return result == InteractionResult.PASS ? ToolUtil.stripLog(context) : result;
        } else {
            if (!player.isCrouching()) {
                result = ToolUtil.pickUse(context);
            }

            if (result == InteractionResult.PASS && context.getClickedFace() == Direction.UP) {
                result = ToolUtil.axeUse(context);
            }

            return result;
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player.isCrouching()) {
            ToolUtil.toggleMode(player, stack);
            return InteractionResultHolder.success(stack);
        }

        return super.use(level, player, hand);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        }

        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Items.DIAMOND_SWORD);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, List<Component> tooltip, @Nonnull TooltipFlag flag) {
        tooltip.add(Component.translatable(getModeString(stack)));
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolUtil.DEFAULT_AIOT_ACTIONS.contains(toolAction);
    }
}

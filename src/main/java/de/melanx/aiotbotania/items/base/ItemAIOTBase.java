package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.handler.data.ModTags;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import de.melanx.aiotbotania.items.livingwood.ItemLivingwoodAIOT;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.ICustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class ItemAIOTBase extends DiggerItem implements ICustomDamageItem {

    protected final int MANA_PER_DAMAGE;
    protected final boolean special;
    protected final Tier mat;

    public ItemAIOTBase(Tier mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special) {
        super(attackDamage, attackSpeed, mat, ModTags.Blocks.MINEABLE_WITH_AIOT, new Item.Properties().tab(AIOTBotania.instance.getTab()));
        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
        this.mat = mat;
    }

    public static boolean getBindMode(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "hoemode", true);
    }

    public static String getModeString(ItemStack stack) {
        Item tool = stack.getItem();
        if (tool instanceof ItemLivingrockAIOT || tool instanceof ItemLivingwoodAIOT)
            return AIOTBotania.MODID + (getBindMode(stack) ? ".hoeMode" : ".utilityMode");
        return AIOTBotania.MODID + (getBindMode(stack) ? ".hoeModePath" : ".utilityMode");
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
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        Direction side = context.getClickedFace();

        if (player == null) {
            return InteractionResult.PASS;
        }

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if (hoemode) {
            if (!player.isCrouching()) {
                return ToolUtil.hoeUse(context, this.special, false);
            } else if (level.getBlockState(pos.above()).isAir()) {
                return ToolUtil.shovelUse(context);
            }
            return ToolUtil.stripLog(context);
        } else {
            if (!player.isCrouching()) {
                return ToolUtil.pickUse(context);
            } else {
                if (side == Direction.UP) {
                    return ToolUtil.axeUse(context);
                }

                return InteractionResult.PASS;
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if (player.isCrouching()) {
                ToolUtil.toggleMode(player, itemStack);
                return InteractionResultHolder.success(itemStack);
            }
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
        tooltip.add(new TranslatableComponent(getModeString(stack)));
    }
}

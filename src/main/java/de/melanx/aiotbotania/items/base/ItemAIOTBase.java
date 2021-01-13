package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import de.melanx.aiotbotania.items.livingwood.ItemLivingwoodAIOT;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

public class ItemAIOTBase extends ToolItem implements IManaUsingItem {

    protected final int MANA_PER_DAMAGE;
    protected final boolean special;
    protected final IItemTier mat;

    public ItemAIOTBase(IItemTier mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special) {
        super(attackDamage, attackSpeed, mat, new HashSet<>(), new Item.Properties().group(AIOTBotania.instance.getTab())
                .addToolType(ToolType.AXE, mat.getHarvestLevel())
                .addToolType(ToolType.PICKAXE, mat.getHarvestLevel())
                .addToolType(ToolType.SHOVEL, mat.getHarvestLevel())
                .addToolType(ToolType.HOE, mat.getHarvestLevel()));
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
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.inventoryTick(stack, world, player, MANA_PER_DAMAGE);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        PlayerEntity player = ctx.getPlayer();
        ItemStack stack = ctx.getItem();
        Direction side = ctx.getFace();
        Block block = world.getBlockState(pos).getBlock();

        if (player == null) return ActionResultType.PASS;

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if (hoemode) {
            if (!player.isCrouching() && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.GRASS_PATH || block instanceof FarmlandBlock)) {
                return ToolUtil.hoeUse(ctx, special, false);
            } else if (side != Direction.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)) {
                return ToolUtil.shovelUse(ctx);
            }
            return ToolUtil.stripLog(ctx);
        } else {
            if (!player.isCrouching()) {
                return ToolUtil.pickUse(ctx);
            } else {
                if (side == Direction.UP) {
                    return ToolUtil.axeUse(ctx);
                }
                return ActionResultType.PASS;
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.isCrouching()) {
                ToolUtil.toggleMode(player, itemStack);
                return ActionResult.resultSuccess(itemStack);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        if (state.getBlock() == Blocks.COBWEB) {
            return 15.0F;
        } else {
            return state.getBlock().getHarvestTool(state) == null || this.getToolTypes(stack).contains(state.getBlock().getHarvestTool(state)) ? this.efficiency : 1.0F;
        }
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent(getModeString(stack)));
    }

    @Override
    public boolean canHarvestBlock(BlockState block) {
        return block.getHarvestLevel() <= mat.getHarvestLevel();
    }
}

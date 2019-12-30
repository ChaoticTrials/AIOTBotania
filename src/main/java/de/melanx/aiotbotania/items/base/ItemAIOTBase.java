package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
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
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;

public class ItemAIOTBase extends ToolItem implements IManaUsingItem {

    private int MANA_PER_DAMAGE;
    private boolean special;

    public ItemAIOTBase(String name, IItemTier mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special){
        super(attackDamage, attackSpeed, mat, new HashSet<>(), new Item.Properties().group(AIOTBotania.instance.getTab())
                .addToolType(ToolType.AXE, mat.getHarvestLevel())
                .addToolType(ToolType.PICKAXE, mat.getHarvestLevel())
                .addToolType(ToolType.SHOVEL, mat.getHarvestLevel()));
        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
        setRegistryName(AIOTBotania.MODID, name);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.inventoryTick(stack, world, player, MANA_PER_DAMAGE);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLivingBase, @Nonnull LivingEntity par3EntityLivingBase) {
        return ToolUtil.hitEntity(par1ItemStack, par3EntityLivingBase, MANA_PER_DAMAGE);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entity) {
        return ToolUtil.onBlockDestroyed(stack, world, state, pos, entity, MANA_PER_DAMAGE);
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

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if (hoemode) {
            if (!player.isSneaking() && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.GRASS_PATH || block instanceof FarmlandBlock)) {
                if (special) {
                    return ToolUtil.hoeUse(ctx, true, false, MANA_PER_DAMAGE);
                } else {
                    return ToolUtil.hoeUse(ctx, false, false, MANA_PER_DAMAGE);
                }
            } else {
                if (side != Direction.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)) {
                    return ToolUtil.shovelUse(ctx, MANA_PER_DAMAGE);
                } else {
                    return ActionResultType.PASS;
                }
            }
        } else {
            if (!player.isSneaking()) {
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
            if (player.isSneaking()) {

                ToolUtil.toggleMode(player, itemStack);
            }
        }
        return ActionResult.newResult(ActionResultType.SUCCESS, itemStack);
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

    public static boolean getBindMode(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "hoemode", true);
    }

    public static String getModeString(ItemStack stack) {
        return "aiotbotania." + (getBindMode(stack) ? "hoeMode" : "utilityMode");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent(getModeString(stack)));
    }

}

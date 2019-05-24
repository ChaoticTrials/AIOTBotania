package de.melanx.aiotbotania.items.base;

import com.google.common.collect.Sets;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.blocks.ModBlocks;
import de.melanx.aiotbotania.util.Registry;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ItemAIOTBase extends ItemTool implements IManaUsingItem {

    private int MANA_PER_DAMAGE;
    private boolean special;

    public ItemAIOTBase(String name, IItemTier mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special){
        super(attackDamage, attackSpeed, mat, new HashSet<>(), new Item.Properties().group(AIOTBotania.aiotItemGroup));
//        this.setHarvestLevels(mat.getHarvestLevel());
        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
        Registry.registerItem(this, name);
        Registry.registerModel(this);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.inventoryTick(stack, world, player, MANA_PER_DAMAGE);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase) {
        return ToolUtil.hitEntity(par1ItemStack, par3EntityLivingBase, MANA_PER_DAMAGE);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity) {
        return ToolUtil.onBlockDestroyed(stack, world, state, pos, entity, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemUseContext ctx){
        World world = ctx.getWorld();
        BlockPos pos = ctx.getPos();
        EntityPlayer player = ctx.getPlayer();
        ItemStack stack = ctx.getItem();
        EnumFacing side = ctx.getFace();

        Block block = world.getBlockState(pos).getBlock();

        boolean hoemode = ItemNBTHelper.getBoolean(stack, "hoemode", true);

        if(hoemode) {
            if(!player.isSneaking() && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.GRASS_PATH || block == Blocks.FARMLAND || block == ModBlocks.superFarmland)) {
                if (special){
                    return ToolUtil.hoeUse(ctx, true, false, MANA_PER_DAMAGE);
                } else {
                    return ToolUtil.hoeUse(ctx, false, false, MANA_PER_DAMAGE);
                }
            } else {
                if (side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)) {
                    return ToolUtil.shovelUse(ctx, MANA_PER_DAMAGE);
                }else{
                    return EnumActionResult.PASS;
                }
            }
        } else {
            if(!player.isSneaking()) {
                return ToolUtil.pickUse(ctx);
            } else {
                if(side == EnumFacing.UP){
                    return ToolUtil.axeUse(ctx);
                }
                return EnumActionResult.PASS;
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!world.isRemote) {
            if(player.isSneaking()) {

                ToolUtil.toggleMode(player, itemStack);
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, IBlockState state){
        if(state.getBlock() == Blocks.COBWEB){
            return 15.0F;
        }
        else{
            return state.getBlock().getHarvestTool(state) == null || this.getToolTypes(stack).contains(state.getBlock().getHarvestTool(state)) ? this.efficiency : 1.0F;
        }
    }

//    @Nonnull
//    @Override
//    public Set<String> getToolClasses(ItemStack stack){
//        return Sets.newHashSet("pickaxe", "axe", "shovel");
//    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

//    private void setHarvestLevels(int amount){
//        for(String s : this.getToolClasses(null)){
//            this.setHarvestLevels(amount);
//        }
//    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.type.canEnchantItem(Items.DIAMOND_SWORD);
    }

    // by Ellpeck (ItemAllToolAA.java by Actually Additions)
    @Override
    public boolean canHarvestBlock(IBlockState state){
        return state.getMaterial().isToolNotRequired() || (state.getBlock() == Blocks.SNOW || state.getBlock() == Blocks.SNOW || (state.getBlock() == Blocks.OBSIDIAN ? this.getTier().getHarvestLevel() >= 3 : (state.getBlock() != Blocks.DIAMOND_BLOCK && state.getBlock() != Blocks.DIAMOND_ORE ? (state.getBlock() != Blocks.EMERALD_ORE && state.getBlock() != Blocks.EMERALD_BLOCK ? (state.getBlock() != Blocks.GOLD_BLOCK && state.getBlock() != Blocks.GOLD_ORE ? (state.getBlock() != Blocks.IRON_BLOCK && state.getBlock() != Blocks.IRON_ORE ? (state.getBlock() != Blocks.LAPIS_BLOCK && state.getBlock() != Blocks.LAPIS_ORE ? (state.getBlock() != Blocks.REDSTONE_ORE && state.getBlock() != Blocks.REDSTONE_ORE ? (state.getMaterial() == Material.ROCK || (state.getMaterial() == Material.IRON || state.getMaterial() == Material.ANVIL)) : this.getTier().getHarvestLevel() >= 2) : this.getTier().getHarvestLevel() >= 1) : this.getTier().getHarvestLevel() >= 1) : this.getTier().getHarvestLevel() >= 2) : this.getTier().getHarvestLevel() >= 2) : this.getTier().getHarvestLevel() >= 2)));
    }

}

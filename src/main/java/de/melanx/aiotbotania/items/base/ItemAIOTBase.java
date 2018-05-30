package de.melanx.aiotbotania.items.base;

import com.google.common.collect.Sets;
import de.melanx.aiotbotania.blocks.ModBlocks;
import de.melanx.aiotbotania.util.Registry;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ItemAIOTBase extends ItemTool implements IManaUsingItem {

    private int MANA_PER_DAMAGE;
    private boolean special;

    public ItemAIOTBase(String name, ToolMaterial mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special){
        super(attackDamage, attackSpeed, mat, new HashSet<>());
        this.setHarvestLevels(mat.getHarvestLevel());
        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.special = special;
        Registry.registerItem(this, name);
        Registry.registerModel(this);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.onUpdate(stack, world, player, MANA_PER_DAMAGE);
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
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        Block block = world.getBlockState(pos).getBlock();

        boolean hoemode = ItemNBTHelper.getBoolean(player.getHeldItem(hand), "hoemode", true);

        if(hoemode) {
            if(!player.isSneaking() && (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRASS_PATH || block == Blocks.FARMLAND || block == ModBlocks.superfarmland)) {
                if (special) return ToolUtil.hoeUse(player, world, pos, hand, side, true, MANA_PER_DAMAGE);
                else return ToolUtil.hoeUse(player, world, pos, hand, side, false, MANA_PER_DAMAGE);
            } else {
                return ToolUtil.shovelUse(player, world, pos, hand, side, MANA_PER_DAMAGE);
            }
        } else {
            if(!player.isSneaking()) {
                return ToolUtil.pickUse(player, world, pos, hand, side, hitX, hitY, hitZ);
            } else {
                return ToolUtil.axeUse(player, world, pos, hand, side, hitX, hitY, hitZ);
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if(player.isSneaking()) {
            RayTraceResult result = player.rayTrace(5, 1.0F);

            if (result != null) {
                BlockPos block = result.getBlockPos();

                if (world.isAirBlock(block)) {
                    ToolUtil.changeMode(player, itemStack);
                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
                }
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, IBlockState state){
        if(state.getBlock() == Blocks.WEB){
            return 15.0F;
        }
        else{
            return state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state).isEmpty() || this.getToolClasses(stack).contains(state.getBlock().getHarvestTool(state)) ? this.efficiency : 1.0F;
        }
    }

    @Nonnull
    @Override
    public Set<String> getToolClasses(ItemStack stack){
        return Sets.newHashSet("pickaxe", "axe", "shovel");
    }


    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
        return par2ItemStack.getItem() == vazkii.botania.common.item.ModItems.manaResource && par2ItemStack.getItemDamage() == 0 || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

    private void setHarvestLevels(int amount){
        for(String s : this.getToolClasses(null)){
            this.setHarvestLevel(s, amount);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.type.canEnchantItem(Items.DIAMOND_SWORD);
    }
}

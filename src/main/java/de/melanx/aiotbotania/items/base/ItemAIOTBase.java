package de.melanx.aiotbotania.items.base;

import com.google.common.collect.Sets;
import de.melanx.aiotbotania.Registry;
import de.melanx.aiotbotania.blocks.ModBlocks;
import de.melanx.aiotbotania.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ItemAIOTBase extends ItemTool implements IManaUsingItem {

    private int MANA_PER_DAMAGE;
    private boolean fertilizer;

    public ItemAIOTBase(String name, ToolMaterial mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean fertilizer) {
        super(attackDamage, attackSpeed, mat, new HashSet<>());
        this.setHarvestLevels(mat.getHarvestLevel());
        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
        this.fertilizer = fertilizer;
        Registry.registerItem(this, name);
        Registry.registerModel(this);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase) {
        ToolCommons.damageItem(par1ItemStack, 1, par3EntityLivingBase, MANA_PER_DAMAGE);
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state){
        if(state.getBlock() == Blocks.WEB){
            return 15.0F;
        }
        else{
            return state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state).isEmpty() || this.getToolClasses(stack).contains(state.getBlock().getHarvestTool(state)) ? this.efficiency : 1.0F;
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        Block block = worldIn.getBlockState(pos).getBlock();

        if(!playerIn.isSneaking() && (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRASS_PATH || block == Blocks.FARMLAND || block == ModBlocks.superfarmland)) {
            if (fertilizer) return ModItems.elementiumhoe.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
            else return ModItems.manahoe.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        } else if(!playerIn.isSneaking()) {
            return vazkii.botania.common.item.ModItems.manasteelPick.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        }

        if (block != Blocks.GRASS_PATH) return vazkii.botania.common.item.ModItems.manasteelShovel.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        return EnumActionResult.PASS;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack){
        return Sets.newHashSet("pickaxe", "axe", "shovel");
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
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
}

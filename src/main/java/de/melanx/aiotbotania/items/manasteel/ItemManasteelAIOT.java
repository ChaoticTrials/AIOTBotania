package de.melanx.aiotbotania.items.manasteel;

import com.google.common.collect.Sets;
import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

import static vazkii.botania.api.BotaniaAPI.manasteelToolMaterial;

public class ItemManasteelAIOT extends ItemTool implements IManaUsingItem {

    private static final int MANA_PER_DAMAGE = 60;

    public ItemManasteelAIOT() {
        this(manasteelToolMaterial, "manasteelAIOT");
    }

    private ItemManasteelAIOT(ToolMaterial mat, String name) {
        super(1.0f, 10.0f, mat, new HashSet<>());
        setCreativeTab(AIOTBotania.creativeTab);
        setRegistryName("manasteelAIOT");
        setUnlocalizedName(name);
    }

    public void registerItemModel() {
        AIOTBotania.proxy.registerItemRenderer(this, 0, "manasteelaiot");
    }


    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase) {
        ToolCommons.damageItem(par1ItemStack, 1, par3EntityLivingBase, getManaPerDmg());
        return true;
    }

    public int getManaPerDmg() {
        return MANA_PER_DAMAGE;
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
        if(!playerIn.isSneaking()) return Items.IRON_HOE.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        return Items.IRON_SHOVEL.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
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
        return par2ItemStack.getItem() == vazkii.botania.common.item.ModItems.manaResource && par2ItemStack.getItemDamage() == 0 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}

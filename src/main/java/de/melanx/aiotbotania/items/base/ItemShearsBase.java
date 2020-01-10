package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class ItemShearsBase extends ShearsItem implements IManaUsingItem {

    private int MANA_PER_DAMAGE;

    public ItemShearsBase(int MANA_PER_DAMAGE, int MAX_DMG) {
        super(new Item.Properties().group(AIOTBotania.instance.getTab()).maxStackSize(1).defaultMaxDamage(MAX_DMG));

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        Block block = state.getBlock();
        if (state.isIn(BlockTags.LEAVES) || block == Blocks.COBWEB || block == Blocks.VINE || block == Blocks.GRASS) {
            ItemStack drop = new ItemStack(block);
            world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop));
            ToolCommons.damageItem(stack, 1, entityLiving, MANA_PER_DAMAGE);
        }
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int invSlot, boolean isCurrentItem) {
        if (!world.isRemote && player instanceof PlayerEntity && stack.getDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (PlayerEntity) player, MANA_PER_DAMAGE * 2, true))
            stack.setDamage(stack.getDamage() - 1);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }
}

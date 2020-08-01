package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.function.Consumer;

public class ItemShearsBase extends ShearsItem implements IManaUsingItem {

    private final int MANA_PER_DAMAGE;

    public ItemShearsBase(int MANA_PER_DAMAGE, int MAX_DMG) {
        super(new Item.Properties().group(AIOTBotania.instance.getTab()).maxStackSize(1).defaultMaxDamage(MAX_DMG));

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, 1, entity, MANA_PER_DAMAGE);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int invSlot, boolean isCurrentItem) {
        if (!world.isRemote && player instanceof PlayerEntity && stack.getDamage() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, (PlayerEntity) player, MANA_PER_DAMAGE * 2, true))
            stack.setDamage(stack.getDamage() - 1);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SILK_TOUCH) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}

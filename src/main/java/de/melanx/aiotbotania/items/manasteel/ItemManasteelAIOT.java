package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ModItems;

public class ItemManasteelAIOT extends ItemAIOTBase {
    public ItemManasteelAIOT(IItemTier mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special) {
        super(mat, attackDamage, attackSpeed, MANA_PER_DAMAGE, special);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.type.canEnchantItem(ModItems.manasteelSword);
    }
}

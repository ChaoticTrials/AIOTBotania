package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import vazkii.botania.common.item.ModItems;

public class ItemManasteelAIOT extends ItemAIOTBase {
    public ItemManasteelAIOT(Tier mat, float attackDamage, float attackSpeed, int MANA_PER_DAMAGE, boolean special) {
        super(mat, attackDamage, attackSpeed, MANA_PER_DAMAGE, special);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(ModItems.manasteelSword);
    }
}

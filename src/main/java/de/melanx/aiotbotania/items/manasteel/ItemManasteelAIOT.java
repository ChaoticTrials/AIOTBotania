package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import vazkii.botania.common.item.BotaniaItems;

public class ItemManasteelAIOT extends ItemAIOTBase {

    public ItemManasteelAIOT(Tier tier, float attackDamage, float attackSpeed, int manaPerDamage, boolean special) {
        super(tier, attackDamage, attackSpeed, manaPerDamage, special);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(BotaniaItems.manasteelSword);
    }
}

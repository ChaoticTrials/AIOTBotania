package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAxeBase;

public class ItemLivingwoodAxe extends ItemAxeBase {

    private static final int MANA_PER_DAMAGE = 30;
    private static final float ATTACK_DAMAGE = 6.0F;
    private static final float ATTACK_SPEED = -3.2F;

    public ItemLivingwoodAxe() {
        super("livingwood_axe", ItemTiers.LIVINGWOOD_ITEM_TIER, MANA_PER_DAMAGE, ATTACK_DAMAGE, ATTACK_SPEED);
    }

}

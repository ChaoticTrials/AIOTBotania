package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemSwordBase;

public class ItemLivingwoodSword extends ItemSwordBase {

    private static final int MANA_PER_DAMAGE = 30;
    private static final int DAMAGE = 3;
    private static final float SPEED = -2.5F;

    public ItemLivingwoodSword() {
        super("livingwood_sword", ItemTiers.LIVINGWOOD_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE);
    }

}

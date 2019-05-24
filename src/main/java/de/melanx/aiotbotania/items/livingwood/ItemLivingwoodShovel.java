package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemShovelBase;

public class ItemLivingwoodShovel extends ItemShovelBase {

    private static final int MANA_PER_DAMAGE = 30;
    private static final int DAMAGE = 1;
    private static final float SPEED = -3.0F;

    public ItemLivingwoodShovel() {
        super("livingwood_shovel", ItemTiers.LIVINGWOOD_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE);
    }

}


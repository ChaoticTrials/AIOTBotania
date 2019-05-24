package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemHoeBase;

public class ItemLivingwoodHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 30;
    private static final int SPEED = -3;

    public ItemLivingwoodHoe() {
        super("livingwood_hoe", ItemTiers.LIVINGWOOD_ITEM_TIER, SPEED, MANA_PER_DAMAGE, false, true);
    }

}

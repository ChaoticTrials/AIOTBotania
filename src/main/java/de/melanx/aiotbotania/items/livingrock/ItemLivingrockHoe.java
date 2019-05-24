package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemHoeBase;

public class ItemLivingrockHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final int SPEED = -2;

    public ItemLivingrockHoe() {
        super("livingrock_hoe", ItemTiers.LIVINGROCK_ITEM_TIER, SPEED, MANA_PER_DAMAGE, false, true);
    }

}

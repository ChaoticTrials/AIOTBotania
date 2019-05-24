package de.melanx.aiotbotania.items.elementium;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import vazkii.botania.api.BotaniaAPI;

public class ItemElementiumHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 60;
    private static final int SPEED = -1;

    public ItemElementiumHoe() {
        super("elementium_hoe", BotaniaAPI.ELEMENTIUM_ITEM_TIER, SPEED, MANA_PER_DAMAGE, true, false);
    }

}

package de.melanx.aiotbotania.items.elementium;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import vazkii.botania.api.BotaniaAPI;

public class ItemElementiumHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 60;

    public ItemElementiumHoe() {
        super("elementiumHoe", BotaniaAPI.elementiumToolMaterial, MANA_PER_DAMAGE, true);
    }
}

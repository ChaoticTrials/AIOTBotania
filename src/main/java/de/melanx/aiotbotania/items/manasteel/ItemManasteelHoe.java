package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import vazkii.botania.api.BotaniaAPI;

public class ItemManasteelHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 1000;

    public ItemManasteelHoe() {
        super( "manasteelHoe", BotaniaAPI.manasteelToolMaterial, MANA_PER_DAMAGE);
    }
}

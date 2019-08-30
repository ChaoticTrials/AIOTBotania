package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaUsingItem;

public class ItemManasteelHoe extends ItemHoeBase implements IManaUsingItem {

    private static final int MANA_PER_DAMAGE = 60;
    private static final int SPEED = -1;

    public ItemManasteelHoe() {
        super("manasteel_hoe", BotaniaAPI.MANASTEEL_ITEM_TIER, SPEED, MANA_PER_DAMAGE, false, false);
    }

}

package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAIOTBase;

public class ItemManasteelAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 66;
    private static final float DAMAGE = 6.0F;
    private static final float SPEED = -2.2F;

    public ItemManasteelAIOT() {
        super("manasteel_aiot", ItemTiers.MANASTEEL_AIOT_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE, false);
    }

}

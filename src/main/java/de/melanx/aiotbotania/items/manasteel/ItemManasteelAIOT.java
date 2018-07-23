package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import vazkii.botania.api.BotaniaAPI;

public class ItemManasteelAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 66;

    public ItemManasteelAIOT() {
        super("manasteelAIOT", BotaniaAPI.manasteelToolMaterial,6.0f, -2.2f, MANA_PER_DAMAGE, false);
    }

}

package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import vazkii.botania.api.BotaniaAPI;

public class ItemManasteelAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 60;

    public ItemManasteelAIOT() {
        super("manasteelAIOT", BotaniaAPI.manasteelToolMaterial,1.0f, 10.0f, MANA_PER_DAMAGE);
    }

}

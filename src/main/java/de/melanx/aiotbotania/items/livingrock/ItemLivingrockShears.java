package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.base.ItemShearsBase;

public class ItemLivingrockShears extends ItemShearsBase {

    public static final int MANA_PER_DAMAGE = 10;
    public static final int MAX_DMG = 119;

    public ItemLivingrockShears() {
        super("livingrockShears", MANA_PER_DAMAGE, MAX_DMG);
    }

}

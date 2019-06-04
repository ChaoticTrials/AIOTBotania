package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.base.ItemShearsBase;

public class ItemLivingwoodShears extends ItemShearsBase {

    public static final int MANA_PER_DAMAGE = 10;
    public static final int MAX_DMG = 60;

    public ItemLivingwoodShears() {
        super("livingwoodShears", MANA_PER_DAMAGE, MAX_DMG);
    }

}

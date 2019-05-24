package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemSwordBase;

public class ItemLivingrockSword extends ItemSwordBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final int DAMAGE = 2;
    private static final float SPEED = -2.4F;

    public ItemLivingrockSword() {
        super("livingrock_sword", ItemTiers.LIVINGROCK_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE);
    }

}

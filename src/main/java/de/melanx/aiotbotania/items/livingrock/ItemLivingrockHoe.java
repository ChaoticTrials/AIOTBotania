package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemHoeBase;

public class ItemLivingrockHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 40;

    public ItemLivingrockHoe() {
        super("livingrockHoe", ToolMaterials.livingrockMaterial, MANA_PER_DAMAGE, false, true);
    }

}

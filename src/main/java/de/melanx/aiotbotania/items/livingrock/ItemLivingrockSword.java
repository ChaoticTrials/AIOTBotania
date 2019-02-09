package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemSwordBase;

public class ItemLivingrockSword extends ItemSwordBase {

    private static final int MANA_PER_DAMAGE = 40;

    public ItemLivingrockSword() {
        super("livingrockSword", ToolMaterials.livingrockMaterial, MANA_PER_DAMAGE);
    }

}

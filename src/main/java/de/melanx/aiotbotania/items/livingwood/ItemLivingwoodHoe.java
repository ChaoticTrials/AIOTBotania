package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemHoeBase;

public class ItemLivingwoodHoe extends ItemHoeBase {

    private static final int MANA_PER_DAMAGE = 30;

    public ItemLivingwoodHoe() {
        super("livingwoodHoe", ToolMaterials.livingwoodMaterial, MANA_PER_DAMAGE, false);
    }

}

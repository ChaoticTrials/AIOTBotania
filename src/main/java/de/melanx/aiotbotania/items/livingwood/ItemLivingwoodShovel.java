package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemShovelBase;

public class ItemLivingwoodShovel extends ItemShovelBase {

    private static final int MANA_PER_DAMAGE = 30;

    public ItemLivingwoodShovel() {
        super("livingwoodShovel", ToolMaterials.livingwoodMaterial, MANA_PER_DAMAGE);
    }

}

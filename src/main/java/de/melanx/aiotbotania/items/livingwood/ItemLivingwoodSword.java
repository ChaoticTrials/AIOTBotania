package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemSwordBase;

public class ItemLivingwoodSword extends ItemSwordBase {

    private static final int MANA_PER_DAMAGE = 30;

    public ItemLivingwoodSword() {
        super("livingwoodSword", ToolMaterials.livingwoodMaterial, MANA_PER_DAMAGE);
    }

}

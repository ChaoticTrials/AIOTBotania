package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemPickaxeBase;

public class ItemLivingwoodPickaxe extends ItemPickaxeBase {

    private static final int MANA_PER_DAMAGE = 30;

    public ItemLivingwoodPickaxe() {
        super("livingwoodPickaxe", ToolMaterials.livingwoodMaterial, MANA_PER_DAMAGE);
    }

}

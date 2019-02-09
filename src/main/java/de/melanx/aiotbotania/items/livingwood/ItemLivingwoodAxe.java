package de.melanx.aiotbotania.items.livingwood;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemAxeBase;

public class ItemLivingwoodAxe extends ItemAxeBase {

    private static final int MANA_PER_DAMAGE = 30;
    private static final float ATTACK_DAMAGE = 6.5F;
    private static final float ATTACK_SPEED = -3.2F;

    public ItemLivingwoodAxe() {
        super("livingwoodAxe", ToolMaterials.livingwoodMaterial, MANA_PER_DAMAGE, ATTACK_DAMAGE, ATTACK_SPEED);
    }

}

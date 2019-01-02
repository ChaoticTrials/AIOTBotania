package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraftforge.common.util.EnumHelper;

public class ItemManasteelAIOT extends ItemAIOTBase {

    private static final int MANA_PER_DAMAGE = 66;
    public static final ToolMaterial manasteelAIOTMaterial;

    static {
        manasteelAIOTMaterial = EnumHelper.addToolMaterial("MANASTEEL_AIOT", 3, 300 * 5, 6.2F, 2.0F, 20);
    }

    public ItemManasteelAIOT() {
        super("manasteelAIOT", manasteelAIOTMaterial,6.0f, -2.2f, MANA_PER_DAMAGE, false);
    }

}

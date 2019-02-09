package de.melanx.aiotbotania.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterials {

    public static final Item.ToolMaterial livingwoodMaterial;
    public static final Item.ToolMaterial livingrockMaterial;

    public static final Item.ToolMaterial manasteelAIOTMaterial;
    public static final Item.ToolMaterial elementiumAIOTMaterial;
    public static final Item.ToolMaterial livingwoodAIOTMaterial;
    public static final Item.ToolMaterial livingrockAIOTMaterial;

    static {
        // basic materials
        livingwoodMaterial = EnumHelper.addToolMaterial("LIVINGWOOD", 0, 68, 2.0F, 0.5F, 18);
        livingrockMaterial = EnumHelper.addToolMaterial("LIVINGROCK", 1, 191, 4.5F, 1.5F, 10);

        // AIOT materials
        livingwoodAIOTMaterial = EnumHelper.addToolMaterial("LIVINGWOOD_AIOT", 3, 68 * 5, 2.0F, 0.5F, 18);
        livingrockAIOTMaterial = EnumHelper.addToolMaterial("LIVINGROCK_AIOT", 1, 191 * 5, 4.5F, 2.5F, 10);
        manasteelAIOTMaterial = EnumHelper.addToolMaterial("MANASTEEL_AIOT", 3, 300 * 5, 6.2F, 2.0F, 20);
        elementiumAIOTMaterial = EnumHelper.addToolMaterial("ELEMENTIUM_AIOT", 3, 720 * 5, 6.2F, 2.0F, 20);
    }

}

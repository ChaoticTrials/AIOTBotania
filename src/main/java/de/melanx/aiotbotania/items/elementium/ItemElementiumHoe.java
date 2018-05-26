package de.melanx.aiotbotania.items.elementium;

import de.melanx.aiotbotania.items.manasteel.ItemManasteelHoe;
import net.minecraftforge.common.MinecraftForge;
import vazkii.botania.api.BotaniaAPI;

public class ItemElementiumHoe extends ItemManasteelHoe {

    public ItemElementiumHoe() {
        super(BotaniaAPI.elementiumToolMaterial, "elementiumHoe");
        MinecraftForge.EVENT_BUS.register(this);
    }

    //To be continued

}

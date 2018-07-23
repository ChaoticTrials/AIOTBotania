package de.melanx.aiotbotania.items.elementium;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPixieSpawner;

public class ItemElementiumAIOT extends ItemAIOTBase implements IPixieSpawner {

    private static final int MANA_PER_DAMAGE = 66;

    public ItemElementiumAIOT() {
        super("elementiumAIOT", BotaniaAPI.elementiumToolMaterial, 6.0f, -2.2f, MANA_PER_DAMAGE, true);
    }

    @Override
    public float getPixieChance(ItemStack stack) {
        return 0.1F;
    }
}

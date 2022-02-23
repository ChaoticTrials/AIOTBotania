package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemShovelBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

import javax.annotation.Nonnull;

public class ItemLivingrockShovel extends ItemShovelBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final int DAMAGE = 1;
    private static final float SPEED = -3.0F;

    public ItemLivingrockShovel() {
        super(ItemTiers.LIVINGROCK_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        return ToolUtil.shovelUse(context);
    }
}

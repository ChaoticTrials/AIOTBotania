package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemAxeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

public class ItemLivingrockAxe extends ItemAxeBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final float ATTACK_DAMAGE = 6.0F;
    private static final float ATTACK_SPEED = -3.1F;

    public ItemLivingrockAxe() {
        super("livingrock_axe", ItemTiers.LIVINGROCK_ITEM_TIER, MANA_PER_DAMAGE, ATTACK_DAMAGE, ATTACK_SPEED);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemUseContext ctx) {
        EnumFacing side = ctx.getFace();
        if(side == EnumFacing.UP) {
            return ToolUtil.axeUse(ctx);
        }
        return EnumActionResult.PASS;
    }

}


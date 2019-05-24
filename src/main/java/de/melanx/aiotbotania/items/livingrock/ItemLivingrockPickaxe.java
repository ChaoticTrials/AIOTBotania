package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemPickaxeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;

import javax.annotation.Nonnull;

public class ItemLivingrockPickaxe extends ItemPickaxeBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final int DAMAGE = 0;
    private static final float SPEED = -2.8F;

    public ItemLivingrockPickaxe() {
        super("livingrock_pickaxe", ItemTiers.LIVINGROCK_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull ItemUseContext ctx) {
        EntityPlayer player = ctx.getPlayer();

        if(!player.isSneaking()) {
            return ToolUtil.pickUse(ctx);
        }
        return EnumActionResult.PASS;
    }
}

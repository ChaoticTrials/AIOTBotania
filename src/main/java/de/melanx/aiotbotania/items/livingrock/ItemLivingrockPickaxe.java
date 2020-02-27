/*
 * This file is part of AIOT Botania.
 *
 * Copyright 2018-2020, MelanX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.ItemPickaxeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class ItemLivingrockPickaxe extends ItemPickaxeBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final int DAMAGE = 0;
    private static final float SPEED = -2.8F;

    public ItemLivingrockPickaxe() {
        super(ItemTiers.LIVINGROCK_ITEM_TIER, DAMAGE, SPEED, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        if (!player.isCrouching()) {
            return ToolUtil.pickUse(ctx);
        }
        return ActionResultType.PASS;
    }
}

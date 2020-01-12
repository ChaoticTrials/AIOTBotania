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
package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {

    private NonNullList<ItemStack> list;

    public CreativeTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Registration.elementium_aiot.get());
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        addItem(Registration.livingwood_shovel.get());
        addItem(Registration.livingwood_pickaxe.get());
        addItem(Registration.livingwood_axe.get());
        addItem(Registration.livingwood_sword.get());
        addItem(Registration.livingwood_hoe.get());
        addItem(Registration.livingwood_aiot.get());
        addItem(Registration.livingwood_shears.get());

        addItem(Registration.livingrock_shovel.get());
        addItem(Registration.livingrock_pickaxe.get());
        addItem(Registration.livingrock_axe.get());
        addItem(Registration.livingrock_sword.get());
        addItem(Registration.livingrock_hoe.get());
        addItem(Registration.livingrock_aiot.get());
        addItem(Registration.livingrock_shears.get());

        addItem(Registration.manasteel_hoe.get());
        addItem(Registration.manasteel_aiot.get());

        addItem(Registration.elementium_hoe.get());
        addItem(Registration.elementium_aiot.get());
    }

    private void addItem(IItemProvider item) {
        item.asItem().fillItemGroup(this, list);
    }

}

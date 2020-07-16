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
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {

    public CreativeTab() {
        super(AIOTBotania.MODID);
    }

    @Override
    @Nonnull
    public ItemStack createIcon() {
        return new ItemStack(Registration.elementium_aiot.get());
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        Registration.ITEMS.getEntries().forEach(entry -> entry.get().fillItemGroup(this, list));
    }
}

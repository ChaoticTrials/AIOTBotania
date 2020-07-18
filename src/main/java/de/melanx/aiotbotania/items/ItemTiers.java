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
package de.melanx.aiotbotania.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.api.BotaniaAPI;

import javax.annotation.Nonnull;

public class ItemTiers {
    private static final BotaniaAPI botaniaAPI = BotaniaAPI.instance();

    private ItemTiers() { }

    // basic tiers
    public static final IItemTier LIVINGWOOD_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 68;
        }

        @Override
        public float getEfficiency() {
            return 2.0F;
        }

        @Override
        public float getAttackDamage() {
            return 0.5F;
        }

        @Override
        public int getHarvestLevel() {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 18;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania", "livingwood"));
            return Ingredient.fromItems(item);
        }
    };

    public static final IItemTier LIVINGROCK_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 191;
        }

        @Override
        public float getEfficiency() {
            return 4.5F;
        }

        @Override
        public float getAttackDamage() {
            return 2.5F;
        }

        @Override
        public int getHarvestLevel() {
            return 1;
        }

        @Override
        public int getEnchantability() {
            return 10;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania", "livingrock"));
            return Ingredient.fromItems(item);
        }
    };

    // AIOT tiers
    public static final IItemTier LIVINGWOOD_AIOT_ITEM_TIER = new DelegatedItemTier(LIVINGWOOD_ITEM_TIER) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    };

    public static final IItemTier LIVINGROCK_AIOT_ITEM_TIER = new DelegatedItemTier(LIVINGROCK_ITEM_TIER) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    };

    public static final IItemTier MANASTEEL_AIOT_ITEM_TIER = new DelegatedItemTier(botaniaAPI.getManasteelItemTier()) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    };

    public static final IItemTier ELEMENTIUM_AIOT_ITEM_TIER = new DelegatedItemTier(botaniaAPI.getElementiumItemTier()) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    };

    /**
     * Helper class to delegate values to another IItemTier
     */
    private static class DelegatedItemTier implements IItemTier {
        protected IItemTier delegate;

        protected DelegatedItemTier(IItemTier delegate) {
            this.delegate = delegate;
        }

        @Override
        public int getMaxUses() {
            return delegate.getMaxUses();
        }

        @Override
        public float getEfficiency() {
            return delegate.getEfficiency();
        }

        @Override
        public float getAttackDamage() {
            return delegate.getAttackDamage();
        }

        @Override
        public int getHarvestLevel() {
            return delegate.getHarvestLevel();
        }

        @Override
        public int getEnchantability() {
            return delegate.getEnchantability();
        }

        @Override
        @Nonnull
        public Ingredient getRepairMaterial() {
            return delegate.getRepairMaterial();
        }
    }
}

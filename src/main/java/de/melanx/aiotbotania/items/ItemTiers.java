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

public class ItemTiers {

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
    public static final IItemTier LIVINGWOOD_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return LIVINGWOOD_ITEM_TIER.getMaxUses() * 5;
        }

        @Override
        public float getEfficiency() {
            return LIVINGWOOD_ITEM_TIER.getEfficiency();
        }

        @Override
        public float getAttackDamage() {
            return LIVINGWOOD_ITEM_TIER.getAttackDamage();
        }

        @Override
        public int getHarvestLevel() {
            return LIVINGWOOD_ITEM_TIER.getHarvestLevel();
        }

        @Override
        public int getEnchantability() {
            return LIVINGWOOD_ITEM_TIER.getEnchantability();
        }

        @Override
        public Ingredient getRepairMaterial() {
            return LIVINGWOOD_ITEM_TIER.getRepairMaterial();
        }
    };

    public static final IItemTier LIVINGROCK_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return LIVINGROCK_ITEM_TIER.getMaxUses() * 5;
        }

        @Override
        public float getEfficiency() {
            return LIVINGROCK_ITEM_TIER.getEfficiency();
        }

        @Override
        public float getAttackDamage() {
            return LIVINGROCK_ITEM_TIER.getAttackDamage();
        }

        @Override
        public int getHarvestLevel() {
            return LIVINGROCK_ITEM_TIER.getHarvestLevel();
        }

        @Override
        public int getEnchantability() {
            return LIVINGROCK_ITEM_TIER.getEnchantability();
        }

        @Override
        public Ingredient getRepairMaterial() {
            return LIVINGROCK_ITEM_TIER.getRepairMaterial();
        }
    };

    public static final IItemTier MANASTEEL_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return BotaniaAPI.instance().getManasteelItemTier().getMaxUses() * 5;
        }

        @Override
        public float getEfficiency() {
            return BotaniaAPI.instance().getManasteelItemTier().getEfficiency();
        }

        @Override
        public float getAttackDamage() {
            return BotaniaAPI.instance().getManasteelItemTier().getAttackDamage();
        }

        @Override
        public int getHarvestLevel() {
            return BotaniaAPI.instance().getManasteelItemTier().getHarvestLevel();
        }

        @Override
        public int getEnchantability() {
            return BotaniaAPI.instance().getManasteelItemTier().getEnchantability();
        }

        @Override
        public Ingredient getRepairMaterial() {
            return BotaniaAPI.instance().getManasteelItemTier().getRepairMaterial();
        }
    };

    public static final IItemTier ELEMENTIUM_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return BotaniaAPI.instance().getElementiumItemTier().getMaxUses() * 5;
        }

        @Override
        public float getEfficiency() {
            return BotaniaAPI.instance().getElementiumItemTier().getEfficiency();
        }

        @Override
        public float getAttackDamage() {
            return BotaniaAPI.instance().getElementiumItemTier().getAttackDamage();
        }

        @Override
        public int getHarvestLevel() {
            return BotaniaAPI.instance().getElementiumItemTier().getHarvestLevel();
        }

        @Override
        public int getEnchantability() {
            return BotaniaAPI.instance().getElementiumItemTier().getEnchantability();
        }

        @Override
        public Ingredient getRepairMaterial() {
            return BotaniaAPI.instance().getElementiumItemTier().getRepairMaterial();
        }
    };

}

package de.melanx.aiotbotania.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

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
            return 68 * 5;
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

    public static final IItemTier LIVINGROCK_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 191 * 5;
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

    public static final IItemTier MANASTEEL_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 300 * 5;
        }

        @Override
        public float getEfficiency() {
            return 6.2F;
        }

        @Override
        public float getAttackDamage() {
            return 2;
        }

        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getEnchantability() {
            return 20;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania", "manasteel_ingot"));
            return Ingredient.fromItems(item);
        }
    };

    public static final IItemTier ELEMENTIUM_AIOT_ITEM_TIER = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 720 * 5;
        }

        @Override
        public float getEfficiency() {
            return 6.2F;
        }

        @Override
        public float getAttackDamage() {
            return 2;
        }

        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getEnchantability() {
            return 20;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania", "elementium_ingot"));
            return Ingredient.fromItems(item);
        }
    };

}

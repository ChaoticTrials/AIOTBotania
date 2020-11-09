package de.melanx.aiotbotania.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.lib.ModTags;

import java.util.function.Supplier;

public enum ItemTiers implements IItemTier {
    LIVINGWOOD_ITEM_TIER(68, 2.0F, 0.5F, 0, 18, () -> Ingredient.fromTag(ModTags.Items.LIVINGWOOD)),
    LIVINGROCK_ITEM_TIER(191, 4.5F, 2.5F, 1, 10, () -> Ingredient.fromTag(ModTags.Items.LIVINGROCK)),
    LIVINGWOOD_AIOT_ITEM_TIER(LIVINGWOOD_ITEM_TIER) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    },
    LIVINGROCK_AIOT_ITEM_TIER(LIVINGROCK_ITEM_TIER) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    },
    MANASTEEL_AIOT_ITEM_TIER(BotaniaAPI.instance().getManasteelItemTier()),
    ELEMENTIUM_AIOT_ITEM_TIER(BotaniaAPI.instance().getElementiumItemTier()),
    TERRASTEEL_AIOT_ITEM_TIER(BotaniaAPI.instance().getTerrasteelItemTier()) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    },

    ALFSTEEL_ITEM_TIER(BotaniaAPI.instance().getTerrasteelItemTier()) {
        @Override
        public int getMaxUses() {
            return 4600;
        }
    },
    ALFSTEEL_AIOT_ITEM_TIER(ALFSTEEL_ITEM_TIER) {
        @Override
        public int getMaxUses() {
            return super.getMaxUses() * 5;
        }
    };

    private final int durability;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    ItemTiers(int durability, double efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.durability = durability;
        this.efficiency = (float) efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    ItemTiers(IItemTier delegate) {
        durability = delegate.getMaxUses();
        efficiency = delegate.getEfficiency();
        attackDamage = delegate.getAttackDamage();
        harvestLevel = delegate.getHarvestLevel();
        enchantability = delegate.getEnchantability();
        repairMaterial = new LazyValue<>(delegate::getRepairMaterial);
    }

    @Override
    public int getMaxUses() {
        return this.durability;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}

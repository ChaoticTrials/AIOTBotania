package de.melanx.aiotbotania.items;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.lib.ModTags;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum ItemTiers implements Tier {
    LIVINGWOOD_ITEM_TIER(68, 2.0F, 0.5F, 0, 18, () -> Ingredient.of(ModTags.Items.LIVINGWOOD_LOGS)),
    LIVINGROCK_ITEM_TIER(191, 4.5F, 2.5F, 1, 10, () -> Ingredient.of(ModBlocks.livingrock)),
    LIVINGWOOD_AIOT_ITEM_TIER(LIVINGWOOD_ITEM_TIER) {
        @Override
        public int getUses() {
            return super.getUses() * 5;
        }
    },
    LIVINGROCK_AIOT_ITEM_TIER(LIVINGROCK_ITEM_TIER) {
        @Override
        public int getUses() {
            return super.getUses() * 5;
        }
    },
    MANASTEEL_AIOT_ITEM_TIER(BotaniaAPI.instance().getManasteelItemTier()),
    ELEMENTIUM_AIOT_ITEM_TIER(BotaniaAPI.instance().getElementiumItemTier()),
    TERRASTEEL_AIOT_ITEM_TIER(BotaniaAPI.instance().getTerrasteelItemTier()) {
        @Override
        public int getUses() {
            return super.getUses() * 5;
        }
    },

    ALFSTEEL_ITEM_TIER(BotaniaAPI.instance().getTerrasteelItemTier()) {
        @Override
        public int getUses() {
            return 4600;
        }
    },
    ALFSTEEL_AIOT_ITEM_TIER(ALFSTEEL_ITEM_TIER) {
        @Override
        public int getUses() {
            return super.getUses() * 5;
        }
    };

    private final int durability;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;
    @SuppressWarnings("deprecation")
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ItemTiers(int durability, double efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.durability = durability;
        this.efficiency = (float) efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        //noinspection deprecation
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    ItemTiers(Tier delegate) {
        this.durability = delegate.getUses();
        this.efficiency = delegate.getSpeed();
        this.attackDamage = delegate.getAttackDamageBonus();
        //noinspection deprecation
        this.harvestLevel = delegate.getLevel();
        this.enchantability = delegate.getEnchantmentValue();
        //noinspection deprecation
        this.repairMaterial = new LazyLoadedValue<>(delegate::getRepairIngredient);
    }

    @Override
    public int getUses() {
        return this.durability;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}

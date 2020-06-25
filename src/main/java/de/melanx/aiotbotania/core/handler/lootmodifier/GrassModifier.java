package de.melanx.aiotbotania.core.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.items.base.ItemShearsBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GrassModifier extends LootModifier {
    protected GrassModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.get(LootParameters.BLOCK_STATE);
        ItemStack tool = context.get(LootParameters.TOOL);
        if (state != null && tool != null)
            if (state.getBlock() instanceof TallGrassBlock && (tool.getItem() instanceof ItemShearsBase) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
                return Collections.singletonList(new ItemStack(Tags.Items.SEEDS.getRandomElement(new Random())));
            }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<GrassModifier> {
        @Override
        public GrassModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new GrassModifier(conditions);
        }
    }
}

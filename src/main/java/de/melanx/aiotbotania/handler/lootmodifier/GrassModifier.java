package de.melanx.aiotbotania.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.items.base.ItemShearsBase;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GrassModifier extends LootModifier {

    private static final Random RANDOM = new Random();

    public GrassModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (state != null && tool != null) {
            if (state.getBlock() instanceof TallGrassBlock && (tool.getItem() instanceof ItemShearsBase) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
                return Collections.singletonList(new ItemStack(GrassModifier::getRandomSeed));
            }
        }

        return generatedLoot;
    }

    @Nonnull
    private static Item getRandomSeed() {
        List<Item> items = new ArrayList<>();
        //noinspection deprecation
        for (Holder<Item> itemHolder : Registry.ITEM.getTagOrEmpty(Tags.Items.SEEDS)) {
            items.add(itemHolder.value());
        }

        return items.get(RANDOM.nextInt(items.size()));
    }

    public static class Serializer extends GlobalLootModifierSerializer<GrassModifier> {

        @Override
        public GrassModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] conditions) {
            return new GrassModifier(conditions);
        }

        @Override
        public JsonObject write(GrassModifier instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}

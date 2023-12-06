package de.melanx.aiotbotania.handler.lootmodifier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.melanx.aiotbotania.items.base.ItemShearsBase;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassModifier extends LootModifier {

    public static final Supplier<Codec<GrassModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, GrassModifier::new)));
    private static final Random RANDOM = new Random();

    public GrassModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (state != null && tool != null) {
            if (state.getBlock() instanceof TallGrassBlock && (tool.getItem() instanceof ItemShearsBase) && tool.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
                return ObjectArrayList.of(new ItemStack(GrassModifier::getRandomSeed));
            }
        }

        return generatedLoot;
    }

    @Nonnull
    private static Item getRandomSeed() {
        List<Item> items = new ArrayList<>();
        //noinspection deprecation
        for (Holder<Item> itemHolder : BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.SEEDS)) {
            items.add(itemHolder.value());
        }

        return items.get(RANDOM.nextInt(items.size()));
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}

package de.melanx.aiotbotania.handler.lootmodifier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.alfsteel.ItemAlfsteelAIOT;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import vazkii.botania.common.lib.BotaniaTags;

import javax.annotation.Nonnull;

public class DisposeModifier extends LootModifier {

    public static final Supplier<Codec<DisposeModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, DisposeModifier::new)));

    public DisposeModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    public static void filterDisposable(ObjectArrayList<ItemStack> drops, Entity entity, ItemStack stack) {
        if (!stack.isEmpty() && (stack.getItem() == Registration.elementium_aiot.get()
                || (stack.getItem() == Registration.terrasteel_aiot.get() && ItemTerraSteelAIOT.isTipped(stack))
                || (stack.getItem() == Registration.alfsteel_aiot.get() && ItemAlfsteelAIOT.isTipped(stack)))) {
            drops.removeIf(s -> !s.isEmpty() && (isDisposable(s) || isSemiDisposable(s) && !entity.isCrouching()));
        }
    }

    private static boolean isDisposable(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return stack.is(BotaniaTags.Items.DISPOSABLE);
    }

    private static boolean isSemiDisposable(ItemStack stack) {
        return stack.is(BotaniaTags.Items.SEMI_DISPOSABLE);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (entity != null && tool != null && !tool.isEmpty()) {
            filterDisposable(generatedLoot, entity, tool);
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}

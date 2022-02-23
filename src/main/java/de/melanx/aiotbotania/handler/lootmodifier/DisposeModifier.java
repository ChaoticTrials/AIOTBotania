package de.melanx.aiotbotania.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import vazkii.botania.common.lib.ModTags;

import javax.annotation.Nonnull;
import java.util.List;

public class DisposeModifier extends LootModifier {

    public DisposeModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    public static void filterDisposable(List<ItemStack> drops, Entity entity, ItemStack stack) {
        if (!stack.isEmpty() && (stack.getItem() == Registration.elementium_aiot.get()
                || (stack.getItem() == Registration.terrasteel_aiot.get() && ItemTerraSteelAIOT.isTipped(stack))
                /* || (stack.getItem() == Registration.alfsteel_aiot.get() && ItemAlfsteelAIOT.isTipped(stack)) TODO Alfsteel */)) {
            drops.removeIf(s -> !s.isEmpty() && (isDisposable(s) || isSemiDisposable(s) && !entity.isCrouching()));
        }
    }

    private static boolean isDisposable(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return ModTags.Items.DISPOSABLE.contains(stack.getItem());
    }

    private static boolean isSemiDisposable(ItemStack stack) {
        return ModTags.Items.SEMI_DISPOSABLE.contains(stack.getItem());
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (entity != null && tool != null && !tool.isEmpty()) {
            filterDisposable(generatedLoot, entity, tool);
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<DisposeModifier> {

        @Override
        public DisposeModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] conditions) {
            return new DisposeModifier(conditions);
        }

        @Override
        public JsonObject write(DisposeModifier instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}

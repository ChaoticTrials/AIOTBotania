package de.melanx.aiotbotania.core.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.core.Registration;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import vazkii.botania.common.lib.ModTags;

import javax.annotation.Nonnull;
import java.util.List;

public class DisposeModifier extends LootModifier {
    protected DisposeModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    public static void filterDisposable(List<ItemStack> drops, Entity entity, ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() == Registration.elementium_aiot.get()) {
            drops.removeIf(s -> !s.isEmpty() && (isDisposable(s) || isSemiDisposable(s) && !entity.isCrouching()));
        }
    }

    public static boolean isDisposable(Block block) {
        return ModTags.Items.DISPOSABLE.contains(block.asItem());
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
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        ItemStack tool = context.get(LootParameters.TOOL);
        if (entity != null && tool != null && !tool.isEmpty()) {
            filterDisposable(generatedLoot, entity, tool);
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<DisposeModifier> {
        @Override
        public DisposeModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new DisposeModifier(conditions);
        }
    }
}

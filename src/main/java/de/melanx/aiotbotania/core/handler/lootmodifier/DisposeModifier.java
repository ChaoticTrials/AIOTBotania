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
            drops.removeIf(s -> !s.isEmpty() && (isDisposable(s) || isSemiDisposable(s) && !entity.isSneaking()));
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

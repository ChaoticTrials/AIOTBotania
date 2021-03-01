package de.melanx.aiotbotania.items.base;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.util.function.Consumer;

public class ItemShovelBase extends ShovelItem implements IManaUsingItem {

    protected final int MANA_PER_DAMAGE;

    public ItemShovelBase(IItemTier mat, int damage, float speed, int MANA_PER_DAMAGE) {
        super(mat, damage, speed, new Item.Properties().group(AIOTBotania.instance.getTab()));

        this.MANA_PER_DAMAGE = MANA_PER_DAMAGE;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        ToolUtil.inventoryTick(stack, world, player, this.MANA_PER_DAMAGE);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, this.MANA_PER_DAMAGE);
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }

}

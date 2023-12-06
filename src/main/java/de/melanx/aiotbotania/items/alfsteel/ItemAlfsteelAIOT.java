package de.melanx.aiotbotania.items.alfsteel;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import mythicbotany.alftools.AlfsteelAxe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fml.ModList;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.LensEffectItem;
import vazkii.botania.common.entity.ManaBurstEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemAlfsteelAIOT extends ItemTerraSteelAIOT implements MythicBotany, ModPylonRepairable, LensEffectItem {

    public static final int MANA_PER_DAMAGE = 200;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ItemAlfsteelAIOT() {
        super(ItemTiers.ALFSTEEL_AIOT_ITEM_TIER);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.getAttackDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 2.4, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            if (!level.isClientSide) {
                ItemStack stack = player.getItemInHand(hand);
                stack.hurtAndBreak(1, player, _x -> {
                });
                player.setItemInHand(hand, stack);
            }
            int ITEM_COLLECT_RANGE = AlfsteelAxe.ITEM_COLLECT_RANGE;
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(x - ITEM_COLLECT_RANGE, y - ITEM_COLLECT_RANGE, z - ITEM_COLLECT_RANGE, x + ITEM_COLLECT_RANGE, y + ITEM_COLLECT_RANGE, z + ITEM_COLLECT_RANGE));
            for (ItemEntity item : items) {
                item.moveTo(x + level.random.nextFloat() - 0.5f, y + level.random.nextFloat(), z + level.random.nextFloat() - 0.5f, item.yRot, item.xRot);
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public ManaBurstEntity getBurst(Player player, ItemStack stack) {
        ManaBurstEntity burst = super.getBurst(player, stack);
        if (burst != null) {
            burst.setColor(0xF79100);
            burst.setMana(getManaPerDamage());
            burst.setStartingMana(getManaPerDamage());
            burst.setMinManaLoss(20);
            burst.setManaLossPerTick(2.0F);
        }

        return burst;
    }

    @Override
    public void updateBurst(ManaBurst burst, ItemStack stack) {
        ThrowableProjectile entity = burst.entity();
        AABB aabb = new AABB(
                entity.getX(), entity.getY(), entity.getZ(),
                entity.xOld, entity.yOld, entity.zOld
        ).inflate(1);
        Entity thrower = entity.getOwner();
        //noinspection resource
        List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb);

        for (LivingEntity living : entities) {
            if (living == thrower || living instanceof Player livingPlayer && thrower instanceof Player throwingPlayer && !throwingPlayer.canHarmPlayer(livingPlayer)) {
                continue;
            }

            if (living.hurtTime == 0) {
                int mana = burst.getMana();
                if (mana >= 33) {
                    burst.setMana(mana - 33);
                    float damage = 4 + this.getAttackDamage();
                    //noinspection resource
                    Level level = entity.level();
                    if (!burst.isFake() && !level.isClientSide) {
                        DamageSource source = level.damageSources().magic();
                        if (thrower instanceof Player player) {
                            source = level.damageSources().playerAttack(player);
                        } else if (thrower instanceof LivingEntity livingThrower) {
                            source = level.damageSources().mobAttack(livingThrower);
                        }
                        living.hurt(source, damage);
                        if (burst.getMana() <= 0) entity.discard();
                    }
                }
            }
        }
    }

    public static int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    @Override
    public float getAttackDamage() {
        return 12;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean canRepairPylon(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public int getRepairManaPerTick(ItemStack stack) {
        return (int) (2.5 * MANA_PER_DAMAGE);
    }

    @Override
    public ItemStack repairOneTick(ItemStack stack) {
        stack.setDamageValue(Math.max(0, stack.getDamageValue() - 5));
        return stack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (!ModList.get().isLoaded("mythicbotany")) {
            tooltip.add(Component.translatable(AIOTBotania.MODID + ".mythicbotany.disabled").withStyle(ChatFormatting.DARK_RED));
        } else {
            super.appendHoverText(stack, level, tooltip, flag);
        }
    }

    @Override
    public void apply(ItemStack stack, BurstProperties props, Level level) {

    }

    @Override
    public boolean collideBurst(ManaBurst burst, HitResult pos, boolean isManaBlock, boolean shouldKill, ItemStack stack) {
        return shouldKill;
    }

    @Override
    public boolean doParticles(ManaBurst burst, ItemStack stack) {
        return true;
    }
}

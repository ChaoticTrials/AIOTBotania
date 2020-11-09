package de.melanx.aiotbotania.items.alfsteel;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModList;
import vazkii.botania.common.entity.EntityManaBurst;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

public class ItemAlfsteelAIOT extends ItemTerraSteelAIOT implements MythicBotany, ModPylonRepairable {

    public static final int MANA_PER_DAMAGE = 200;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ItemAlfsteelAIOT() {
        super(ItemTiers.ALFSTEEL_AIOT_ITEM_TIER);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAttackDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 2.4, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        if (player.isSneaking()) {
            double x = player.getPosX();
            double y = player.getPosY();
            double z = player.getPosZ();
            if (!world.isRemote) {
                ItemStack stack = player.getHeldItem(hand);
                stack.damageItem(1, player, _x -> {
                });
                player.setHeldItem(hand, stack);
            }
            int ITEM_COLLECT_RANGE = mythicbotany.alftools.AlfsteelAxe.ITEM_COLLECT_RANGE;
            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(x - ITEM_COLLECT_RANGE, y - ITEM_COLLECT_RANGE, z - ITEM_COLLECT_RANGE, x + ITEM_COLLECT_RANGE, y + ITEM_COLLECT_RANGE, z + ITEM_COLLECT_RANGE));
            for (ItemEntity item : items) {
                item.setLocationAndAngles(x + world.rand.nextFloat() - 0.5f, y + world.rand.nextFloat(), z + world.rand.nextFloat() - 0.5f, item.rotationYaw, item.rotationPitch);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    private void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            mythicbotany.MythicBotany.getNetwork().instance.sendToServer(new mythicbotany.network.AlfSwordLeftClickSerializer.AlfSwordLeftClickMessage());
        }
    }

    @Override
    public EntityManaBurst getBurst(PlayerEntity player, ItemStack stack) {
        EntityManaBurst burst = super.getBurst(player, stack);
        if (burst != null) {
            burst.setColor(0xF79100);
            burst.setMana(getManaPerDamage());
            burst.setStartingMana(getManaPerDamage());
            burst.setMinManaLoss(20);
            burst.setManaLossPerTick(2.0F);
        }
        return burst;
    }

    public static int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    @Override
    public float getAttackDamage() {
        return 12;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public int getRepairManaPerTick(ItemStack stack) {
        return MANA_PER_DAMAGE;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (!ModList.get().isLoaded("mythicbotany")) {
            tooltip.add(new TranslationTextComponent(AIOTBotania.MODID + ".mythicbotany.disabled").mergeStyle(TextFormatting.DARK_RED));
        }
    }
}

package de.melanx.aiotbotania.items.alfsteel;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import vazkii.botania.common.core.helper.PlayerHelper;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.ItemTemperanceStone;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemThorRing;
import vazkii.botania.common.lib.ResourceLocationHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemAlfsteelAIOT extends ItemTerraSteelAIOT implements MythicBotany, ModPylonRepairable {

    public static final int MANA_PER_DAMAGE = 200;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ItemAlfsteelAIOT() {
        super(ItemTiers.ALFSTEEL_AIOT_ITEM_TIER);
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
    public boolean canRepairPylon(ItemStack stack) {
        return stack.getDamage() > 0;
    }

    @Override
    public int getRepairManaPerTick(ItemStack stack) {
        return (int) (2.5 * MANA_PER_DAMAGE);
    }

    @Override
    public ItemStack repairOneTick(ItemStack stack) {
        stack.setDamage(Math.max(0, stack.getDamage() - 5));
        return stack;
    }

    @Override
    public void breakOtherBlock(PlayerEntity player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (isEnabled(stack)) {
            World world = player.world;
            Material mat = world.getBlockState(pos).getMaterial();
            if (MATERIALS.contains(mat) && !world.isAirBlock(pos)) {
                boolean thor = !ItemThorRing.getThorRing(player).isEmpty();
                boolean doX = thor || side.getXOffset() == 0;
                boolean doY = thor || side.getYOffset() == 0;
                boolean doZ = thor || side.getZOffset() == 0;
                int origLevel = getLevel(stack);
                int level = origLevel + (thor ? 1 : 0);
                int rangeDepth = level / 2;
                if (ItemTemperanceStone.hasTemperanceActive(player) && level > 2) {
                    level = 2;
                    rangeDepth = 0;
                }

                int range = level - 1;
                int rangeY = Math.max(1, range);
                if (range != 0 || level == 1) {
                    Vector3i beginDiff = new Vector3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
                    Vector3i endDiff = new Vector3i(doX ? range : rangeDepth * -side.getXOffset(), doY ? rangeY * 2 - 1 : 0, doZ ? range : rangeDepth * -side.getZOffset());
                    ToolCommons.removeBlocksInIteration(player, stack, world, pos, beginDiff, endDiff, (state) -> MATERIALS.contains(state.getMaterial()));
                    if (origLevel == 5) {
                        PlayerHelper.grantCriterion((ServerPlayerEntity)player, ResourceLocationHelper.prefix("challenge/rank_ss_pick"), "code_triggered");
                    }
                }
            }
        }
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        if (!ModList.get().isLoaded("mythicbotany")) {
            tooltip.add(new TranslationTextComponent(AIOTBotania.MODID + ".mythicbotany.disabled").mergeStyle(TextFormatting.DARK_RED));
        } else {
            super.addInformation(stack, world, tooltip, flag);
        }
    }
}

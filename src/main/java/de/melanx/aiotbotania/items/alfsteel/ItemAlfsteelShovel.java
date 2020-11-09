package de.melanx.aiotbotania.items.alfsteel;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.MythicBotany;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraShovel;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import de.melanx.aiotbotania.util.ToolBreakContext;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemAlfsteelShovel extends ItemTerraShovel implements MythicBotany, ModPylonRepairable {

    public ItemAlfsteelShovel() {
        super(ItemTiers.ALFSTEEL_AIOT_ITEM_TIER);
    }

    @Override
    public void breakOtherBlock(PlayerEntity player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (ItemTerraSteelAIOT.isEnabled(stack)) {
            World world = player.world;
            BlockState state = world.getBlockState(pos);
            if (stack.getToolTypes().stream().anyMatch(state::isToolEffective)) {
                if (!world.isAirBlock(pos)) {
                    ToolUtil.removeBlocksInRange(new ToolBreakContext(player, pos, this.getTier()), side, 3);
                }
            }
        }
    }

    @Override
    public int getRepairManaPerTick(ItemStack stack) {
        return MANA_PER_DAMAGE / 2; // Alftools need half on the mana required normally on the pylon.
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

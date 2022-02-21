//package de.melanx.aiotbotania.items.alfsteel;
//
//import de.melanx.aiotbotania.AIOTBotania;
//import de.melanx.aiotbotania.compat.MythicBotany;
//import de.melanx.aiotbotania.items.ItemTiers;
//import de.melanx.aiotbotania.items.terrasteel.ItemTerraShovel;
//import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
//import de.melanx.aiotbotania.util.ToolBreakContext;
//import de.melanx.aiotbotania.util.ToolUtil;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.core.Direction;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.Component;
//import net.minecraft.ChatFormatting;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.fml.ModList;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.List;
//
//public class ItemAlfsteelShovel extends ItemTerraShovel implements MythicBotany, ModPylonRepairable {
//
//    public ItemAlfsteelShovel() {
//        super(ItemTiers.ALFSTEEL_AIOT_ITEM_TIER);
//    }
//
//    @Override
//    public void breakOtherBlock(Player player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
//        if (ItemTerraSteelAIOT.isEnabled(stack)) {
//            Level level = player.level;
//            BlockState state = world.getBlockState(pos);
//            if (stack.getToolTypes().stream().anyMatch(state::isToolEffective)) {
//                if (!level.isEmptyBlock(pos)) {
//                    ToolUtil.removeBlocksInRange(new ToolBreakContext(player, pos, this.getTier()), side, 3);
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean canRepairPylon(ItemStack stack) {
//        return stack.getDamageValue() > 0;
//    }
//
//    @Override
//    public int getRepairManaPerTick(ItemStack stack) {
//        return (int) (2.5 * this.MANA_PER_DAMAGE);
//    }
//
//    @Override
//    public ItemStack repairOneTick(ItemStack stack) {
//        stack.setDamageValue(Math.max(0, stack.getDamageValue() - 5));
//        return stack;
//    }
//
//    @Override
//    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
//        if (!ModList.get().isLoaded("mythicbotany")) {
//            tooltip.add(new TranslatableComponent(AIOTBotania.MODID + ".mythicbotany.disabled").withStyle(ChatFormatting.DARK_RED));
//        } else {
//            super.appendHoverText(stack, level, tooltip, flag);
//        }
//    }
//}

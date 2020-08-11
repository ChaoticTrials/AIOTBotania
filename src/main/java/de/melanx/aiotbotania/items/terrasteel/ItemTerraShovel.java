package de.melanx.aiotbotania.items.terrasteel;

import com.google.common.collect.ImmutableSet;
import de.melanx.aiotbotania.items.base.ItemShovelBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.Set;

public class ItemTerraShovel extends ItemShovelBase implements ISequentialBreaker {

    public static final Set<Material> MATERIALS = ImmutableSet.of(Material.CLAY, Material.GOURD, Material.EARTH, Material.SAND);
    public static final Set<Block> BLOCKS = ImmutableSet.of(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);


    public ItemTerraShovel() {
        super(BotaniaAPI.instance().getTerrasteelItemTier(), (int) (ItemTerraSteelAIOT.DAMAGE / 2f),15, ItemTerraSteelAIOT.MANA_PER_DAMAGE);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemTerraSteelAIOT.setEnabled(stack, !ItemTerraSteelAIOT.isEnabled(stack));
        if (!world.isRemote) {
            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.terraPickMode, SoundCategory.PLAYERS, 0.5F, 0.4F);
        }
        return ActionResult.resultSuccess(stack);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        BlockRayTraceResult raycast = ToolCommons.raytraceFromEntity(player, 10.0D, false);
        if (!player.world.isRemote && raycast.getType() == RayTraceResult.Type.BLOCK) {
            Direction face = raycast.getFace();
            this.breakOtherBlock(player, stack, pos, pos, face);
            BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
        }
        return false;
    }

    @Override
    public void breakOtherBlock(PlayerEntity player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (ItemTerraSteelAIOT.isEnabled(stack)) {
            World world = player.world;
            BlockState state = world.getBlockState(pos);
            if (MATERIALS.contains(state.getMaterial()) || BLOCKS.contains(state.getBlock())) {
                if (!world.isAirBlock(pos)) {
                    boolean doX = side.getXOffset() == 0;
                    boolean doY = side.getYOffset() == 0;
                    boolean doZ = side.getZOffset() == 0;
                    int range = 1;
                    int rangeY = 1;
                    Vector3i beginDiff = new Vector3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
                    Vector3i endDiff = new Vector3i(doX ? range : 0, doY ? rangeY * 2 - 1 : 0, doZ ? range : 0);
                    ToolCommons.removeBlocksInIteration(player, stack, world, pos, beginDiff, endDiff, (blockstate) -> MATERIALS.contains(blockstate.getMaterial()) || BLOCKS.contains(blockstate.getBlock()), false);
                }
            }
        }
    }

    @Override
    public boolean disposeOfTrashBlocks(ItemStack itemStack) {
        return false;
    }
}

package de.melanx.aiotbotania.items.elementium;

import de.melanx.aiotbotania.items.base.ItemAIOTBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import static vazkii.botania.common.item.equipment.tool.ToolCommons.materialsShovel;

public class ItemElementiumAIOT extends ItemAIOTBase implements IPixieSpawner {

    private static final int MANA_PER_DAMAGE = 60;

    public ItemElementiumAIOT() {
        super("elementiumAIOT", BotaniaAPI.elementiumToolMaterial, 6.0f, -2.2f, MANA_PER_DAMAGE, true);
    }

    // The following code is by Vazkii

    @Override
    public float getPixieChance(ItemStack stack) {
        return 0.1F;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        World world = player.world;
        Material mat = world.getBlockState(pos).getMaterial();
        if (!materialsShovel.contains(mat))
            return false;

        RayTraceResult block = ToolCommons.raytraceFromEntity(world, player, true, 10);
        if (block == null)
            return false;

        int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;

        Block blk = world.getBlockState(pos).getBlock();
        if(blk instanceof BlockFalling)
            ToolCommons.removeBlocksInIteration(player, stack, world, pos, new Vec3i(0, -12, 0), new Vec3i(1, 12, 1), blk, materialsShovel, silk, fortune, false);

        return false;
    }
}

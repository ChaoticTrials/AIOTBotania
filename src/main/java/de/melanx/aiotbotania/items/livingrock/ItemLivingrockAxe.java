package de.melanx.aiotbotania.items.livingrock;

import de.melanx.aiotbotania.items.ToolMaterials;
import de.melanx.aiotbotania.items.base.ItemAxeBase;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemLivingrockAxe extends ItemAxeBase {

    private static final int MANA_PER_DAMAGE = 40;
    private static final float ATTACK_DAMAGE = 8.5F;
    private static final float ATTACK_SPEED = -3.1F;

    public ItemLivingrockAxe() {
        super("livingrockAxe", ToolMaterials.livingrockMaterial, MANA_PER_DAMAGE, ATTACK_DAMAGE, ATTACK_SPEED);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if(side == EnumFacing.UP) {
            return ToolUtil.axeUse(player, world, pos, hand, side, hitX, hitY, hitZ);
        }
        return EnumActionResult.PASS;
    }

}

package de.melanx.aiotbotania.items.manasteel;

import de.melanx.aiotbotania.items.base.ItemHoeBase;
import de.melanx.aiotbotania.lib.LibMisc;
import de.melanx.aiotbotania.util.Registry;
import de.melanx.aiotbotania.util.ToolUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;

public class ItemManasteelHoe extends ItemHoeBase implements IManaUsingItem {

    private static final int MANA_PER_DAMAGE = 60;
    private static final int SPEED = -1;

    public ItemManasteelHoe() {
        super("manasteel_hoe", BotaniaAPI.MANASTEEL_ITEM_TIER, SPEED, MANA_PER_DAMAGE, false, false);
    }

}

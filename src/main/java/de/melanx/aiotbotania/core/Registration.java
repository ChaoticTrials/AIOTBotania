package de.melanx.aiotbotania.core;

import com.mojang.serialization.Codec;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.blocks.BlockCustomFarmland;
import de.melanx.aiotbotania.core.crafting.MythicBotanyCondition;
import de.melanx.aiotbotania.core.crafting.TerrasteelCondition;
import de.melanx.aiotbotania.handler.lootmodifier.DisposeModifier;
import de.melanx.aiotbotania.handler.lootmodifier.GrassModifier;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.alfsteel.ItemAlfsteelAIOT;
import de.melanx.aiotbotania.items.alfsteel.ItemAlfsteelHoe;
import de.melanx.aiotbotania.items.alfsteel.ItemAlfsteelShovel;
import de.melanx.aiotbotania.items.base.*;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAxe;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockPickaxe;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockShovel;
import de.melanx.aiotbotania.items.livingwood.ItemLivingwoodAIOT;
import de.melanx.aiotbotania.items.manasteel.ItemManasteelAIOT;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraHoe;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraShovel;
import de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AIOTBotania.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AIOTBotania.MODID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AIOTBotania.MODID);

    public static final RegistryObject<Item> livingwood_shears = ITEMS.register("livingwood_shears", () -> new ItemShearsBase(10, 60));
    public static final RegistryObject<Item> livingwood_sword = ITEMS.register("livingwood_sword", () -> new ItemSwordBase(ItemTiers.LIVINGWOOD_ITEM_TIER, 3, -2.5F, 30));
    public static final RegistryObject<Item> livingwood_axe = ITEMS.register("livingwood_axe", () -> new ItemAxeBase(ItemTiers.LIVINGWOOD_ITEM_TIER, 30, 6.0F, -3.2F));
    public static final RegistryObject<Item> livingwood_pickaxe = ITEMS.register("livingwood_pickaxe", () -> new ItemPickaxeBase(ItemTiers.LIVINGWOOD_ITEM_TIER, 1, -2.8F, 30));
    public static final RegistryObject<Item> livingwood_shovel = ITEMS.register("livingwood_shovel", () -> new ItemShovelBase(ItemTiers.LIVINGWOOD_ITEM_TIER, 1, -3.0F, 30));
    public static final RegistryObject<Item> livingwood_hoe = ITEMS.register("livingwood_hoe", () -> new ItemHoeBase(ItemTiers.LIVINGWOOD_ITEM_TIER, -3, 30, false, true));
    public static final RegistryObject<Item> livingwood_aiot = ITEMS.register("livingwood_aiot", ItemLivingwoodAIOT::new);

    public static final RegistryObject<Item> livingrock_shears = ITEMS.register("livingrock_shears", () -> new ItemShearsBase(10, 119));
    public static final RegistryObject<Item> livingrock_sword = ITEMS.register("livingrock_sword", () -> new ItemSwordBase(ItemTiers.LIVINGROCK_ITEM_TIER, 2, -2.4F, 40));
    public static final RegistryObject<Item> livingrock_axe = ITEMS.register("livingrock_axe", ItemLivingrockAxe::new);
    public static final RegistryObject<Item> livingrock_pickaxe = ITEMS.register("livingrock_pickaxe", ItemLivingrockPickaxe::new);
    public static final RegistryObject<Item> livingrock_shovel = ITEMS.register("livingrock_shovel", ItemLivingrockShovel::new);
    public static final RegistryObject<Item> livingrock_hoe = ITEMS.register("livingrock_hoe", () -> new ItemHoeBase(ItemTiers.LIVINGROCK_ITEM_TIER, -2, 40, false, true));
    public static final RegistryObject<Item> livingrock_aiot = ITEMS.register("livingrock_aiot", ItemLivingrockAIOT::new);

    public static final RegistryObject<Item> manasteel_aiot = ITEMS.register("manasteel_aiot", () -> new ItemManasteelAIOT(ItemTiers.MANASTEEL_AIOT_ITEM_TIER, 6.0F, -2.2F, 66, false));

    public static final RegistryObject<Item> elementium_aiot = ITEMS.register("elementium_aiot", ItemElementiumAIOT::new);

    public static final RegistryObject<Item> terrasteel_shovel = ITEMS.register("terra_shovel", ItemTerraShovel::new);
    public static final RegistryObject<Item> terrasteel_hoe = ITEMS.register("terra_hoe", ItemTerraHoe::new);
    public static final RegistryObject<Item> terrasteel_aiot = ITEMS.register("terra_aiot", ItemTerraSteelAIOT::new);

    public static final RegistryObject<Item> alfsteel_shovel = ITEMS.register("alfsteel_shovel", ItemAlfsteelShovel::new);
    public static final RegistryObject<Item> alfsteel_hoe = ITEMS.register("alfsteel_hoe", ItemAlfsteelHoe::new);
    public static final RegistryObject<Item> alfsteel_aiot = ITEMS.register("alfsteel_aiot", ItemAlfsteelAIOT::new);

    public static final RegistryObject<Block> custom_farmland = BLOCKS.register("super_farmland", BlockCustomFarmland::new);

    public static final RegistryObject<Codec<DisposeModifier>> dispose_modifier = LOOT_MODIFIER.register("dispose", DisposeModifier.CODEC);
    public static final RegistryObject<Codec<GrassModifier>> grass_modifier = LOOT_MODIFIER.register("grass", GrassModifier.CODEC);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        AIOTBotania.instance.getLogger().info("Items registered.");
        BLOCKS.register(bus);
        AIOTBotania.instance.getLogger().info("Blocks registered.");
        LOOT_MODIFIER.register(bus);
        AIOTBotania.instance.getLogger().info("Global loot modifiers registered.");

        CraftingHelper.register(MythicBotanyCondition.SERIALIZER);
        CraftingHelper.register(TerrasteelCondition.SERIALIZER);
    }

    public static void registerDispenseBehavior() {
        DispenserBlock.registerBehavior(livingwood_shears.get(), new ShearsDispenseItemBehavior());
        DispenserBlock.registerBehavior(livingrock_shears.get(), new ShearsDispenseItemBehavior());
    }
}

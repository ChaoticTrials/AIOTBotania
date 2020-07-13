package de.melanx.aiotbotania.core;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.blocks.BlockCustomFarmland;
import de.melanx.aiotbotania.core.handler.lootmodifier.DisposeModifier;
import de.melanx.aiotbotania.core.handler.lootmodifier.GrassModifier;
import de.melanx.aiotbotania.items.ItemTiers;
import de.melanx.aiotbotania.items.base.*;
import de.melanx.aiotbotania.items.elementium.ItemElementiumAIOT;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAIOT;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockAxe;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockPickaxe;
import de.melanx.aiotbotania.items.livingrock.ItemLivingrockShovel;
import de.melanx.aiotbotania.items.livingwood.ItemLivingwoodAIOT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.api.BotaniaAPI;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AIOTBotania.MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, AIOTBotania.MODID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER = new DeferredRegister<>(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, AIOTBotania.MODID);

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

    public static final RegistryObject<Item> manasteel_hoe = ITEMS.register("manasteel_hoe", () -> new ItemHoeBase(BotaniaAPI.instance().getManasteelItemTier(), -1, 60, false, false));
    public static final RegistryObject<Item> manasteel_aiot = ITEMS.register("manasteel_aiot", () -> new ItemAIOTBase(ItemTiers.MANASTEEL_AIOT_ITEM_TIER, 6.0F, -2.2F, 66, false));

    public static final RegistryObject<Item> elementium_hoe = ITEMS.register("elementium_hoe", () -> new ItemHoeBase(BotaniaAPI.instance().getElementiumItemTier(), -1, 60, true, false));
    public static final RegistryObject<Item> elementium_aiot = ITEMS.register("elementium_aiot", ItemElementiumAIOT::new);

    public static final RegistryObject<Block> custom_farmland = BLOCKS.register("super_farmland", BlockCustomFarmland::new);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> dispose = LOOT_MODIFIER.register("dispose", DisposeModifier.Serializer::new);
    public static final RegistryObject<GlobalLootModifierSerializer<?>> sapling_modifier = LOOT_MODIFIER.register("grass", GrassModifier.Serializer::new);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        AIOTBotania.instance.getLogger().info("Items registered.");
        BLOCKS.register(bus);
        AIOTBotania.instance.getLogger().info("Blocks registered.");
        LOOT_MODIFIER.register(bus);
        AIOTBotania.instance.getLogger().info("Global loot modifiers registered.");
    }

}

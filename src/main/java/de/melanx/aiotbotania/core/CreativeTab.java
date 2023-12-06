package de.melanx.aiotbotania.core;

import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.compat.CompatItem;
import de.melanx.aiotbotania.compat.MythicBotany;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import static de.melanx.aiotbotania.items.terrasteel.ItemTerraSteelAIOT.*;

public class CreativeTab {

    public static void createModeTab(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(AIOTBotania.MODID, CreativeModeTab.builder()
                    .title(Component.literal("AIOT Botania"))
                    .icon(() -> new ItemStack(Registration.elementium_aiot.get()))
                    .displayItems((flags, output) -> {
                        for (Item item : ForgeRegistries.ITEMS.getValues()) {
                            //noinspection DataFlowIssue
                            if (AIOTBotania.MODID.equals(ForgeRegistries.ITEMS.getKey(item).getNamespace())) {
                                if (!(item instanceof CompatItem) || (ModList.get().isLoaded("mythicbotany") && item instanceof MythicBotany)) {
                                    if (item == Registration.terrasteel_aiot.get() || item == Registration.alfsteel_aiot.get()) {
                                        for (int mana : CREATIVE_MANA) {
                                            ItemStack stack = new ItemStack(item);
                                            setMana(stack, mana);
                                            output.accept(stack);
                                        }

                                        ItemStack stack = new ItemStack(item);
                                        setMana(stack, CREATIVE_MANA[1]);
                                        setTipped(stack);
                                        output.accept(stack);
                                    } else {
                                        output.accept(new ItemStack(item));
                                    }
                                }
                            }
                        }
                    })
                    .build());
        });
    }
}

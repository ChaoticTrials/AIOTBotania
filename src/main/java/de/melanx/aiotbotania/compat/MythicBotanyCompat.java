package de.melanx.aiotbotania.compat;

import de.melanx.aiotbotania.core.Registration;
import de.melanx.aiotbotania.util.ToolSet;
import mythicbotany.register.ModItems;
import net.minecraftforge.fml.ModList;

public class MythicBotanyCompat {

    public static boolean isLoaded() {
        return ModList.get().isLoaded("mythicbotany");
    }

    public static ToolSet alfsteelToolset() {
        return new ToolSet(
                ModItems.alfsteelSword,
                ModItems.alfsteelAxe,
                ModItems.alfsteelPick,
                Registration.alfsteel_shovel.get(),
                Registration.alfsteel_hoe.get()
        );
    }
}

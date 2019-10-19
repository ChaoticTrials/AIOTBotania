package de.melanx.aiotbotania.util;

import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.util.ResourceLocation;

public class Util {
    public static ResourceLocation resourceOf(String s) {
        return new ResourceLocation(AIOTBotania.MODID, s);
    }
}

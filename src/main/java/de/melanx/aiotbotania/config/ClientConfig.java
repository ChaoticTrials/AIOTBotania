package de.melanx.aiotbotania.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec CLIENT_CONFIG;
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    static {
        init(CLIENT_BUILDER);
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static ForgeConfigSpec.BooleanValue particles;

    private static void init(ForgeConfigSpec.Builder builder) {
        particles = builder.comment("If set to false, particles will be disabled. [default: true]")
                .define("particles.enabled", true);
    }
}

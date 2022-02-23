package de.melanx.aiotbotania.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static final ForgeConfigSpec COMMON_CONFIG;
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    static {
        init(COMMON_BUILDER);
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static ForgeConfigSpec.BooleanValue terraAiot;

    private static void init(ForgeConfigSpec.Builder builder) {
        terraAiot = builder.comment("If set to false, the recipes for the Terrasteel AIOT will be disabled. [default: true]")
                .define("terra_tier.enabled", true);
    }
}

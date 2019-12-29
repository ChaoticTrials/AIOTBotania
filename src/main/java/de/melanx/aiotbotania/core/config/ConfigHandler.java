package de.melanx.aiotbotania.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHandler {

    public static class Common {
        public final BooleanValue LIVINGWOOD_TOOLS;
        public final BooleanValue LIVINGROCK_TOOLS;
//        public final BooleanValue PARTICLES;

        public Common(ForgeConfigSpec.Builder builder) {
            LIVINGWOOD_TOOLS = builder
                    .comment("If set to false, Livingwood tools will be disabled.")
                    .define("livingwood_tools.enabled", true);

            LIVINGROCK_TOOLS = builder
                    .comment("If set to false, Livingrock tools will be disabled. [default: true]")
                    .define("livingrock_tools.enabled", true);

//            PARTICLES = builder
//                    .comment("If set to false, particles will be disabled. [default: true]")
//                    .define("particles.enabled", true);
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}

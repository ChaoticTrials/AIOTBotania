package de.melanx.aiotbotania.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHandler {

    public static class Client {
        public final BooleanValue PARTICLES;

        public Client(ForgeConfigSpec.Builder builder) {
            this.PARTICLES = builder.comment("If set to false, particles will be disabled. [default: true]")
                    .define("particles.enabled", true);
        }
    }

    public static class Common {
        public final BooleanValue TERRA_AIOT;

        public Common(ForgeConfigSpec.Builder builder) {
            this.TERRA_AIOT = builder.comment("If set to false, the recipes for the Terrasteel AIOT will be disabled. [default: true]")
                    .define("terra_tier.enabled", true);
        }
    }

    public static final Client CLIENT;
    public static final Common COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPairClient = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();

        final Pair<Common, ForgeConfigSpec> specPairCommon = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPairCommon.getRight();
        COMMON = specPairCommon.getLeft();
    }
}

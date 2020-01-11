package de.melanx.aiotbotania.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHandler {

    public static class Client {
        public final BooleanValue PARTICLES;

        public Client(ForgeConfigSpec.Builder builder) {
            PARTICLES = builder.comment("If set to false, particles will be disabled. [default: true]")
                    .define("particles.enabled", true);
        }
    }

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
}

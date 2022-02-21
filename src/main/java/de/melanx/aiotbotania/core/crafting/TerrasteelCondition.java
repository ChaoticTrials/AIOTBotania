package de.melanx.aiotbotania.core.crafting;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.core.config.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class TerrasteelCondition implements ICondition {
    public static final ResourceLocation KEY = new ResourceLocation(AIOTBotania.MODID, "terrasteel_enabled");
    private final boolean value;

    public TerrasteelCondition(boolean value) {
        this.value = value;
    }

    @Override
    public ResourceLocation getID() {
        return KEY;
    }

    @Override
    public boolean test() {
        return ConfigHandler.COMMON.TERRA_AIOT.get();
    }

    public static final IConditionSerializer<TerrasteelCondition> SERIALIZER = new IConditionSerializer<TerrasteelCondition>() {
        @Override
        public void write(JsonObject json, TerrasteelCondition value) {
            json.addProperty("value", value.value);
        }

        @Override
        public TerrasteelCondition read(JsonObject json) {
            return new TerrasteelCondition(json.get("value").getAsBoolean());
        }

        @Override
        public ResourceLocation getID() {
            return KEY;
        }
    };
}

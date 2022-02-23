package de.melanx.aiotbotania.core.crafting;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.AIOTBotania;
import de.melanx.aiotbotania.config.CommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class TerrasteelCondition implements ICondition {

    public static final ResourceLocation KEY = new ResourceLocation(AIOTBotania.MODID, "terrasteel_enabled");

    @Override
    public ResourceLocation getID() {
        return KEY;
    }

    @Override
    public boolean test() {
        return CommonConfig.terraAiot.get();
    }

    public static final IConditionSerializer<TerrasteelCondition> SERIALIZER = new IConditionSerializer<>() {

        @Override
        public void write(JsonObject json, TerrasteelCondition value) {

        }

        @Override
        public TerrasteelCondition read(JsonObject json) {
            return new TerrasteelCondition();
        }

        @Override
        public ResourceLocation getID() {
            return KEY;
        }
    };
}

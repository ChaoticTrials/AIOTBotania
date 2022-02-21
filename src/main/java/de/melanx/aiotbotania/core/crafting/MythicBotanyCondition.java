package de.melanx.aiotbotania.core.crafting;

import com.google.gson.JsonObject;
import de.melanx.aiotbotania.AIOTBotania;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

public class MythicBotanyCondition implements ICondition {
    public static final ResourceLocation KEY = new ResourceLocation(AIOTBotania.MODID, "mythicbotany");
    private final boolean value;

    public MythicBotanyCondition(boolean value) {
        this.value = value;
    }

    @Override
    public ResourceLocation getID() {
        return KEY;
    }

    @Override
    public boolean test() {
        return ModList.get().isLoaded("mythicbotany");
    }

    public static final IConditionSerializer<MythicBotanyCondition> SERIALIZER = new IConditionSerializer<MythicBotanyCondition>() {
        @Override
        public void write(JsonObject json, MythicBotanyCondition value) {
            json.addProperty("value", value.value);
        }

        @Override
        public MythicBotanyCondition read(JsonObject json) {
            return new MythicBotanyCondition(json.get("value").getAsBoolean());
        }

        @Override
        public ResourceLocation getID() {
            return KEY;
        }
    };
}

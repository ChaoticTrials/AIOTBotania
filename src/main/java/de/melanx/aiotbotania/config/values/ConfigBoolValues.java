package de.melanx.aiotbotania.config.values;

import de.melanx.aiotbotania.config.ConfigCategories;

public enum ConfigBoolValues {

    GENERAL("Set to true", ConfigCategories.GENERAL, true, "If set to true, this is true. Else this is wrong. [default: true]");

    public final String name;
    public final String category;
    public final boolean defaultValue;
    public final String desc;

    public boolean currentValue;

    ConfigBoolValues(String name, ConfigCategories category, boolean defaultValue, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public boolean isEnabled(){
        return this.currentValue;
    }

}

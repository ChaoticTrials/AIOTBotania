package de.melanx.aiotbotania.config.values;

import de.melanx.aiotbotania.config.ConfigCategories;

public enum ConfigBoolValues {

    MANASTEEL_AIOT("Manasteel AIOT", ConfigCategories.TOOLS, true, "If set to false, Manasteel AIOT will be disabled. [default: true]"),
    ELEMENTIUM_AIOT("Elementium AIOT", ConfigCategories.TOOLS, true, "If set to false, Elementium AIOT will be disabled. [default: true]");

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

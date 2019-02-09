package de.melanx.aiotbotania.config.values;

import de.melanx.aiotbotania.config.ConfigCategories;

public enum ConfigBoolValues {

    LIVINGWOOD_TOOLS("Livingwood Tools", ConfigCategories.TOOLS, false, "If set to true, Livingwood tools will be enabled. Livingwood AIOT can only be enabled if the tools are enabled. [default: false]"),
    LIVINGROCK_TOOLS("Livingrock Tools", ConfigCategories.TOOLS, false, "If set to true, Livingrock tools will be enabled. Livingrock AIOT can only be enabled if the tools are enabled. [default: false]"),
    LIVINGWOOD_AIOT("Livingwood AIOT", ConfigCategories.TOOLS, true, "If set to false, Livingwood AIOT will be disabled. [default: true]"),
    LIVINGROCK_AIOT("Livingrock AIOT", ConfigCategories.TOOLS, true, "If set to false, Livingrock AIOT will be disabled. [default: true]"),
    MANASTEEL_AIOT("Manasteel AIOT", ConfigCategories.TOOLS, true, "If set to false, Manasteel AIOT will be disabled. [default: true]"),
    ELEMENTIUM_AIOT("Elementium AIOT", ConfigCategories.TOOLS, true, "If set to false, Elementium AIOT will be disabled. [default: true]"),
    PARTICLES("Particles", ConfigCategories.CLIENT, true, "If set to false, particles will be disabled. [default: true]");

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

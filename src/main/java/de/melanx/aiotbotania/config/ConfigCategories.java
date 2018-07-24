package de.melanx.aiotbotania.config;

import java.util.Locale;

public enum ConfigCategories {

    GENERAL("General", "Just the general configs");

    public final String name;
    public final String comment;

    ConfigCategories(String name, String comment) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.comment = comment;
    }

}

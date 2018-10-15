package de.melanx.aiotbotania.config;

import java.util.Locale;

public enum ConfigCategories {

    TOOLS("Tools", "Things about tools"),
    CLIENT("Client", "Things about the client");

    public final String name;
    public final String comment;

    ConfigCategories(String name, String comment) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.comment = comment;
    }

}

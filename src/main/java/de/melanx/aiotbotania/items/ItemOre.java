package de.melanx.aiotbotania.items;

import net.minecraftforge.oredict.OreDictionary;

public class ItemOre extends ItemBase {

    private String oreName;

    public ItemOre(String name, String oreName) {

        super(name);

        this.oreName = oreName;

    }

    public void initOreDict() {
        OreDictionary.registerOre(oreName, this);
    }

}


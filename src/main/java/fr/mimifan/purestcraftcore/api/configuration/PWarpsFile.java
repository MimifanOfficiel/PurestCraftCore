package fr.mimifan.purestcraftcore.api.configuration;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import org.bukkit.plugin.java.JavaPlugin;

public class PWarpsFile extends YamlConfigurationFile {

    private static PWarpsFile instance = new PWarpsFile(PurestCraftCore.getInstance());

    public PWarpsFile(JavaPlugin plugin) {
        super("pwarps", plugin);
    }

    public static PWarpsFile getInstance() {
        return instance;
    }

}

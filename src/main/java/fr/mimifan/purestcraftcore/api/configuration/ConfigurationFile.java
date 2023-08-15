package fr.mimifan.purestcraftcore.api.configuration;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationFile extends YamlConfigurationFile {
    private static ConfigurationFile instance = new ConfigurationFile(PurestCraftCore.getInstance());
    public ConfigurationFile(JavaPlugin plugin) {
        super("config", plugin);
    }

    public static ConfigurationFile getInstance() {
        return instance;
    }
}

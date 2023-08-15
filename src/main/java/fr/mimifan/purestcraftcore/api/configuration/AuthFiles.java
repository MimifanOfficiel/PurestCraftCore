package fr.mimifan.purestcraftcore.api.configuration;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthFiles extends YamlConfigurationFile {

    private static AuthFiles instance = new AuthFiles(PurestCraftCore.getInstance());

    public AuthFiles(JavaPlugin plugin) {
        super("auths", plugin);
    }

    public static AuthFiles getInstance() {
        return instance;
    }
}

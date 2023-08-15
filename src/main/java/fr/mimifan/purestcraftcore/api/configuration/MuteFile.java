package fr.mimifan.purestcraftcore.api.configuration;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import org.bukkit.plugin.java.JavaPlugin;

public class MuteFile extends YamlConfigurationFile {

    private static MuteFile instance = new MuteFile(PurestCraftCore.getInstance());
    public MuteFile(JavaPlugin plugin) {
        super("mutes", plugin);
    }

    public static MuteFile getInstance() {
        return instance;
    }

}

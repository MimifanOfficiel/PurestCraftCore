package fr.mimifan.purestcraftcore.api.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class YamlConfigurationFile {

    private final String fileName;

    private final JavaPlugin plugin;

    private YamlConfiguration configuration;

    private File file;

    public YamlConfigurationFile(String fileName, JavaPlugin plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.configuration = null;
        this.file = null;

        this.saveDefault();
    }

    public void load() {
        this.file = new File(this.plugin.getDataFolder(), this.fileName + ".yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);

        InputStream defaultStream = this.plugin.getResource(this.fileName + ".yml");

        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.configuration.setDefaults(defaultConfig);
        }
    }

    public YamlConfiguration getConfiguration() {
        if (this.configuration == null) {
            this.load();
        }

        return this.configuration;
    }

    public void save() {
        if (this.configuration == null || this.file == null) {
            return;
        }

        try {
            this.getConfiguration().save(this.file);
        } catch (Exception e) {
            this.plugin.getLogger().severe("Could not save configuration file " + this.file.getName() + "!");
        }
    }

    public void saveDefault() {
        if (this.file == null) {
            this.file = new File(this.plugin.getDataFolder(), this.fileName + ".yml");
        }

        if (!this.file.exists()) {
            this.plugin.saveResource(this.fileName + ".yml", false);
        }
    }

    public String getMessage(String path) {
        String message = this.getConfiguration().getString(path);

        if (message == null) {
            return path;
        }

        return message.replace("&", "§");
    }

    public boolean getBoolean(String path){ return this.getConfiguration().getBoolean(path); }

    public List<String> getMessageList(String path) {
        List<String> messages = this.getConfiguration().getStringList(path);

        messages.replaceAll(s -> s.replace("&", "§"));
        return messages;
    }

    public List<String> getMessageList(String path, String... args) {
        List<String> messages = this.getConfiguration().getStringList(path);

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < messages.size(); j++) {
                messages.set(j, messages.get(j).replace("{" + i + "}", args[i]));
            }
        }

        messages.replaceAll(s -> s.replace("&", "§"));
        return messages;
    }

    public String getMessage(String path, String... args) {
        String message = this.getConfiguration().getString(path);

        if (message == null) {
            return path;
        }

        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i]);
        }

        return message.replace("&", "§");
    }

    public Location getLocation(String locName) {
        String world = getMessage(locName + ".World");
        double x = getConfiguration().getDouble(locName + ".X");
        double y = getConfiguration().getDouble(locName + ".Y");
        double z = getConfiguration().getDouble(locName + ".Z");
        float yaw = (float) getConfiguration().getDouble(locName + ".Yaw");
        float pitch = (float) getConfiguration().getDouble(locName + ".Pitch");
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void setLocation(String locName, Location location){
        getConfiguration().set(locName + ".World", Objects.requireNonNull(location.getWorld()).getName());
        getConfiguration().set(locName + ".X", location.getX());
        getConfiguration().set(locName + ".Y", location.getY());
        getConfiguration().set(locName + ".Z", location.getZ());
        getConfiguration().set(locName + ".Pitch", location.getPitch());
        getConfiguration().set(locName + ".Yaw", location.getYaw());
    }

}
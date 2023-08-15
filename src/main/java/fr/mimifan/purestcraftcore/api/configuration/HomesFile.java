package fr.mimifan.purestcraftcore.api.configuration;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class HomesFile extends YamlConfigurationFile {

    private static HomesFile instance = new HomesFile(PurestCraftCore.getInstance());

    public HomesFile(JavaPlugin plugin) {
        super("homes", plugin);
    }

    public static HomesFile getInstance() {
        return instance;
    }

    public Location getHomeLocation(String playername, String homeName){
        return getLocation("Homes." + playername + "." + homeName);
    }

    public void setHomeLocation(String playername, String homeName, Location homeLocation){
        setLocation("Homes." + playername + "." + homeName, homeLocation);
    }

    public void removeHome(String playerName, String homeName){
        getConfiguration().set("Homes." + playerName + "." + homeName, null);
    }

    public int getNumberOfHomes(String playername){
        int value = 0;
        try {
            value = Objects.requireNonNull(getConfiguration().getConfigurationSection("Homes." + playername)).getKeys(false).size();
        } catch (NullPointerException e) {
            return value;
        }
        return value;
    }

    public StringBuilder listHomes(String playername){
        StringBuilder sb = new StringBuilder();
        for(String name : Objects.requireNonNull(getConfiguration().getConfigurationSection("Homes." + playername)).getKeys(false)){
            String path = "Homes." + playername + "." + name;
            sb.append("§a").append(name).append(" §8§l: §aMonde §f: §6")
                    .append(getMessage(path + ".World"))
                    .append(" §6, §ax §f: §6").append((int) getConfiguration().getDouble(path + ".X"))
                    .append(" §6, §ay §f: §6").append((int) getConfiguration().getDouble(path + ".Y"))
                    .append(" §6, §az §f: §6").append((int) getConfiguration().getDouble(path + ".Z"))
                    .append("\n");
        }
        return sb;
    }

    public boolean homeExists(String playerName, String homeName){
        return getConfiguration().contains("Homes." + playerName + "." + homeName);
    }
}

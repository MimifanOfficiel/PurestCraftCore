package fr.mimifan.purestcraftcore.manager;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.event.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private static final PlayerManager instance = new PlayerManager();

    private List<PurestCraftPlayer> players = new ArrayList<>();

    public void load() {
        PurestCraftCore plugin = PurestCraftCore.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        Bukkit.getLogger().info("Initialisation des Events.");
        pluginManager.registerEvents(new PlayerEvents(), plugin);

        for(Player player : Bukkit.getOnlinePlayers()){
            PurestCraftPlayer purestCraftPlayer = new PurestCraftPlayer(player);
            players.add(purestCraftPlayer);
        }

    }

    public void unload() {
        players.clear();
    }

    public void register(PurestCraftPlayer player) {
        if (players.contains(player)) { throw new IllegalArgumentException(); }
        players.add(player);
    }

    public void unregister(PurestCraftPlayer player) { players.remove(player); }

    @Nullable
    public PurestCraftPlayer get(Player player) {
        return this.players.stream().filter(p -> p.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public static PlayerManager getInstance() {
        return instance;
    }

}

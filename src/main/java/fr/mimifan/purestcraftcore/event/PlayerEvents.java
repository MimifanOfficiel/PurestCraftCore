package fr.mimifan.purestcraftcore.event;

import com.nametagedit.plugin.NametagEdit;
import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.api.configuration.ConfigurationFile;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerManager.getInstance().register(new PurestCraftPlayer(event.getPlayer()));
        if(!event.getPlayer().hasPlayedBefore()){
            event.setJoinMessage("§eBienvenue §6" + event.getPlayer().getName() + " §esur PurestCraft !");
            event.getPlayer().teleport(ConfigurationFile.getInstance().getLocation("spawn"));

            //event.getPlayer().sendMessage("§cEnregistrez vous avec /register <motdepasse>");
        } else {
            event.setJoinMessage("§7§l[§a+§7§l] §7" + event.getPlayer().getName());
            //event.getPlayer().sendMessage("§cConnectez vous avec /login <motdepasse>");
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        PlayerManager.getInstance().unregister(PlayerManager.getInstance().get(event.getPlayer()));
        event.setQuitMessage("§7§l[§4-§7§l] §7" + event.getPlayer().getName());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(!Objects.requireNonNull(PlayerManager.getInstance().get(event.getPlayer())).isMuted()){

            event.setFormat(NametagEdit.getApi().getNametag(event.getPlayer()).getPrefix() +
                    event.getPlayer().getName() + " §7§l> §r" +
                    ChatColor.translateAlternateColorCodes('&', event.getMessage()));

            return;
        }
        event.setCancelled(true);
        event.getPlayer().sendMessage("§7Vous êtes actuellement muet, vous ne pouvez pas envoyer de message.");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        //if(!Objects.requireNonNull(PlayerManager.getInstance().get(event.getPlayer())).isLogged()) event.setCancelled(true);
    }


}

package fr.mimifan.purestcraftcore.event;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        PurestCraftPlayer player = PlayerManager.getInstance().get(event.getPlayer());
        if(player == null) return;
        if(!player.isStaffChatEnabled()) return;
        event.setCancelled(true);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("purestcraft.staffchat")){
                onlinePlayer.sendMessage("§5StaffChat §8» §7" + player.getPlayer().getName() + " §f: §r" + event.getMessage().replace("&", "§"));
            }
        }
    }


}

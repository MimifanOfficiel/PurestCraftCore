package fr.mimifan.purestcraftcore.commands;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandStaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(PurestCraftCore.getPlugin(PurestCraftCore.class), () -> {
            String userName = "§9Console";
            if(commandSender instanceof Player p){
                userName = p.getName();
                if(args.length < 1){
                    PurestCraftPlayer purestCraftPlayer = PlayerManager.getInstance().get(p);
                    if(purestCraftPlayer == null) return;

                    purestCraftPlayer.setStaffChatEnabled(!purestCraftPlayer.isStaffChatEnabled());
                    p.sendMessage(purestCraftPlayer.isStaffChatEnabled() ? "§aLe StaffChat est activé." : "§cLe StaffChat est désactivé.");
                    return;
                }
            }
            if(args.length == 0) return;
            for(Player player : Bukkit.getOnlinePlayers()){
                if(!player.hasPermission(Objects.requireNonNull(command.getPermission()))) return;
                StringBuilder sb = new StringBuilder();
                for (String arg : args) { sb.append(arg.replace("&", "§")).append(" "); }
                player.sendMessage("§5StaffChat §8» §7" + userName + " §f: §r" + sb);
            }
        });
        return true;
    }

}

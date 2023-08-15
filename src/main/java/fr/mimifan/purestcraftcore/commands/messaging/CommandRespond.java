package fr.mimifan.purestcraftcore.commands.messaging;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRespond implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return false;
        if(args.length < 1) return true;

        PurestCraftPlayer purestCraftPlayer = PlayerManager.getInstance().get(player);

        if(purestCraftPlayer == null) return false;
        if(purestCraftPlayer.getLastMSGPlayer() == null){
            player.sendMessage("§cVous n'avez personne à qui répondre.");
            return false;
        }

        PurestCraftPlayer target = purestCraftPlayer.getLastMSGPlayer();

        if(target == null || !target.getPlayer().isOnline()) {
            player.sendMessage("§cCe joueur n'est plus connecté.");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            message.append(args[i].replace('&', '§')).append(" ");
        }

        player.sendMessage("§e[§7Moi §5» §7" + target.getPlayer().getName() + "§e] §r" + message);
        target.getPlayer().sendMessage("§e[§7" + player.getName() + " §5» §7Moi§e] §r" + message);

        purestCraftPlayer.setLastMSGPlayer(target);
        target.setLastMSGPlayer(purestCraftPlayer);

        target.getPlayer().playSound(target.getPlayer().getLocation(), Sound.ENTITY_SILVERFISH_HURT, 3.0F, 2.0F);

        return true;
    }
}

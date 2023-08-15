package fr.mimifan.purestcraftcore.commands.messaging;

import fr.mimifan.purestcraftcore.api.PurestCraftPlayer;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandMSG implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)) return false;
        if(args.length < 2) return false;

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null || !target.isOnline()) {
            player.sendMessage("§cCe joueur n'est pas connecté ou n'existe pas.");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i].replace('&', '§')).append(" ");
        }

        player.sendMessage("§e[§7Moi §5» §7" + target.getName() + "§e] §r" + message);
        target.sendMessage("§e[§7" + player.getName() + " §5» §7Moi§e] §r" + message);

        PurestCraftPlayer player1 = PlayerManager.getInstance().get(player);
        PurestCraftPlayer target1 = PlayerManager.getInstance().get(target);

        if(player1 == null || target1 == null) return false;

        player1.setLastMSGPlayer(target1);
        target1.setLastMSGPlayer(player1);

        Objects.requireNonNull(target1.getPlayer()).playSound(target1.getPlayer().getLocation(), Sound.ENTITY_SILVERFISH_HURT, 3.0F, 2.0F);


        return true;
    }
}

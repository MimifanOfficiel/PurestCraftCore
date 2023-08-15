package fr.mimifan.purestcraftcore;

import com.sk89q.worldguard.WorldGuard;
import fr.mimifan.purestcraftcore.api.configuration.ConfigurationFile;
import fr.mimifan.purestcraftcore.api.configuration.HomesFile;
import fr.mimifan.purestcraftcore.api.configuration.MuteFile;
import fr.mimifan.purestcraftcore.api.configuration.PWarpsFile;
import fr.mimifan.purestcraftcore.commands.auth.CommandLogin;
import fr.mimifan.purestcraftcore.commands.auth.CommandRegister;
import fr.mimifan.purestcraftcore.commands.spawn.CommandSetSpawn;
import fr.mimifan.purestcraftcore.commands.spawn.CommandSpawn;
import fr.mimifan.purestcraftcore.commands.CommandStaffChat;
import fr.mimifan.purestcraftcore.commands.homes.*;
import fr.mimifan.purestcraftcore.commands.messaging.CommandMSG;
import fr.mimifan.purestcraftcore.commands.messaging.CommandRespond;
import fr.mimifan.purestcraftcore.commands.sanctions.CommandKick;
import fr.mimifan.purestcraftcore.commands.sanctions.mutes.CommandMute;
import fr.mimifan.purestcraftcore.commands.sanctions.mutes.CommandTempMute;
import fr.mimifan.purestcraftcore.commands.sanctions.mutes.CommandUnMute;
import fr.mimifan.purestcraftcore.event.ExplosionEvent;
import fr.mimifan.purestcraftcore.event.StaffChatEvent;
import fr.mimifan.purestcraftcore.inventory.PWarpsInventory;
import fr.mimifan.purestcraftcore.manager.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class PurestCraftCore extends JavaPlugin {

    private static PurestCraftCore instance;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getLogger().info("Enabling PurestCraftCore");
        getLogger().info("Loading PurestCraftCore's PlayerManager");
        PlayerManager.getInstance().load();
        ConfigurationFile.getInstance().load();
        MuteFile.getInstance().load();
        HomesFile.getInstance().load();
        PWarpsFile.getInstance().load();

        getLogger().info("Loading Vault API");
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Désactivation, Vault n'a pas pu charger !", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Vault API loaded successfully");

        getLogger().info("Loading WorldGuard API");
        WorldGuard.getInstance().setup();


        getLogger().info("Loading StaffChat");
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new CommandStaffChat());


        getLogger().info("Loading spawn commands");
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new CommandSetSpawn());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new CommandSpawn());


        getLogger().info("Loading sanctions commands");
        Objects.requireNonNull(getCommand("kick")).setExecutor(new CommandKick());
        Objects.requireNonNull(getCommand("mute")).setExecutor(new CommandMute());
        Objects.requireNonNull(getCommand("tempmute")).setExecutor(new CommandTempMute());
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new CommandUnMute());


        getLogger().info("Loading messaging commands");
        Objects.requireNonNull(getCommand("msg")).setExecutor(new CommandMSG());
        Objects.requireNonNull(getCommand("respond")).setExecutor(new CommandRespond());


        getLogger().info("Loading homes commands");
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new CommandSetHome());
        Objects.requireNonNull(getCommand("delhome")).setExecutor(new CommandDelHome());
        Objects.requireNonNull(getCommand("home")).setExecutor(new CommandHome());
        Objects.requireNonNull(getCommand("homes")).setExecutor(new CommandHomes());

        Objects.requireNonNull(getCommand("pwarp")).setExecutor(new CommandPWarp());


        /*getLogger().info("Loading login/register commands");
        Objects.requireNonNull(getCommand("login")).setExecutor(new CommandLogin());
        Objects.requireNonNull(getCommand("register")).setExecutor(new CommandRegister());*/


        getLogger().info("Loading listeners");
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new StaffChatEvent(), this);
        pluginManager.registerEvents(new ExplosionEvent(), this);
        pluginManager.registerEvents(new PWarpsInventory(), this);

        //getLogger().info("Loading chest shops");
        //pluginManager.registerEvents(new ChestShopEvents(), this);




        getLogger().info("PurestCraftCore has been enabled successfully");
    }

    public static PurestCraftCore getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        getLogger().info("PurestCraftCore is disabling");
        getLogger().info("Unloading PlayerManager");
        PlayerManager.getInstance().unload();
    }
}

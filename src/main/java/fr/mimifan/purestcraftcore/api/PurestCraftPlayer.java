package fr.mimifan.purestcraftcore.api;

import fr.mimifan.purestcraftcore.api.configuration.AuthFiles;
import fr.mimifan.purestcraftcore.api.configuration.MuteFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PurestCraftPlayer {

    protected Player player;

    protected PurestCraftPlayer lastMSGPlayer;

    protected boolean staffChatEnabled;
    protected boolean isLogged;

    protected Location lastLocation, teleportedLocation;

    public PurestCraftPlayer(Player player) {
        this.player = player;
        this.isLogged = false;
        lastLocation = player.getLocation();
        this.teleportedLocation = new Location(player.getPlayer().getWorld(), 0, 255, 0, 0 , 0);
    }


    public Player getPlayer() {
        return player;
    }

    public boolean isStaffChatEnabled() {
        return staffChatEnabled;
    }
    public void setStaffChatEnabled(boolean staffChatEnabled) {
        this.staffChatEnabled = staffChatEnabled;
    }

    public boolean isMuted() {
        return MuteFile.getInstance().getBoolean("Mutes." + player.getName());
    }

    public void setMuted(boolean muted, @Nullable String reason) {
        if(!muted) { MuteFile.getInstance().getConfiguration().set("Mutes." + player.getName(), null); }
        else {
            MuteFile.getInstance().getConfiguration().set("Mutes." + player.getName(), true);
            MuteFile.getInstance().getConfiguration().set("Mutes." + player.getName() + ".Reason", reason);
        }
        MuteFile.getInstance().save();
    }

    public void setMuted(boolean muted) {
        setMuted(muted, null);
    }

    public PurestCraftPlayer getLastMSGPlayer() {
        return lastMSGPlayer;
    }

    public void setLastMSGPlayer(PurestCraftPlayer lastMSGPlayer) {
        this.lastMSGPlayer = lastMSGPlayer;
    }

    public Location getLastLocation() { return lastLocation; }

    public boolean isLogged() { return isLogged; }

    public void setLogged(boolean logged) { isLogged = logged; }

    public boolean isRegistered() {
        return AuthFiles.getInstance().getConfiguration().contains("Auth." + player.getName());
    }

    public void register(String password, UUID uuid) {
        AuthFiles.getInstance().getConfiguration().set("Auth." + player.getName() + ".password", password);
        AuthFiles.getInstance().getConfiguration().set("Auth." + player.getName() + ".uuid", uuid);
        AuthFiles.getInstance().save();
    }

    public boolean login(String password) {
        if(! password.equals(AuthFiles.getInstance().getConfiguration().get("Auth." + player.getName() + ".password"))) return false;
        this.isLogged = true;
        return true;
    }

    public void login() {
        player.teleport(getLastLocation());
        player.setFlying(false);
        player.setAllowFlight(false);
        isLogged = true;
    }

    public Location getTeleportedLocation() {
        return this.teleportedLocation;
    }


}

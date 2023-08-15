package fr.mimifan.purestcraftcore.inventory;

import fr.mimifan.purestcraftcore.PurestCraftCore;
import fr.mimifan.purestcraftcore.api.configuration.PWarpsFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PWarpsInventory implements Listener {

    private Inventory inventory;
    private List<String> lore = new ArrayList<>();

    private int pages = PWarpsFile.getInstance().getMessageList("PWarps").size();

    public PWarpsInventory(){
        inventory = Bukkit.createInventory(null, 54, "§9§lPWarps");
        Bukkit.getScheduler().runTaskAsynchronously(PurestCraftCore.getPlugin(PurestCraftCore.class), () -> {
            Integer size = PWarpsFile.getInstance().getConfiguration().getConfigurationSection("PWarps").getKeys(false).size();
            if(size == 0) return;
            for (int i = 0; i < size; i++) {
                String playerWarp = (String) Objects.requireNonNull(PWarpsFile.getInstance().getConfiguration().getConfigurationSection("PWarps")).getKeys( false).toArray()[i];
                ItemStack pWarpItem = new ItemStack(Material.PLAYER_HEAD);

                SkullMeta skullMeta = (SkullMeta) pWarpItem.getItemMeta();
                assert skullMeta != null;
                skullMeta.setDisplayName("§f" + playerWarp);
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerWarp));

                Location loc = PWarpsFile.getInstance().getLocation("PWarps." + playerWarp);

                lore.add("§bCliquez pour vous téléporter.");
                lore.add("§aMonde: §e" + Objects.requireNonNull(loc.getWorld()).getName());
                lore.add("§aX: §e" + loc.getX());
                lore.add("§aY: §e" + loc.getY());
                lore.add("§aZ: §e" + loc.getZ());

                skullMeta.setLore(lore);
                pWarpItem.setItemMeta(skullMeta);
                inventory.setItem(i, pWarpItem);
                lore.clear();
            }
        });
    }

    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getView().getTitle().equals("§9§lPWarps")){
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack == null) return;
            if(!itemStack.getType().equals(Material.PLAYER_HEAD)) return;

            if(itemStack.hasItemMeta()){
                String playerName = itemStack.getItemMeta().getDisplayName();
                event.getWhoClicked().teleport(PWarpsFile.getInstance().getLocation("PWarps." + playerName.replace("§f", "")));
                event.getWhoClicked().sendMessage("§aVous avez été téléporté au warp de §e" + playerName.replace("§f", ""));
            }
        }
    }
}

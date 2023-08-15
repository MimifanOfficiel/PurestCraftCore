package fr.mimifan.purestcraftcore.event;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import fr.mimifan.purestcraftcore.PurestCraftCore;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionEvent implements Listener {

    @EventHandler
    public void onExplosion(EntityExplodeEvent event){
         for (Block block : event.blockList()) {
            Location location = BukkitAdapter.adapt(block.getLocation());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(location);
            for (ProtectedRegion protectedRegion : set) {
                if(protectedRegion.getId().equals("spawn")){
                    event.setCancelled(true);
                }
            }
        }
    }


}

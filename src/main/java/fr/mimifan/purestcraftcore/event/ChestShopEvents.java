package fr.mimifan.purestcraftcore.event;

import fr.mimifan.purestcraftcore.api.configuration.ConfigurationFile;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class ChestShopEvents implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if(!isSignWithChest(event.getBlock())) return;

        if(!Objects.requireNonNull(event.getLine(0)).equalsIgnoreCase("Shop")) return;

        Material material = Material.matchMaterial(Objects.requireNonNull(event.getLine(3)));
        if(material == null) return;

        event.setLine(0, "Magasin privé");

        event.setLine(3, material.name());

        event.getPlayer().sendMessage("§aUn shop a été créé.");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction() != Action.LEFT_CLICK_BLOCK || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!isSignWithChest(Objects.requireNonNull(event.getClickedBlock()))) return;

        Sign sign = (Sign) event.getClickedBlock();

        BlockData data = event.getClickedBlock().getBlockData();
        if(!(data instanceof Directional directional)) return;

        Chest chest = (Chest) event.getClickedBlock().getRelative(directional.getFacing().getOppositeFace());

        if(!sign.getLine(0).equals("Magasin privé")) return;

        ItemStack shopItem = new ItemStack(Material.matchMaterial(sign.getLine(3)));


    }


    private boolean isSignWithChest(Block block){
        List<String> signs = ConfigurationFile.getInstance().getConfiguration().getStringList("chest-signs");

        if(!signs.contains(block.getType().name())) return false;

        if (!(block.getState() instanceof Sign)) return false;

        BlockData data = block.getBlockData();
        if (!(data instanceof Directional directional)) return false;

        Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());
        return blockBehind.getType() == Material.CHEST;
    }

}

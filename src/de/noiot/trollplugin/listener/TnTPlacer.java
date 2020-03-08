package de.noiot.trollplugin.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.noiot.trollplugin.data.Data;

public class TnTPlacer implements Listener{
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if(Data.tntplacer.contains(p)) {
			e.getBlock().setType(Material.AIR);
			p.getInventory().getItemInHand().setType(Material.TNT);
			e.getBlock().getWorld().spawn(e.getBlock().getLocation(),TNTPrimed.class);
		}
	}

}

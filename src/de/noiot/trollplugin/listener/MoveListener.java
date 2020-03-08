package de.noiot.trollplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.noiot.trollplugin.data.Data;

public class MoveListener implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(Data.freeze.contains(p)) {
			e.setCancelled(true);
		}
	}

}

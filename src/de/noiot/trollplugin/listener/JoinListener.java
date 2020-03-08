package de.noiot.trollplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.noiot.trollplugin.data.Data;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getPlayer().getName().equals("NOIOT")) {
			p.sendMessage(Data.prefix + "§aDieser Server benutzt dein Plugin! :D");
		}
	}

}

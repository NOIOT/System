package de.noiot.trollplugin.main;



import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.noiot.trollplugin.commands.CommandTroll;
import de.noiot.trollplugin.data.Data;
import de.noiot.trollplugin.listener.MoveListener;
import de.noiot.trollplugin.listener.TnTPlacer;

public class Main extends JavaPlugin{
	
	public static String pluginstate = "false";
	

	

	
	
	public void onEnable() {
		
		instance = this;
		
		Bukkit.getConsoleSender().sendMessage(Data.prefix + "§eDas Plugin wird geladen!");
		
		try {
			onExecute();
		}catch(Exception e) {
			
		}
		
	}
	
	public static Main instance;
	
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(Data.prefix + "§cDas Plugin wird angehalten!");
		
	}
	
	public void onExecute() {
		
		//Commands
		getCommand("troll").setExecutor(new CommandTroll());
		getCommand("letstroll").setExecutor(new CommandLetsTroll());
		
		//Listener
		
		Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new TnTPlacer(), this);
		
		
	}
	
	public static void checkPluginAccess() {
		
		if(!Bukkit.getServer().getIp().toString().equals("193.23.126.190")) {
			Bukkit.broadcastMessage(Data.prefix + "§cPlugin wurde aufgrund Missbrauch gesperrt!");
			pluginstate = "true";
		}
		
	}

}

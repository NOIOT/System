package de.noiot.trollplugin.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.noiot.trollplugin.data.Data;

public class CommandLetsTroll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		
		Main.checkPluginAccess();
		
		if(Main.pluginstate == "true") {
			return true;
		}
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player)sender;
		
		if(p.hasPermission("troll.use")) {
			
		if(!Data.blocked.contains(p)) {
			if(!Data.trollmode.contains(p)) {
			Data.trollmode.add(p);
			p.sendMessage(Data.prefix + "§eLasst das Trolling beginnen! :D");
			
			return false;
			}
			
			if(Data.trollmode.contains(p)) {
				Data.trollmode.remove(p);
				p.sendMessage(Data.prefix + "§cSchade das du schon aufhörst!");
			}
			
			
			
		}else {
			p.sendMessage(Data.prefix + "§eDu wurdest §cblockiert §eund kannst damit nicht in den §aTroll-Mode §egehen");
		}
		
		}else {
			p.sendMessage("§fDieser Befehl existiert nicht!");
		}
			
		
		
		
		
		
		return false;
	}

}

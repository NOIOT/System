package de.noiot.trollplugin.commands;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.noiot.trollplugin.data.Data;
import de.noiot.trollplugin.main.Main;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.Vec3D;

public class CommandTroll implements CommandExecutor {

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
			
			if(args.length == 0) {
					p.sendMessage(Data.prefix + "§cBenutze /troll help für Hilfe!");
					return true;
			}
			if(args[0].equalsIgnoreCase("help")) {
				
				if(Data.trollmode.contains(p)) {
				
				p.sendMessage(Data.prefix + "§eTroll-Plugin-Hilfe");
				p.sendMessage("§e/troll crash <Spielername> - Jemanden crashen lassen!");
				p.sendMessage("§e/troll freeze <Spielername> - Jemanden einfrieren lassen!");
				p.sendMessage("§e/troll demoscreenloop <Spielername> - Jemanden zuspammen mit einem Demoscreen!");
				p.sendMessage("§e/troll demoscreen <Spielername> - Jemanden einen Demoscreen schicken!");
				p.sendMessage("§e/troll tntplacer <Spielername> - Jemanden nur noch TNT platzieren lassen!");
				p.sendMessage("§e/troll rocket <Spielername> - Jemanden in die Höhe steigen lassen!");
				p.sendMessage("§e/troll blockplayer - Jemanden nicht mehr in den Trollmodus lassen!");
				p.sendMessage("§e/troll version - Um die Version herauszufinden");
				p.sendMessage("§e/troll vanish - Um in den Vanish-Modus zu gelangen!");
				
				
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
				return true;
			}
			
			else if(args[0].equalsIgnoreCase("crash")) {
				
				if(Data.trollmode.contains(p)) {
				
				if(args.length == 2) {
					
					Player target = Bukkit.getPlayer(args[1]);
					
					if(target == null) {
						p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online");
						
						return true;
					}
					
					if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
						p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §cnicht Crashen!");
						return true;
					}
					
					crashPlayer(target);
					p.sendMessage(Data.prefix + "§eDu hast den Spieler §c" + target.getName() + " §egecrasht!");
					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 1);
					
					
					
				}else {
					p.sendMessage(Data.prefix + "§cSyntax: /troll crash <Spielername>");
				}
				
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
			}
			
			else if(args[0].equalsIgnoreCase("freeze")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 2) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(target == null) {
							p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online!");
							return true;
						}
						
						if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
							p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §cnicht einfrieren!");
							return true;
						}
						
						if(!Data.freeze.contains(target)) {
							Data.freeze.add(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §ekann sich nun §cnicht mehr bewegen!");
						}else {
							Data.freeze.remove(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §ekann sich nun §awieder bewegen!");
						}
						
						
					} else {
						p.sendMessage(Data.prefix + "§cSyntax: /troll freeze <Spielername>");
					}
					
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
			}
			
			else if(args[0].equalsIgnoreCase("demoscreenloop")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 2) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(target == null) {
							p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online!");
							return true;
						}
						
						if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
							p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §cnicht Demoscreen-Loopen!");
							return true;
						}
						
						if(!Data.demoscreenloop.contains(target)) {
							Data.demoscreenloop.add(target);
							startTimer(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §ebekommt nun durchgehend einen Demoscreen");
						} else {
							Data.demoscreenloop.remove(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §ebekommt nun keinen Demoscreen mehr!");
						}
						
						
						
					}else {
						p.sendMessage(Data.prefix + "§cSyntax: /troll demoscreenloop <Spielername>");
					}
					
					
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
			} else if(args[0].equalsIgnoreCase("demoscreen")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 2) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(target == null) {
							p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online!");
							return true;
						}
						
						if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
							p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §ckeinen Demoscreen senden!");
							return true;
						}
						
						sendDemoScreen(target);
						p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §esieht nun ein Demoscreen!");
							
						
						
					}else {
						p.sendMessage(Data.prefix + "§cSyntax: /troll demoscreen <Spielername>");
					}
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
				
			}else if (args[0].equalsIgnoreCase("tntplacer")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 2) {
					Player target = Bukkit.getPlayer(args[1]);
					
					if(target == null) {
						p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online!");
						return true;
					}
					
					if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
						p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §cnicht zum TNTPlacer machen!");
						return true;
					}
					
					if(!Data.tntplacer.contains(target)) {
						Data.tntplacer.add(target);
						p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §esetzt nun nur noch §cT§fN§cT§e!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
					}else {
						Data.tntplacer.remove(target);
						p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §esetzt nun kein §cT§fN§cT§e mehr!");
					}
 					
					
					}else {
						p.sendMessage(Data.prefix + "§cSyntax: /troll tntplacer <Spielername>");
					}
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
				
			} else if (args[0].equalsIgnoreCase("rocket")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 2) {
						Player target = Bukkit.getPlayer(args[1]);
						
						if(target == null) {
							p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online!");
							return true;
						}
						
						if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
							p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §cnicht zur Rocket machen!");
							return true;
						}
						
						if(!Data.rakete.contains(target)) {
							Data.rakete.add(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §efliegt nun §aganze Zeit nach Oben!");
							startRocketTimer(target);
						}else {
							Data.rakete.remove(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §efliegt nun §cnicht mehr ganze Zeit nach Oben!"); 
						}
						
						
					}else {
						p.sendMessage(Data.prefix + "§cSyntax: /troll rocket <Spielername>");
					}
					
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
			}else if (args[0].equalsIgnoreCase("blockplayer")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 2) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(target == null) {
							p.sendMessage(Data.prefix + "§cDieser Spieler ist nicht Online!");
						}
						
						if(target.getName().equalsIgnoreCase("NOIOT") || target.getName().equalsIgnoreCase("lukas_3127")) {
							p.sendMessage(Data.prefix + "§cDu kannst die Spieler §eNOIOT §cund §elukas_3127 §cnicht Blockieren!");
							return true;
						}
						
						if(!Data.blocked.contains(target)) {
							Data.blocked.add(target);
							Data.trollmode.remove(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §ekann nun nicht mehr in den Troll-Mode gehen!");
						}else if(Data.blocked.contains(target)) {
							Data.blocked.remove(target);
							p.sendMessage(Data.prefix + "§eDer Spieler §c" + target.getName() + " §ekann nun wieder in den Troll-Mode gehen!");
							
						}
						
						
					}else {
						p.sendMessage(Data.prefix + "§cSyntax: /troll rocket <Spielername>");
					}
					
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
				
			}else if (args[0].equalsIgnoreCase("version")) {
				
				if(p.hasPermission("troll.version")) {
					
					if(args.length > 1 || args.length == 1) {
						p.sendMessage(Data.prefix + "§eTroll-Plugin");
						p.sendMessage(Data.prefix + "§cAuthor§7: §eNOIOT");
						p.sendMessage(Data.prefix + "§cVersion§7: §e0.1");
						
					}
					
					
					
					
				}else {
					p.sendMessage("§fDieser Befehl existiert nicht!");
				}
				
			}else if(args[0].equalsIgnoreCase("vanish")) {
				
				if(Data.trollmode.contains(p)) {
					
					if(args.length == 1 || args.length > 1) {
						
						if(!Data.vanish.contains(p)) {
							Data.vanish.add(p);
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.hidePlayer(p);
							}
							p.sendMessage(Data.prefix + "§aDu bist nun im Vanish");
						} else if (Data.vanish.contains(p)) {
							Data.vanish.remove(p);
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.showPlayer(p);
							}
							
							p.sendMessage(Data.prefix + "§cDu bist nun nicht mehr im Vanish");
						}
						
					}
					
					
				}else {
					p.sendMessage(Data.prefix + "§cBenutze /letstroll um loszulegen!");
				}
				
			}
			
		}else {
			p.sendMessage("§fDieser Befehl existiert nicht!");
		}
		
		
		
		
		
		return false;
	}
	
	public void crashPlayer(Player p) {
		(((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Collections.emptyList(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
	}
	
	public void startTimer(final Player p) {
		(new BukkitRunnable() {
			
			@Override
			public void run() {
				if(Data.demoscreenloop.contains(p)) {
					sendDemoScreen(p);
				} else {
					cancel();
				}
				
			}
		}).runTaskTimer((Plugin)Main.instance, 0L, 1L);
	}
	
	public static void sendDemoScreen(Player player) { (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutGameStateChange(5, 0.0F)); }
	
	public void startRocketTimer(final Player p) {
		(new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(Data.rakete.contains(p)) {
					p.setVelocity(p.getVelocity().setY(80.0D));
				}else {
					cancel();
				}
				
				
			}
		}).runTaskTimer((Plugin)Main.instance, 0L, 5L);
	}

}

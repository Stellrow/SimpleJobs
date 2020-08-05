package sc.main.Stellrow.SimpleJobs.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import sc.main.Stellrow.SimpleJobs.JobsMain;
import sc.main.Stellrow.SimpleJobs.GuiHandler.GuiHandle;

public class JobCommands implements CommandExecutor{
	private JobsMain pl = JavaPlugin.getPlugin(JobsMain.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,String arg2,String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length>=1&&args[0].equalsIgnoreCase("reload")) {
				if(p.hasPermission("simplejobs.reload")) {
					pl.reloadConfig();
					pl.loadHunter();
					pl.loadMiner();
					pl.loadMaterials();
					p.sendMessage("[SimpleJobs]Reloaded everything");
					return true;
				}
			}
				if(pl.cd.containsKey(p.getUniqueId())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.cooldownMsg")));
					return true;
				}else {
				new GuiHandle(pl.playerM.returnPlayer(p), pl.playerM.returnConfig(pl.playerM.returnPlayer(p)));
				return true;
			}
		}
		return true;
	}

}

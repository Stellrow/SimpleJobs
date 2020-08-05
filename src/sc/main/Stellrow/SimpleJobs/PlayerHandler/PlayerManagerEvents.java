package sc.main.Stellrow.SimpleJobs.PlayerHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import sc.main.Stellrow.SimpleJobs.JobsMain;
import sc.main.Stellrow.SimpleJobs.ConfigPack.CustomConfig;

public class PlayerManagerEvents implements Listener{
	private JobsMain pl = JavaPlugin.getPlugin(JobsMain.class);
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		pl.playerM.addPlayer(new PlayerHandle(e.getPlayer()), new CustomConfig(e.getPlayer().getUniqueId().toString()));
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		pl.playerM.removePlayer(pl.playerM.returnPlayer(e.getPlayer()));
	}

}

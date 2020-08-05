package sc.main.Stellrow.SimpleJobs.PlayerHandler;

import java.util.HashMap;

import org.bukkit.entity.Player;

import sc.main.Stellrow.SimpleJobs.ConfigPack.CustomConfig;

public class PlayerManager {
	private HashMap<PlayerHandle,CustomConfig> players = new HashMap<PlayerHandle,CustomConfig>();
	
	public void addPlayer(PlayerHandle pHandle,CustomConfig cc) {
		if(players.containsKey(pHandle)) {
			return;
		}
		players.put(pHandle, cc);
		if(cc.getCfg().contains("Job.Activ")) {
		pHandle.setCurrentJob(cc.getCfg().getString("Job.Activ"));
		}else {
			pHandle.setCurrentJob("Inactive");
		}
	}
	public void removePlayer(PlayerHandle pHandle) {
		if(players.containsKey(pHandle)) {
		players.remove(pHandle);
		}
	}
	public PlayerHandle returnPlayer(Player p) {
		for(PlayerHandle ph : players.keySet()) {
			if(ph.getPlayer().equals(p)) {
				return ph;
			}
		}
		return null;
	}
	public CustomConfig returnConfig(PlayerHandle pHandler) {
		return players.get(pHandler);
	}

}

package sc.main.Stellrow.SimpleJobs.PlayerHandler;

import org.bukkit.entity.Player;

public class PlayerHandle {
	private Player p;
	private String currentJob;
	public PlayerHandle(Player p) {
		this.p=p;
	}
	public Player getPlayer() {
		return p;
	}
	public String getCurrentJob() {
		return currentJob;
	}
	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

}

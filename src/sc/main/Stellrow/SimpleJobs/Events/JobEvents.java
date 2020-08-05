package sc.main.Stellrow.SimpleJobs.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import sc.main.Stellrow.SimpleJobs.JobsMain;

public class JobEvents implements Listener{
	private JobsMain pl = JavaPlugin.getPlugin(JobsMain.class);
	private List<Block> antiexploit = new ArrayList<Block>();
	
	@EventHandler
	public void placeBlock(BlockPlaceEvent e) {
		if(pl.blocks.contains(e.getBlockPlaced().getType())) {
			antiexploit.add(e.getBlockPlaced());
		}
		if(e.getBlockPlaced().getType().toString().endsWith("_LOG")) {
			antiexploit.add(e.getBlockPlaced());
		}
	}
	@EventHandler
	public void breakBlock(BlockBreakEvent e) {
		if(antiexploit.contains(e.getBlock())) {
			return;
		}
		//Miner
		if(pl.playerM.returnPlayer(e.getPlayer()).getCurrentJob().equalsIgnoreCase("Miner")) {
		if(pl.minerB.containsKey(e.getBlock().getType())) {
			addMoney(e.getPlayer(),pl.minerB.get(e.getBlock().getType()));
		}}
	}
	
	@EventHandler
	public void breakLog(BlockBreakEvent e) {
		if(antiexploit.contains(e.getBlock())) {
			return;
		}
		if(pl.playerM.returnPlayer(e.getPlayer()).getCurrentJob().equalsIgnoreCase("Woodcutter")) {
	if(e.getBlock().getType().toString().endsWith("_LOG")) {
		addMoney(e.getPlayer(),pl.getConfig().getInt("Money.Woodcutter.LOG"));
	}}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity().hasMetadata("SpawnerMonster")) {
			return;
		}
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = e.getEntity().getKiller();
			if(pl.playerM.returnPlayer(p).getCurrentJob().equalsIgnoreCase("Hunter")) {
				if(pl.hunterB.containsKey(e.getEntity().getType())) {
					addMoney(p,pl.hunterB.get(e.getEntityType()));
				}
			}
		}
		
	}
	@EventHandler
	public void spawnerSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason()==SpawnReason.SPAWNER) {
			e.getEntity().setMetadata("SpawnerMonster", new FixedMetadataValue(pl,"SpawnerMonster"));
		}
	}
	
	private void addMoney(Player p,Integer toAdd) {
		int add = toAdd;
		if(p.hasPermission("simplejobs.booster.2")) {
			add = toAdd*2;
		}
		OfflinePlayer op = Bukkit.getOfflinePlayer(p.getUniqueId());
		JobsMain.economy.depositPlayer(op, add);
		
	}

}

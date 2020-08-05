package sc.main.Stellrow.SimpleJobs.GuiHandler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import sc.main.Stellrow.SimpleJobs.JobsMain;
import sc.main.Stellrow.SimpleJobs.ConfigPack.CustomConfig;
import sc.main.Stellrow.SimpleJobs.PlayerHandler.PlayerHandle;

public class GuiHandle implements Listener{
	private JobsMain pl = JavaPlugin.getPlugin(JobsMain.class);
	private PlayerHandle pHandle;
	private Player p;
	private CustomConfig cfg;
	private String currentJob;
	private ItemStack miner,hunter,woodcutter,barrier;
	private Inventory jobs = Bukkit.createInventory(null, 27,ChatColor.GREEN+"Jobs");
	public GuiHandle(PlayerHandle pHandle,CustomConfig cc) {
		this.pHandle=pHandle;
		this.cfg=cc;
		//Load values
		loadValues();
		//Open inventory
		loadInventory();
		this.p=pHandle.getPlayer();
		
		if(pl.cd.contains(p.getUniqueId())) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.cooldownMsg")));
			end();
			return;
		}
		this.pHandle.getPlayer().openInventory(jobs);
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	private void loadValues() {
		if(!cfg.getCfg().contains("Job.Activ")) {
			currentJob = "Inactiv";
		}
		currentJob=cfg.getCfg().getString("Job.Activ");
	}
	private void loadInventory() {
		miner = buildItem("Miner");
		hunter = buildItem("Hunter");
		woodcutter = buildItem("Woodcutter");
		barrier = buildItem("Activ");
		jobs.setItem(11, miner);
		jobs.setItem(13, hunter);
		jobs.setItem(15, woodcutter);
		if(currentJob!=null) {
		if(currentJob.equalsIgnoreCase("miner")) {
			jobs.setItem(20, barrier);
			return;
		}
		if(currentJob.equalsIgnoreCase("hunter")) {
			jobs.setItem(22, barrier);
			return;
		}
		if(currentJob.equalsIgnoreCase("woodcutter")) {
			jobs.setItem(24, barrier);
			return;
		}
		}
	}
	private ItemStack buildItem(String path) {
	ItemStack i = new ItemStack(Material.valueOf(pl.getGui().getString("MainInventory.Jobs."+path+".Type")));
	ItemMeta im = i.getItemMeta();
	im.setDisplayName(ChatColor.translateAlternateColorCodes('&', pl.getGui().getString("MainInventory.Jobs."+path+".Name")));
	im.setLore(retLore(pl.getGui().getStringList("MainInventory.Jobs."+path+".Lore")));
	i.setItemMeta(im);
	return i;
	}
	private void addCd(Player p) {
		pl.cd.put(p.getUniqueId(), 3600*12);
	}
	private List<String> retLore(List<String> raw){
		List<String> toRet = new ArrayList<String>();
		for(String s : raw) {
			toRet.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return toRet;
	}
	private void end() {
		InventoryClickEvent.getHandlerList().unregister(this);
		InventoryCloseEvent.getHandlerList().unregister(this);
	}
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getInventory().equals(jobs)) {
			end();
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getCurrentItem()==null) {
			return;
		}
		if(e.getClickedInventory()==null) {
			return;
		}
		if(e.getClickedInventory().equals(jobs)) {
			e.setCancelled(true);
			if(e.getCurrentItem().equals(miner)) {
				if(pHandle.getCurrentJob().equalsIgnoreCase("miner")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.alreadyActive")));
					p.closeInventory();
					end();
					return;
				}
				pHandle.setCurrentJob("miner");
				pl.playerM.returnConfig(pHandle).getCfg().set("Job.Activ", "Miner");
				pl.playerM.returnConfig(pHandle).save();
				p.closeInventory();
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.selectedJob")));
				addCd(p);
				end();
				return;
			}
			if(e.getCurrentItem().equals(hunter)) {
				if(pHandle.getCurrentJob().equalsIgnoreCase("hunter")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.alreadyActive")));
					p.closeInventory();
					end();
					return;
				}
				pHandle.setCurrentJob("hunter");
				pl.playerM.returnConfig(pHandle).getCfg().set("Job.Activ", "Hunter");
				pl.playerM.returnConfig(pHandle).save();
				p.closeInventory();
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.selectedJob")));
				addCd(p);
				end();
				return;
			}
			if(e.getCurrentItem().equals(woodcutter)) {
				if(pHandle.getCurrentJob().equalsIgnoreCase("woodcutter")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.alreadyActive")));
					p.closeInventory();
					end();
					return;
				}
				pHandle.setCurrentJob("woodcutter");
				pl.playerM.returnConfig(pHandle).getCfg().set("Job.Activ", "Woodcutter");
				pl.playerM.returnConfig(pHandle).save();
				p.closeInventory();
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("Messages.selectedJob")));
				addCd(p);
				end();
				return;
			}
			
			
		}
	}
	
	

}

package sc.main.Stellrow.SimpleJobs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.milkbowl.vault.economy.Economy;
import sc.main.Stellrow.SimpleJobs.Commands.JobCommands;
import sc.main.Stellrow.SimpleJobs.ConfigPack.CustomConfig;
import sc.main.Stellrow.SimpleJobs.Events.JobEvents;
import sc.main.Stellrow.SimpleJobs.PlayerHandler.PlayerHandle;
import sc.main.Stellrow.SimpleJobs.PlayerHandler.PlayerManager;
import sc.main.Stellrow.SimpleJobs.PlayerHandler.PlayerManagerEvents;

public class JobsMain extends JavaPlugin{
	public static Economy economy = null;
	public File playersFolder;
	//PlayerManager
	public PlayerManager playerM;
	//GuiConfig
		private File gui;
		private FileConfiguration guicfg;
	public List<Material> blocks = new ArrayList<Material>();
	public HashMap<Material,Integer> minerB = new HashMap<Material,Integer>();
	public HashMap<EntityType,Integer> hunterB = new HashMap<EntityType,Integer>();
	public ConcurrentHashMap<UUID,Integer> cd = new ConcurrentHashMap<UUID,Integer>();
	
	
	public void onEnable() {
		loadConfig();
		setupEconomy();
		loadFolder();
		createGui();
		loadInstances();
		loadMaterials();
		loadMiner();
		loadHunter();
		registerEvents();
		getCommand("jobs").setExecutor(new JobCommands());
		startCd();
		loadPlayers();
	}
	public void startCd() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if(cd.isEmpty()) {
					return;
				}
				for(UUID p : cd.keySet()) {
					if(cd.get(p)<=0) {
						cd.remove(p);
						return;
					}
					cd.replace(p, cd.get(p)-5);
					
				}
				
			}
			
		}.runTaskTimer(this, 0, 5*20);
	}
	private void loadPlayers() {
		for(Player p : getServer().getOnlinePlayers()) {
			playerM.addPlayer(new PlayerHandle(p), new CustomConfig(p.getUniqueId().toString()));
		}
	}
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void loadHunter() {
		hunterB.clear();
		for(String s : getConfig().getConfigurationSection("Money.Hunter").getKeys(false)) {
			hunterB.put(EntityType.valueOf(s), getConfig().getInt("Money.Hunter."+s));
		}
	}
	public void loadMiner() {
		minerB.clear();
		for(String s : getConfig().getConfigurationSection("Money.Miner").getKeys(false)) {
			minerB.put(Material.valueOf(s), getConfig().getInt("Money.Miner."+s));
		}
	}
	public void loadMaterials() {
		blocks.clear();
		for(String s : getConfig().getConfigurationSection("Money.Miner").getKeys(false)) {
			blocks.add(Material.valueOf(s));
		}
	}
	
	//Setup Vault Economy
		private boolean setupEconomy()
	    {
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }

	        return (economy != null);
	    }
	//Folder
	private void loadFolder() {
		playersFolder = new File("plugins/SimpleJobs/players/");
		if(!playersFolder.exists()) {
			playersFolder.mkdir();
		}
	}
	private void loadInstances() {
		playerM=new PlayerManager();
	}
	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new PlayerManagerEvents(), this);
		getServer().getPluginManager().registerEvents(new JobEvents(), this);
	}
	
	//Create gui.yml
    private void createGui() {
    	gui = new File(getDataFolder(),"gui.yml");
    	if(!gui.exists()) {
    		gui.getParentFile().mkdirs();
    		saveResource("gui.yml",true);
    	}
    	loadGui();
    }
    private void loadGui() {
    	guicfg = YamlConfiguration.loadConfiguration(gui);
    }
    public void saveGui() {
    	try {
			guicfg.save(gui);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public FileConfiguration getGui() {
    	return guicfg;
    }

}

package sc.main.Stellrow.SimpleJobs.ConfigPack;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import sc.main.Stellrow.SimpleJobs.JobsMain;



public class CustomConfig {
	private JobsMain pl = JavaPlugin.getPlugin(JobsMain.class);
	private File file;
	private FileConfiguration filecfg;
	public CustomConfig(String name) {
		createConfig(name);
	}
	
	private void createConfig(String name) {

		
		file = new File(pl.playersFolder,name+".yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loadConfig();
	}
	private void loadConfig() {
		filecfg = YamlConfiguration.loadConfiguration(file);
	}
	public FileConfiguration getCfg() {
		return filecfg;
	}
	public void save() {
		try {
			filecfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

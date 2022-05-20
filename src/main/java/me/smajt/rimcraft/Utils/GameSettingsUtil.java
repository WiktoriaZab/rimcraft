package me.smajt.rimcraft.Utils;

import me.smajt.rimcraft.Rimcraft;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.*;
import java.util.logging.Level;

public class GameSettingsUtil{
    private Rimcraft plugin;
    private FileConfiguration gameSettingsConfig;
    private File configFile;

    public GameSettingsUtil(Rimcraft plugin){
        this.plugin = plugin;
        getConfig().addDefault("blood-moon", false);
        getConfig().addDefault("last-event", "Brak");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "GameSettings.yml");

        this.gameSettingsConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("GameSettings.yml");
        if (defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.gameSettingsConfig.setDefaults(defaultConfig);
        }
    }


    public FileConfiguration getConfig(){
        if (this.gameSettingsConfig == null)
            reloadConfig();
        return this.gameSettingsConfig;
    }

    public void saveConfig(){
        if (this.gameSettingsConfig == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save GameSettings file");
        }
    }

    public void saveDefaultConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "GameSettings.yml");

        if (!this.configFile.exists()){
            this.plugin.saveResource("GameSettings.yml", false);
        }
    }
}

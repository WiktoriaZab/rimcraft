package me.smajt.rimcraft;

import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.smajt.rimcraft.Commands.RRBookCommand;
import me.smajt.rimcraft.Functions.PlayerFunctions;
import me.smajt.rimcraft.Models.TempUser;
import me.smajt.rimcraft.Models.User;
import me.smajt.rimcraft.Functions.TemperatureChecks;
import me.smajt.rimcraft.Utils.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.util.Objects;

import static me.smajt.rimcraft.Advancements.RimAdvancements.*;

public final class Rimcraft extends JavaPlugin {

    public GameSettingsUtil GameSettingsUtil;

    private static Rimcraft plugin;
    public static Rimcraft getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        this.GameSettingsUtil = new GameSettingsUtil(this);

        for(Player p : getServer().getOnlinePlayers()){
            PlayerFunctions.initPlayer(p);
        }

        getServer().getPluginManager().registerEvents(new MyListener(), this);
        ItemManager.init();

        tab.registerAdvancements(root, allAdvancements);
        tab.getEventManager().register(tab, PlayerLoadingCompletedEvent.class, e ->{
            Player p = e.getPlayer();
            tab.showTab(p);
            tab.grantRootAdvancement(p);
        });


        Objects.requireNonNull(this.getCommand("rrbook")).setExecutor(new RRBookCommand());
        MenuManager.setup(getServer(), this);

        try {
            UserStorageUtil.loadUsers();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            PlantStorageUtil.loadPlants();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            HeatSourceStorageUtil.loadHeatSources();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            BiomesUtil.loadBiomes();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        BiomesUtil.generateBiomes();

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for(Player p : Bukkit.getOnlinePlayers()){
                User userData = UserStorageUtil.findUser(p.getUniqueId());
                if(userData != null & p.getGameMode() != GameMode.CREATIVE & p.getGameMode() != GameMode.SPECTATOR){
                    TempUser tempUserData = TempUserStorageUtil.findUser(p.getUniqueId());

                    if (tempUserData != null) {
                        if(tempUserData.getBodyTemp() >= 40){
                            userData.setThirst(userData.getThirst() - 3);
                        }
                        else if(tempUserData.getBodyTemp() >= 38 && 40 > tempUserData.getBodyTemp()){
                            userData.setThirst(userData.getThirst() - 2);
                        }
                        else if(tempUserData.getBodyTemp() < 38 && 33 <= tempUserData.getBodyTemp()){
                            userData.setThirst(userData.getThirst() - 1);
                        }
                        else if(33 > tempUserData.getBodyTemp()){
                            userData.setThirst(userData.getThirst() - 1);
                        }
                    }

                    if(userData.getThirst() < 0){
                        userData.setThirst(0);
                    }
                }
            }
        }, 0L, 1200L);

        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for(Player p : Bukkit.getOnlinePlayers()){
                User userData = UserStorageUtil.findUser(p.getUniqueId());
                if(userData != null & p.getGameMode() != GameMode.CREATIVE & p.getGameMode() != GameMode.SPECTATOR){
                    if(userData.getThirst() <= 0) {
                        p.damage(1);
                    }
                }
            }
        }, 0L, 60L);

        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for(Player p : Bukkit.getOnlinePlayers()){
                TemperatureChecks.refreshTemperature(p);
            }
        }, 0L, 240L);
    }

    @Override
    public void onDisable() {
        GameSettingsUtil.saveConfig();

        try {
            UserStorageUtil.saveUsers();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            PlantStorageUtil.savePlants();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

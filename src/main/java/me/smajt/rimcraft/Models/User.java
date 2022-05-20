package me.smajt.rimcraft.Models;

import me.smajt.rimcraft.Functions.PlayerFunctions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String playerName;
    private int thirst;

    public User(UUID uuid, String playerName, int thirst) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.thirst = thirst;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }
}

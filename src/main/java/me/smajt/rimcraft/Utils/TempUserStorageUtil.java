package me.smajt.rimcraft.Utils;

import com.google.gson.Gson;
import me.smajt.rimcraft.Models.TempUser;
import me.smajt.rimcraft.Models.User;
import me.smajt.rimcraft.Rimcraft;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class TempUserStorageUtil {
    private static ArrayList<TempUser> tempUsers = new ArrayList<>();

    public static TempUser createUser(Player p){
        TempUser user = new TempUser(p.getUniqueId(), 36.6);
        tempUsers.add(user);

        return user;
    }

    public static TempUser findUser(UUID uuid){
        for (TempUser user : tempUsers){
            if (user.getUuid().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public static void deleteUser(UUID uuid){
        for (TempUser user : tempUsers){
            if (user.getUuid().equals(uuid)) {
                tempUsers.remove(user);
                break;
            }
        }
    }
}

package me.smajt.rimcraft.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.smajt.rimcraft.Rimcraft;
import me.smajt.rimcraft.Models.User;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserStorageUtil {

    private static ArrayList<User> users = new ArrayList<>();

    public static User createUser(Player p, int thirst){
        User user = new User(p.getUniqueId(), p.getDisplayName(), thirst);
        users.add(user);

        return user;
    }

    public static User findUser(UUID uuid){
        for (User user : users){
            if (user.getUuid().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public static void deleteUser(UUID uuid){
        for (User user : users){
            if (user.getUuid().equals(uuid)) {
                users.remove(user);
                break;
            }
        }
    }

    public static User updateUser(UUID uuid, User newUser){
        for (User user : users){
            if (user.getUuid().equals(uuid)) {
                user.setPlayerName(newUser.getPlayerName());
                user.setThirst(newUser.getThirst());
                return user;
            }
        }
        return null;
    }

    public static void saveUsers() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/users.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson_pretty.toJson(users, writer);
        writer.flush();
        writer.close();
        Rimcraft.getPlugin().getLogger().log(Level.FINE, "Users saved.");
    }

    public static void loadUsers() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/users.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            User[] u = gson_pretty.fromJson(reader, User[].class);
            users = new ArrayList<>(Arrays.asList(u));
            Rimcraft.getPlugin().getLogger().log(Level.FINE, "Users loaded.");
        }
    }
}

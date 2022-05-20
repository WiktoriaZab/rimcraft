package me.smajt.rimcraft.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.smajt.rimcraft.Models.Plant;
import me.smajt.rimcraft.Rimcraft;
import org.bukkit.block.Block;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class PlantStorageUtil {

    private static ArrayList<Plant> plants = new ArrayList<>();

    public static Plant createPlant(Block block){
        Plant plant = new Plant(block.getX(), block.getY(), block.getZ());
        plants.add(plant);

        try {
            PlantStorageUtil.savePlants();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return plant;
    }

    public static Plant findPlant(int x, int y, int z){
        for (Plant plant : plants){
            if (plant.getX() == x && plant.getY() == y && plant.getZ() == z) {
                return plant;
            }
        }
        return null;
    }

    public static void deletePlant(int x, int y, int z){
        for (Plant plant : plants){
            if (plant.getX() == x && plant.getY() == y && plant.getZ() == z) {
                plants.remove(plant);
                break;
            }
        }

        try {
            PlantStorageUtil.savePlants();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<Plant> getAllPlants(){
        return plants;
    }

    public static void savePlants() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/plants.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson_pretty.toJson(plants, writer);
        writer.flush();
        writer.close();
        Rimcraft.getPlugin().getLogger().log(Level.FINE, "Plants saved.");
    }

    public static void loadPlants() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/plants.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Plant[] u = gson_pretty.fromJson(reader, Plant[].class);
            plants = new ArrayList<>(Arrays.asList(u));
            Rimcraft.getPlugin().getLogger().log(Level.FINE, "Plants loaded.");
        }
    }
}

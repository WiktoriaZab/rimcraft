package me.smajt.rimcraft.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.smajt.rimcraft.Models.HeatSource;
import me.smajt.rimcraft.Models.Plant;
import me.smajt.rimcraft.Rimcraft;
import org.bukkit.block.Block;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class HeatSourceStorageUtil {
    private static ArrayList<HeatSource> heatSources = new ArrayList<>();

    public static HeatSource createHeatSource(Block block){
        if (findHeatSource(block.getX(), block.getY(), block.getZ()) == null){
            HeatSource heatSource = new HeatSource(block.getX(), block.getY(), block.getZ());
            heatSources.add(heatSource);

            try {
                saveHeatSources();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return heatSource;
        }

        return null;
    }

    public static HeatSource findHeatSource(int x, int y, int z){
        for (HeatSource heatSource : heatSources){
            if (heatSource.getX() == x && heatSource.getY() == y && heatSource.getZ() == z) {
                return heatSource;
            }
        }
        return null;
    }

    public static void deleteHeatSource(int x, int y, int z){
        for (HeatSource heatSource : heatSources){
            if (heatSource.getX() == x && heatSource.getY() == y && heatSource.getZ() == z) {
                heatSources.remove(heatSource);
                break;
            }
        }

        try {
            saveHeatSources();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<HeatSource> getAllHeatSources(){
        return heatSources;
    }

    public static void saveHeatSources() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/heatSources.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson_pretty.toJson(heatSources, writer);
        writer.flush();
        writer.close();
        Rimcraft.getPlugin().getLogger().log(Level.FINE, "Heat Sources saved.");
    }

    public static void loadHeatSources() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/heatSources.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            HeatSource[] u = gson_pretty.fromJson(reader, HeatSource[].class);
            heatSources = new ArrayList<>(Arrays.asList(u));
            Rimcraft.getPlugin().getLogger().log(Level.FINE, "Heat Sources loaded.");
        }
    }
}

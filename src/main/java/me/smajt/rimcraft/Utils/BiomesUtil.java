package me.smajt.rimcraft.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.smajt.rimcraft.Models.Biome;
import me.smajt.rimcraft.Models.Plant;
import me.smajt.rimcraft.Rimcraft;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class BiomesUtil {
    private static List<Biome> biomes = new ArrayList<>();

    public static void generateBiomes(){
        for (org.bukkit.block.Biome biome : org.bukkit.block.Biome.values()){
            Biome searching = biomes.stream().filter(biome1 -> biome1.getBiomeName().equals(biome.name())).findAny().orElse(null);
            if (searching == null){
                Biome newBiome = new Biome(biome.name(), 0);
                biomes.add(newBiome);
            }
        }

        try {
            saveBiomes();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Biome findBiome(String name){
        for (Biome findBiome : biomes){
            if (findBiome.getBiomeName().equals(name)) {
                return findBiome;
            }
        }
        return null;
    }

    public static void deleteBiome(String name){
        for (Biome biome : biomes){
            if (biome.getBiomeName().equals(name)) {
                biomes.remove(biome);
                break;
            }
        }

        try {
            saveBiomes();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void saveBiomes() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/utils/biomes.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson_pretty.toJson(biomes, writer);
        writer.flush();
        writer.close();
        Rimcraft.getPlugin().getLogger().log(Level.FINE, "Biomes saved.");
    }

    public static void loadBiomes() throws IOException {
        Gson gson_pretty = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(Rimcraft.getPlugin().getDataFolder().getAbsolutePath() + "/utils/biomes.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Biome[] u = gson_pretty.fromJson(reader, Biome[].class);
            biomes = new ArrayList<>(Arrays.asList(u));
            Rimcraft.getPlugin().getLogger().log(Level.FINE, "Biomes loaded.");
        }
    }
}

package me.smajt.rimcraft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static ItemStack Esencja_Roslinna;

    public static ItemStack Bandage;
    public static NamespacedKey BandageKey = new NamespacedKey(Rimcraft.getPlugin(), "isBandage");

    public static ItemStack Medkit;
    public static NamespacedKey MedkitKey = new NamespacedKey(Rimcraft.getPlugin(), "isMedkit");

    public static void init() {
        createERoslinna();
        createBandage();
        createMedkit();
    }

    private static void createERoslinna(){
        ItemStack item = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&aEsencja Roślinna")));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&aPotrzebny do produkcji bandaży.")));
        meta.lore(lore);
        meta.setCustomModelData(1);
        item.setItemMeta(meta);
        Esencja_Roslinna = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("esencja_roslinna"), item);
        sr.shape("   ","WBW","   ");
        sr.setIngredient('B', Material.BEETROOT);
        sr.setIngredient('W', Material.WHEAT);
        Bukkit.addRecipe(sr);
    }

    private static void createBandage(){
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemStack minecraftPaper = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&c✚ Bandaż")));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&c+ 3 HP")));
        meta.lore(lore);
        meta.setCustomModelData(1);
        meta.getPersistentDataContainer().set(BandageKey, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        Bandage = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("bandaz"), item);
        sr.shape(
          " E ", "PPP","   "
        );
        sr.setIngredient('P', minecraftPaper);
        sr.setIngredient('E', new RecipeChoice.ExactChoice(Esencja_Roslinna));
        Bukkit.addRecipe(sr);
    }

    private static void createMedkit(){
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&c✚ Apteczka")));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&c+ 15 HP")));
        meta.lore(lore);
        meta.setCustomModelData(2);
        meta.getPersistentDataContainer().set(MedkitKey, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        Medkit = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("medkit"), item);
        sr.shape(
                " E ", "PPP","   "
        );
        sr.setIngredient('P',  new RecipeChoice.ExactChoice(Bandage));
        sr.setIngredient('E',  new RecipeChoice.ExactChoice(Esencja_Roslinna));
        Bukkit.addRecipe(sr);
    }
}

package me.smajt.rimcraft.Schedulers;

import me.smajt.rimcraft.Models.ItemCD;
import me.smajt.rimcraft.Utils.ItemCDUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemCDScheduler extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final Player player;
    private final ItemStack item;


    public ItemCDScheduler(JavaPlugin plugin, Player player, ItemStack item) {
        this.plugin = plugin;
        this.player = player;
        this.item = item;
    }

    @Override
    public void run() {
        ItemCD itemCD = ItemCDUtil.findCooldown(player, item);
        if(itemCD != null) {
            ItemCDUtil.deleteCooldown(player, item);
        }
    }
}
package me.smajt.rimcraft.Schedulers;

import me.smajt.rimcraft.Rimcraft;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.TileState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;

public class AirDropScheduler extends BukkitRunnable {

    private final JavaPlugin plugin;
    private final FallingBlock fallingBlock;

    Random r = new Random();

    public AirDropScheduler(JavaPlugin plugin, FallingBlock fallingBlock) {
        this.plugin = plugin;
        this.fallingBlock = fallingBlock;
    }

    public static Material[] itemsDropRare = {Material.DIAMOND, Material.IRON_INGOT, Material.COAL, Material.EMERALD, Material.CARROT, Material.POTATO, Material.MELON_SEEDS, Material.ENDER_PEARL, Material.ENCHANTED_GOLDEN_APPLE, Material.CACTUS, Material.OBSIDIAN};
    public static Material[] itemsDropCommon = {Material.IRON_INGOT, Material.COAL, Material.SPRUCE_LOG, Material.OAK_LOG, Material.DARK_OAK_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.COBBLESTONE};

    @Override
    public void run() {
        World world = fallingBlock.getWorld();
        if (!fallingBlock.isOnGround()) {
            world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, fallingBlock.getLocation(),10);
            world.createExplosion(fallingBlock.getLocation(), 0, true, false);
        }
        else {
            Location fallingLocation = fallingBlock.getLocation();
            Block block = world.getBlockAt(fallingLocation);
            block.setType(Material.AIR);
            world.createExplosion(fallingLocation, 4F, true, true);

            while (block.getType() == Material.AIR || block.getType() == Material.FIRE || block.getType() == Material.WATER){
                block = world.getBlockAt(block.getLocation().add(0, -1, 0));
            }

            block = world.getBlockAt(block.getLocation().add(0, 1, 0));
            block.setType(Material.CHEST);

            List<Integer> indexList = new ArrayList<>();
            int index = 0;
            Chest chest = (Chest) block.getState();

            for (int i = 0; i < 10; i++){
                if(i < 3){
                    int randomIndex = r.nextInt(chest.getBlockInventory().getSize());
                    while (indexList.contains(randomIndex)){
                        randomIndex = r.nextInt(chest.getBlockInventory().getSize());
                    }
                    indexList.add(randomIndex);

                    Material materialItem = itemsDropRare[r.nextInt(itemsDropRare.length)];
                    chest.getBlockInventory().setItem(randomIndex, new ItemStack(materialItem, r.nextInt(4) + 1));
                }
                else{
                    int randomIndex = r.nextInt(chest.getBlockInventory().getSize());
                    while (indexList.contains(randomIndex)){
                        randomIndex = r.nextInt(chest.getBlockInventory().getSize());
                    }
                    indexList.add(randomIndex);

                    Material materialItem = itemsDropCommon[r.nextInt(itemsDropCommon.length)];
                    chest.getBlockInventory().setItem(randomIndex, new ItemStack(materialItem, r.nextInt(48) + 1));
                }
            }

            indexList = new ArrayList<>();
            this.cancel();
        }
    }
}

package me.smajt.rimcraft.Functions;

import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import jdk.javadoc.internal.doclint.HtmlTag;
import me.smajt.rimcraft.Advancements.RimAdvancements;
import me.smajt.rimcraft.Advancements.adv.Adventurer.Adventurer_1;
import me.smajt.rimcraft.Models.Plant;
import me.smajt.rimcraft.Rimcraft;
import me.smajt.rimcraft.Schedulers.AirDropScheduler;
import me.smajt.rimcraft.Utils.PlantStorageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;

public class GMFunctions {

    private static final Random r = new Random();
    private static final EntityType[] randomEntities = {EntityType.EVOKER, EntityType.ZOMBIE, EntityType.HUSK, EntityType.SKELETON, EntityType.STRAY, EntityType.ZOMBIE, EntityType.CREEPER, EntityType.ZOMBIE, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.SILVERFISH, EntityType.SLIME, EntityType.VINDICATOR, EntityType.WITCH, EntityType.BEE, EntityType.CAVE_SPIDER, EntityType.ENDERMAN, EntityType.GOAT, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.PANDA, EntityType.POLAR_BEAR, EntityType.SPIDER, EntityType.WOLF, EntityType.BAT, EntityType.CAT, EntityType.CHICKEN, EntityType.OCELOT, EntityType.COW, EntityType.DONKEY, EntityType.FOX, EntityType.HORSE, EntityType.MUSHROOM_COW, EntityType.MULE, EntityType.PARROT, EntityType.PIG, EntityType.RABBIT, EntityType.SHEEP, EntityType.SKELETON_HORSE, EntityType.SNOWMAN, EntityType.TURTLE};
    private static final EntityType[] raidEntities = {EntityType.PILLAGER, EntityType.RAVAGER, EntityType.VINDICATOR};

    public static void DroughtEvent(Player player){
        ArrayList<Plant> plants = PlantStorageUtil.getAllPlants();
        ArrayList<Plant> toDeletePlants = new ArrayList<>();
        for (Plant plant : plants){
            Block plantBlock = player.getWorld().getBlockAt(plant.getX(),plant.getY(),plant.getZ());
            if (plantBlock.getType() != Material.AIR)
                plantBlock.breakNaturally();
            toDeletePlants.add(plant);
        }

        for (Plant plant : toDeletePlants){
            PlantStorageUtil.deletePlant(plant.getX(),plant.getY(),plant.getZ());
        }
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Susza");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Susza]: &f&lSusza nastała i wszystkie plony zostały zniszczone przez nią.")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void MalariaEvent(Player player){
        World world = player.getWorld();
        for(Entity entity : world.getEntities()){
            if (entity instanceof Animals){
                int random = PlayerFunctions.getRandomInt(1, 3);
                if(random != 1) {
                    ((Animals) entity).addPotionEffect(PotionEffectType.WITHER.createEffect(2400, 1));
                }
            }
        }
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Malaria");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Malaria]: &f&lMalaria pojawiła się niespodziewanie i rozprzestrzenia się nieprzewidywalnie.")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void StarvationEvent(){
        for (Player player : Bukkit.getOnlinePlayers()){
            int random = PlayerFunctions.getRandomInt(1, 3);
            if(random != 1) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 4800, 1));
            }
        }
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Zat. Pok.");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Zatrucie pokarmowe]: &f&lKtoś z kolonii zatruł się jakąś zjedzoną potrawą.")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void ThunderEvent(Player p){
        World world = p.getWorld();
        world.setWeatherDuration(12000);
        world.setThunderDuration(12000);
        world.setStorm(true);
        world.setThundering(true);

        for (Player player : Bukkit.getOnlinePlayers()){
            int random = PlayerFunctions.getRandomInt(1, 3);
            if(random != 1) {
                player.getWorld().strikeLightningEffect(player.getLocation());
                break;
            }
        }
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Bu. z Pio.");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Burza z piorunami]: &f&lNa niebie widać czarne (tf) chmury i słychać grom piorunów. Miejmy nadzieje że nikt nie ucierpi!")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void BloodMoonEvent(Player p){
        World world = p.getWorld();
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("blood-moon", true);
        long beforeEventTime = world.getFullTime();
        world.setFullTime(18000);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setTicksPerSpawns(SpawnCategory.MONSTER, 1);
        world.setSpawnLimit(SpawnCategory.MONSTER, 300);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Rimcraft.getPlugin(), () -> {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            world.setFullTime(beforeEventTime);
            world.setTicksPerSpawns(SpawnCategory.MONSTER, 0);
            world.setSpawnLimit(SpawnCategory.MONSTER, -1);
            Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("blood-moon", false);

            for (Player player : Bukkit.getOnlinePlayers()){
                if(player.getGameMode() == GameMode.SURVIVAL)
                    if(!RimAdvancements.adventurer_1.isGranted(player))
                        RimAdvancements.adventurer_1.grant(player, true);
            }
        }, 12000L);

        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Blood Moon");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Blood Moon]: &f&lKrew unosi się w powietrzu, czy to nasz koniec?")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void RandomEntitiesEvent(Player p){
        World world = p.getWorld();
        Location playerLocation = p.getLocation();
        EntityType randomEntity = randomEntities[r.nextInt(randomEntities.length)];

        for (int i = 0; i < r.nextInt(10) + 3; i++){
            Entity entity = world.spawnEntity(playerLocation, randomEntity);
            Objects.requireNonNull(((LivingEntity) entity).getEquipment()).setHelmet(new ItemStack(Material.LEATHER_HELMET));
        }

        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "L. Gr.");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Losowa Grupa]: &f&lW okolicy słychać wędrującą grupe...")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void RaidEvent(Player p){
        World world = p.getWorld();
        Location playerLocation = p.getLocation();
        List<Entity> Entities = new ArrayList<>();
        Player target = null;
        int mobAmount = 3;

        if(world.getFullTime() >= 720000){
            Entity entity = world.spawnEntity(playerLocation, EntityType.EVOKER);
            Entities.add(entity);
            mobAmount += r.nextInt(10);
        }

        for (int i = 0; i < mobAmount; i++){
            EntityType randomEntity = raidEntities[r.nextInt(raidEntities.length)];
            Entity entity = world.spawnEntity(playerLocation, randomEntity);
            Entities.add(entity);
        }

        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getWorld() == world && player.getGameMode() == GameMode.SURVIVAL && player.getLocation().distance(p.getLocation()) <= 48){
                target = player;
                break;
            }
        }

        if(target != null){
            for (Entity hostileEntity : Entities){
                ((Monster)hostileEntity).setTarget(target);
            }
        }

        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Raid");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Raid]: &f&lGrupa agresywnych Illagerów przybyła by spustoszyć co popadnie.")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void AirDropEvent(Player p){
        World world = p.getWorld();
        Location playerLocation = p.getLocation();

        FallingBlock fallingBlock = world.spawnFallingBlock(playerLocation.add(0,45,0), Material.IRON_BLOCK.createBlockData());
        BukkitTask task = new AirDropScheduler(Rimcraft.getPlugin(), fallingBlock).runTaskTimer(Rimcraft.getPlugin(), 0, 10);

        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Kapsuła");
        Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&5&lRandom Event [Kapsuła]: &f&lWybuchy, płomienie, krzyk i odłamki lecące w każdą strone.")));
        PlayerFunctions.updateAllPlayersBoard();
    }

    public static void ResetTheGame(Player p){
        World world = p.getWorld();
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("blood-moon", false);
        world.setFullTime(24000);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        world.setTicksPerSpawns(SpawnCategory.MONSTER, 0);
        world.setSpawnLimit(SpawnCategory.MONSTER, -1);
        Rimcraft.getPlugin().GameSettingsUtil.getConfig().set("last-event", "Brak");
        world.setThunderDuration(0);
        world.setThundering(false);

        for (Player player : Bukkit.getOnlinePlayers()){
            for (BaseAdvancement adv : RimAdvancements.allAdvancements){
                adv.revoke(player);
            }
        }


        PlayerFunctions.updateAllPlayersBoard();
    }
}

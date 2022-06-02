package me.smajt.rimcraft;

import com.google.errorprone.annotations.Var;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.smajt.rimcraft.Advancements.RimAdvancements;
import me.smajt.rimcraft.Commands.RRBookCommand;
import me.smajt.rimcraft.Functions.GMFunctions;
import me.smajt.rimcraft.Functions.PlayerFunctions;
import me.smajt.rimcraft.Menus.RRMainMenu;
import me.smajt.rimcraft.Models.*;
import me.smajt.rimcraft.Schedulers.AirDropScheduler;
import me.smajt.rimcraft.Schedulers.ItemCDScheduler;
import me.smajt.rimcraft.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class MyListener implements Listener {

    FileConfiguration config = Rimcraft.getPlugin().getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(!Objects.isNull(config.getString("resourceURL"))){
            player.setResourcePack(Objects.requireNonNull(config.getString("resourceURL")), Objects.requireNonNull(config.getString("resourceHash")), config.getBoolean("resourceRequired"));
        }
        PlayerFunctions.initPlayer(player);
    }

    @EventHandler
    public void onAcceptResource(PlayerResourcePackStatusEvent e){
        Player player = e.getPlayer();
        if(e.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED || e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
            player.showTitle(Title.title(Component.text(ChatColor.translateAlternateColorCodes('&', "&7&l- &c&lRimCraft &7&l-")), Component.text("")));
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        User userData = UserStorageUtil.findUser(player.getUniqueId());
        TempUser tempUserData = TempUserStorageUtil.findUser(player.getUniqueId());
        if(tempUserData != null){
            tempUserData.setBodyTemp(36.6);
            if (userData != null) {
                userData.setThirst(20);
            }
        }
        else{
            TempUserStorageUtil.createUser(player);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        try {
            UserStorageUtil.saveUsers();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void OnConsume(PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();

        TempUser tempUserData = TempUserStorageUtil.findUser(player.getUniqueId());
        User user = UserStorageUtil.findUser(player.getUniqueId());

        if(itemStack.getType().equals(Material.POTION)){

            PotionType potionType = ((PotionMeta)itemStack.getItemMeta()).getBasePotionData().getType();
            if(potionType != PotionType.WATER){
                return;
            }

            if(UserStorageUtil.findUser(player.getUniqueId()) != null){

                Biome playerbiome = BiomesUtil.findBiome(player.getWorld().getBiome(player.getLocation()).name());
                if (playerbiome.getTargetBodyTemp() > 36 && tempUserData.getBodyTemp() > playerbiome.getTargetBodyTemp() - 2){
                    tempUserData.setBodyTemp(tempUserData.getBodyTemp() - 0.35);
                }

                user.setThirst(user.getThirst() + 2);
                if(user.getThirst() > 20){
                    user.setThirst(20);
                }
            }
        }
        else if (itemStack.getType().equals(Material.MELON_SLICE)){
            if(UserStorageUtil.findUser(player.getUniqueId()) != null){

                Biome playerbiome = BiomesUtil.findBiome(player.getWorld().getBiome(player.getLocation()).name());
                if (playerbiome.getTargetBodyTemp() > 36 && tempUserData.getBodyTemp() > playerbiome.getTargetBodyTemp() - 2){
                    tempUserData.setBodyTemp(tempUserData.getBodyTemp() - 0.35);
                }

                user.setThirst(user.getThirst() + 4);
                if(user.getThirst() > 20){
                    user.setThirst(20);
                }
            }
        }
        else if (itemStack.getType().equals(Material.MILK_BUCKET)){
            if(tempUserData.getBodyTemp() >= 40){
                e.setCancelled(true);
            }
            else if(tempUserData.getBodyTemp() >= 37 && 40 > tempUserData.getBodyTemp()){
                e.setCancelled(false);
            }
            else if(tempUserData.getBodyTemp() < 37 && 33 <= tempUserData.getBodyTemp()){
                e.setCancelled(true);
            }
            else if(34 > tempUserData.getBodyTemp()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if(block.getType() == Material.WHEAT ||
                block.getType() == Material.BEETROOTS ||
                block.getType() == Material.CARROTS ||
                block.getType() == Material.POTATOES ||
                block.getType() == Material.MELON_STEM ||
                block.getType() == Material.PUMPKIN_STEM ||
                block.getType() == Material.SUGAR_CANE ||
                block.getType() == Material.CACTUS ||
                block.getType() == Material.BAMBOO_SAPLING ||
                block.getType() == Material.SWEET_BERRY_BUSH ||
                block.getType() == Material.COCOA){
            PlantStorageUtil.createPlant(block);
        }
        else if(block.getType() == Material.CAMPFIRE ||
                block.getType() == Material.SOUL_CAMPFIRE ||
                block.getType() == Material.FURNACE ||
                block.getType() == Material.BLAST_FURNACE ||
                block.getType() == Material.TORCH ||
                block.getType() == Material.SMOKER ){
            HeatSourceStorageUtil.createHeatSource(block);
        }
    }

    @EventHandler
    public void PreDeathEvent(PlayerDeathEvent e){
        if(TempUserStorageUtil.findUser(e.getPlayer().getUniqueId()) != null){
            e.setCancelled(true);
            TempUser tempUser = TempUserStorageUtil.findUser(e.getPlayer().getUniqueId());
            if(!tempUser.isDowned()){
                tempUser.setDowned(true);
                PlayerFunctions.downPlayer(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        Entity entity = e.getEntity();
        if (entity instanceof Player){
            Player player = (Player) e.getEntity();
            TempUser tempUser = TempUserStorageUtil.findUser(player.getUniqueId());
            if (tempUser != null && tempUser.isDowned()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void tryingToMove(PlayerMoveEvent e ){
        if(TempUserStorageUtil.findUser(e.getPlayer().getUniqueId()) != null){
            TempUser tempUser = TempUserStorageUtil.findUser(e.getPlayer().getUniqueId());
            if(tempUser.isDowned()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void tryingToMove(PlayerToggleSneakEvent e ){
        Player player = e.getPlayer();
        if(TempUserStorageUtil.findUser(e.getPlayer().getUniqueId()) != null){
            TempUser tempUser = TempUserStorageUtil.findUser(e.getPlayer().getUniqueId());
            if(!tempUser.isDowned()){
                for (Player p : Rimcraft.getPlugin().getServer().getOnlinePlayers()){
                    TempUser tempUserDowned = TempUserStorageUtil.findUser(p.getUniqueId());
                    if(tempUserDowned != null){
                        if(p.getLocation().distance(player.getLocation()) < 5){
                            if(tempUserDowned.isDowned()){
                                tempUserDowned.setDowned(false);
                                p.showTitle(Title.title(Component.text(ChatColor.translateAlternateColorCodes('&', "&a&lZostałeś uratowany !")), Component.text("Gratuluję.")));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();

        ArrayList<Plant> plants = PlantStorageUtil.getAllPlants();
        Plant plantToDestroy = plants.stream().filter(x -> x.getX() == block.getX() && x.getY() == block.getY() && x.getZ() == block.getZ()).findFirst().orElse(null);
        if (plantToDestroy != null){
            PlantStorageUtil.deletePlant(plantToDestroy.getX(), plantToDestroy.getY(), plantToDestroy.getZ());
        }

        ArrayList<HeatSource> heatSources = HeatSourceStorageUtil.getAllHeatSources();
        HeatSource heatSourceToDestroy = heatSources.stream().filter(x -> x.getX() == block.getX() && x.getY() == block.getY() && x.getZ() == block.getZ()).findFirst().orElse(null);
        if (heatSourceToDestroy != null){
            HeatSourceStorageUtil.deleteHeatSource(heatSourceToDestroy.getX(), heatSourceToDestroy.getY(), heatSourceToDestroy.getZ());
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block block : e.blockList()){
            if(HeatSourceStorageUtil.findHeatSource(block.getX(), block.getY(), block.getZ()) != null){
                HeatSourceStorageUtil.deleteHeatSource(block.getX(), block.getY(), block.getZ());
            }
            else if(PlantStorageUtil.findPlant(block.getX(), block.getY(), block.getZ()) != null){
                PlantStorageUtil.deletePlant(block.getX(), block.getY(), block.getZ());
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        e.getDrops().removeIf(item -> item.getType() == Material.TOTEM_OF_UNDYING);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e){
        Event isBloodMoon = EventsUtil.findEvent(GMFunctions.Events.BLOODMOON);
        Entity entity = e.getEntity();

        if(entity instanceof Zombie)
            ((Zombie) entity).setCanBreakDoors(true);

        if(isBloodMoon != null){
            if(entity instanceof Monster){
                switch (entity.getType()){
                    case ZOMBIE:
                        ((Monster) entity).addPotionEffect(PotionEffectType.SPEED.createEffect(9999, 1));
                        ((Monster) entity).addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(9999, 1));
                        ((Monster) entity).addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(9999, 1));
                        break;
                    case SPIDER:
                        ((Monster) entity).addPotionEffect(PotionEffectType.SPEED.createEffect(9999, 2));
                        ((Monster) entity).addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(9999, 1));
                        break;
                    case STRAY:
                    case SKELETON:
                        ((Monster) entity).addPotionEffect(PotionEffectType.SPEED.createEffect(9999, 1));
                        ((Monster) entity).addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(9999, 1));
                        break;
                    case CREEPER:
                        ((Creeper) entity).setPowered(true);
                        ((Monster) entity).addPotionEffect(PotionEffectType.SPEED.createEffect(9999, 1));
                        break;
                }

            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws MenuManagerException, MenuManagerNotSetupException {
        Player p = e.getPlayer();

        if(TempUserStorageUtil.findUser(e.getPlayer().getUniqueId()) != null){
            TempUser tempUser = TempUserStorageUtil.findUser(e.getPlayer().getUniqueId());
            if(tempUser.isDowned()){
                e.setCancelled(true);
                return;
            }
        }

        if(e.getItem() != null){
            if(Objects.requireNonNull(e.getItem()).getItemMeta().getPersistentDataContainer().has(RRBookCommand.rrBookKey)) {
                MenuManager.openMenu(RRMainMenu.class, p);
            }
            if(e.getItem().getItemMeta().getPersistentDataContainer().has(ItemManager.BandageKey)){
                ItemStack bandage = e.getItem();
                if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(ItemCDUtil.findCooldown(p, ItemManager.Bandage) == null){
                        if(p.getHealth() < 20){
                            e.getItem().setAmount(bandage.getAmount() - 1);

                            if(p.getHealth() + 3 >= 20){
                                p.setHealth(20);
                            }
                            else
                            {
                                p.setHealth(p.getHealth() + 3);
                            }

                            p.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&a+ 3 HP")));
                            BukkitTask task = new ItemCDScheduler(Rimcraft.getPlugin(), p, ItemManager.Bandage).runTaskLater(Rimcraft.getPlugin(), 60L);
                        }
                    }
                }
            }
            else if(e.getItem().getItemMeta().getPersistentDataContainer().has(ItemManager.MedkitKey)){
                ItemStack medkit = e.getItem();
                if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(ItemCDUtil.findCooldown(p, ItemManager.Medkit) == null){
                        if(p.getHealth() < 20){
                            e.getItem().setAmount(medkit.getAmount() - 1);

                            if(p.getHealth() + 15 >= 20){
                                p.setHealth(20);
                            }
                            else
                            {
                                p.setHealth(p.getHealth() + 15);
                            }

                            p.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&a+ 15 HP")));
                            BukkitTask task = new ItemCDScheduler(Rimcraft.getPlugin(), p, ItemManager.Medkit).runTaskLater(Rimcraft.getPlugin(), 600L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        Block block = e.getBlock();
        if(HeatSourceStorageUtil.findHeatSource(block.getX(), block.getY(), block.getZ()) != null){
            HeatSourceStorageUtil.deleteHeatSource(block.getX(), block.getY(), block.getZ());
        }
        else if(PlantStorageUtil.findPlant(block.getX(), block.getY(), block.getZ()) != null){
            PlantStorageUtil.deletePlant(block.getX(), block.getY(), block.getZ());
        }
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        int percentage = 5;
        if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)){
            int fortuneLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            switch (fortuneLevel){
                case 1:
                    percentage = 4;
                    break;
                case 2:
                    percentage = 3;
                    break;
                case 3:
                    percentage = 2;
                    break;
            }
        }

        int random = PlayerFunctions.getRandomInt(1, percentage);

        if (block.getDrops(player.getInventory().getItemInMainHand()).isEmpty()) return;

        if(block.getType().equals(Material.COAL_ORE) || block.getType().equals(Material.DEEPSLATE_COAL_ORE)){
            e.setCancelled(true);
            block.setType(Material.AIR);

            if(random == 1){
                ItemStack coal = new ItemStack(Material.COAL, PlayerFunctions.getRandomInt(1, 5));
                player.getWorld().dropItemNaturally(block.getLocation(), coal);
            }
        }
        else if(block.getType().equals(Material.IRON_ORE) || block.getType().equals(Material.DEEPSLATE_IRON_ORE)){
            e.setCancelled(true);
            block.setType(Material.AIR);

            if(random == 1) {
                int value = PlayerFunctions.getRandomInt(1, 3);
                ItemStack iron = new ItemStack(Material.RAW_IRON, value);
                player.getWorld().dropItemNaturally(block.getLocation(), iron);
                if(!RimAdvancements.gather_3.isGranted(player) && RimAdvancements.gather_2.isGranted(player))
                    RimAdvancements.gather_3.incrementProgression(player, value);
            }
        }
        else if(block.getType().equals(Material.COPPER_ORE) || block.getType().equals(Material.DEEPSLATE_COPPER_ORE)){
            e.setCancelled(true);
            block.setType(Material.AIR);

            if(random == 1) {
                ItemStack copper = new ItemStack(Material.RAW_COPPER, PlayerFunctions.getRandomInt(1, 4));
                player.getWorld().dropItemNaturally(block.getLocation(), copper);
            }
        }
        else if(block.getType().equals(Material.GOLD_ORE) || block.getType().equals(Material.DEEPSLATE_GOLD_ORE)){
            e.setCancelled(true);
            block.setType(Material.AIR);

            if(random == 1) {
                ItemStack gold = new ItemStack(Material.RAW_GOLD, PlayerFunctions.getRandomInt(1, 3));
                player.getWorld().dropItemNaturally(block.getLocation(), gold);
            }
        }
        else if(block.getType().equals(Material.DIAMOND_ORE) || block.getType().equals(Material.DEEPSLATE_DIAMOND_ORE)){
            e.setCancelled(true);
            block.setType(Material.AIR);

            if(random == 1) {
                ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
                player.getWorld().dropItemNaturally(block.getLocation(), diamond);
            }
        }
    }
}

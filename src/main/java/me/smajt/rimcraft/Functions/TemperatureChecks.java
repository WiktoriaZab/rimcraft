package me.smajt.rimcraft.Functions;

import me.smajt.rimcraft.Advancements.RimAdvancements;
import me.smajt.rimcraft.Models.Biome;
import me.smajt.rimcraft.Models.HeatSource;
import me.smajt.rimcraft.Models.TempUser;
import me.smajt.rimcraft.Models.User;
import me.smajt.rimcraft.Utils.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TemperatureChecks {

    public static void refreshTemperature(Player p){
        Random r = new Random();
        TempUser tempUserData = TempUserStorageUtil.findUser(p.getUniqueId());
        ArrayList<HeatSource> heatSources = HeatSourceStorageUtil.getAllHeatSources();

        if(tempUserData != null && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            double playerTemp = tempUserData.getBodyTemp();
            Biome playerBiome = BiomesUtil.findBiome(p.getWorld().getBiome(p.getLocation()).name());
            double targetBiomeTemp = Objects.requireNonNull(playerBiome).getTargetBodyTemp();

            if(EventsUtil.findEvent(GMFunctions.Events.HEATWAVE) != null){
                targetBiomeTemp += 3;
            }
            else if(EventsUtil.findEvent(GMFunctions.Events.COLDWAVE) != null){
                targetBiomeTemp -= 3;
            }

            double roznica = (0.4 + (0.8 - 0.4) * r.nextDouble());
            if (!p.getWorld().getName().endsWith("_nether") && !p.getWorld().getName().endsWith("_the_end")){
                if (p.getWorld().getTime() > 12300){
                    if (playerTemp > targetBiomeTemp - 2){
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                    else if (playerTemp < targetBiomeTemp - 2){
                        tempUserData.setBodyTemp(playerTemp + roznica);
                    }
                    else{
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                }
                else{
                    if (playerTemp > targetBiomeTemp){
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                    else if (playerTemp < targetBiomeTemp){
                        tempUserData.setBodyTemp(playerTemp + roznica);
                    }
                    else{
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                }
            }
            else{
                if (playerTemp > targetBiomeTemp){
                    tempUserData.setBodyTemp(playerTemp - roznica);
                }
                else if (playerTemp < targetBiomeTemp){
                    tempUserData.setBodyTemp(playerTemp + roznica);
                }
                else{
                    tempUserData.setBodyTemp(playerTemp - roznica);
                }
            }

            playerTemp = tempUserData.getBodyTemp();
            roznica = (0.5 + (1.8 - 0.5) * r.nextDouble());
            if (p.getLocation().getBlockY() <= 40 && playerTemp < 33){
                tempUserData.setBodyTemp(playerTemp - roznica);
            }
            else if (p.getLocation().getBlockY() >= 115 && playerTemp < 33){
                tempUserData.setBodyTemp(playerTemp - roznica);
            }

            roznica = (0.8 + (2.4 - 0.8) * r.nextDouble());
            playerTemp = tempUserData.getBodyTemp();
            for(HeatSource heatSource : heatSources){
                Block heatSourceBlock = p.getWorld().getBlockAt(heatSource.getX(), heatSource.getY(), heatSource.getZ());
                if(p.getLocation().distance(heatSourceBlock.getLocation()) <= 5){
                    if (heatSourceBlock.getBlockData().getAsString().contains("lit=true")){
                        if (playerTemp < targetBiomeTemp + 3)
                            tempUserData.setBodyTemp(playerTemp + roznica);
                    }
                }
            }

            playerTemp = tempUserData.getBodyTemp();
            EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};


            for (EquipmentSlot slot : slots){
                switch (p.getInventory().getItem(slot).getType()){
                    case LEATHER_HELMET:
                    case LEATHER_BOOTS:
                        if(playerBiome.getTargetBodyTemp() + 2 > playerTemp)
                            tempUserData.setBodyTemp(playerTemp + 0.5);
                        break;
                    case LEATHER_CHESTPLATE:
                        if(playerBiome.getTargetBodyTemp() + 2 > playerTemp)
                            tempUserData.setBodyTemp(playerTemp + 1.5);
                        break;
                    case LEATHER_LEGGINGS:
                        if(playerBiome.getTargetBodyTemp() + 2 > playerTemp)
                            tempUserData.setBodyTemp(playerTemp + 1);
                        break;
                }
            }

            playerTemp = tempUserData.getBodyTemp();
            if(p.isInWater()){
                if(targetBiomeTemp < 36){
                    roznica = (0.5 + (1.6 - 0.5) * r.nextDouble());
                    tempUserData.setBodyTemp(playerTemp - roznica);
                }
                else if (targetBiomeTemp >= 36){
                    roznica = (0.5 + (0.8 - 0.5) * r.nextDouble());
                    tempUserData.setBodyTemp(playerTemp - roznica);
                }
            }

            playerTemp = tempUserData.getBodyTemp();
            if(p.isInWater()){
                if(targetBiomeTemp < 36){
                    roznica = (0.5 + (1.6 - 0.5) * r.nextDouble());
                    tempUserData.setBodyTemp(playerTemp - roznica);
                }
                else if (targetBiomeTemp >= 36){
                    roznica = (0.5 + (0.8 - 0.5) * r.nextDouble());
                    tempUserData.setBodyTemp(playerTemp - roznica);
                }
            }

            playerTemp = tempUserData.getBodyTemp();
            if(p.getWorld().hasStorm()){
                if(p.getWorld().isThundering()){
                    if(targetBiomeTemp < 36){
                        roznica = (0.5 + (1.6 - 0.5) * r.nextDouble());
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                    else if (targetBiomeTemp >= 36){
                        roznica = (0.3 + (0.8 - 0.3) * r.nextDouble());
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                }
                else{
                    if(targetBiomeTemp < 36){
                        roznica = (0.5 + (1.2 - 0.5) * r.nextDouble());
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                    else if (targetBiomeTemp >= 36){
                        roznica = (0.3 + (0.8 - 0.3) * r.nextDouble());
                        tempUserData.setBodyTemp(playerTemp - roznica);
                    }
                }
            }

            applyEffects(p);
        }
    }

    public static void applyEffects(Player p){
        TempUser tempUserData = TempUserStorageUtil.findUser(p.getUniqueId());

        if(tempUserData.getBodyTemp() > 40){
            if(!RimAdvancements.veteran_2.isGranted(p) && RimAdvancements.veteran_1.isGranted(p))
                RimAdvancements.veteran_2.grant(p, true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2400, 2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 2400, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2400, 1));
            p.damage(3);
        }
        else if(33 > tempUserData.getBodyTemp()){
            if(!RimAdvancements.veteran_1.isGranted(p))
                RimAdvancements.veteran_1.grant(p, true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2400, 2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 2400, 1));
            p.setFreezeTicks(2400);
            p.damage(1);
        }
        else{
            for (PotionEffect effect : p.getActivePotionEffects())
                if(effect.getType() == PotionEffectType.SLOW || effect.getType() == PotionEffectType.WEAKNESS || effect.getType() == PotionEffectType.CONFUSION)
                    p.removePotionEffect(effect.getType());

            p.setFreezeTicks(0);
        }
    }
}

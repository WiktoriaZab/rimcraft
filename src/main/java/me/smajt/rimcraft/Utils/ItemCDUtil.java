package me.smajt.rimcraft.Utils;

import me.smajt.rimcraft.Functions.GMFunctions;
import me.smajt.rimcraft.Models.Event;
import me.smajt.rimcraft.Models.ItemCD;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemCDUtil {
    private static final ArrayList<ItemCD> itemCDS = new ArrayList<>();

    public static ItemCD createCooldown(Player player, ItemStack item){
        ItemCD itemCD = new ItemCD(player.getUniqueId(), item);
        itemCDS.add(itemCD);

        return itemCD;
    }

    public static ItemCD findCooldown(Player player, ItemStack item){
        for (ItemCD itemCD : itemCDS){
            if (itemCD.getPlayerUuid() == player.getUniqueId() && itemCD.getItemStack() == item) {
                return itemCD;
            }
        }
        return null;
    }

    public static void deleteCooldown(Player player, ItemStack item){
        for (ItemCD itemCD : itemCDS){
            if (itemCD.getPlayerUuid() == player.getUniqueId() && itemCD.getItemStack() == item) {
                itemCDS.remove(itemCD);
                break;
            }
        }
    }
}

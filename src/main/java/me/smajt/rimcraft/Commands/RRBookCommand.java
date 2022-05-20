package me.smajt.rimcraft.Commands;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.smajt.rimcraft.Menus.RRMainMenu;
import me.smajt.rimcraft.Rimcraft;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class RRBookCommand implements CommandExecutor {
    public static NamespacedKey rrBookKey = new NamespacedKey(Rimcraft.getPlugin(), "rrbook");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ItemStack rrBook = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta newItemMeta = rrBook.getItemMeta();
            newItemMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&4&lRandom Randy's Book")));
            newItemMeta.getPersistentDataContainer().set(rrBookKey, PersistentDataType.INTEGER, 1);
            rrBook.setItemMeta(newItemMeta);
            ((Player) sender).getInventory().addItem(rrBook);
        }
        return true;
    }
}

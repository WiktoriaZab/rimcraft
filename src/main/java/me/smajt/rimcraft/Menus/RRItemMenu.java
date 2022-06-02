package me.smajt.rimcraft.Menus;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.smajt.rimcraft.Functions.GMFunctions;
import me.smajt.rimcraft.ItemManager;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class RRItemMenu extends Menu {
    public RRItemMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ColorTranslator.translateColorCodes("&d&lItem Giver");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        ItemStack currentItem = e.getCurrentItem();
        if (ItemManager.Esencja_Roslinna.getItemMeta().equals(currentItem.getItemMeta())) {
            p.getInventory().addItem(ItemManager.Esencja_Roslinna);
        } else if (ItemManager.Bandage.getItemMeta().equals(currentItem.getItemMeta())) {
            p.getInventory().addItem(ItemManager.Bandage);
        } else if (ItemManager.Medkit.getItemMeta().equals(currentItem.getItemMeta())) {
            p.getInventory().addItem(ItemManager.Medkit);
        } else if(e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE){
            MenuManager.openMenu(RRMainMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack esencja = ItemManager.Esencja_Roslinna;
        inventory.setItem(0, esencja);
        ItemStack bandaz = ItemManager.Bandage;
        inventory.setItem(1, bandaz);
        ItemStack medkit = ItemManager.Medkit;
        inventory.setItem(2, medkit);

        ItemStack back = makeItem(Material.RED_STAINED_GLASS_PANE, ColorTranslator.translateColorCodes("&4&lCofnij"));
        inventory.setItem(getSlots() - 1, back);
    }
}

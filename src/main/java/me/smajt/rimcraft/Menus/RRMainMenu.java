package me.smajt.rimcraft.Menus;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.smajt.rimcraft.Functions.GMFunctions;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RRMainMenu extends Menu {
    public RRMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ColorTranslator.translateColorCodes("&4&lRandom Randy's Menu");
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        switch (e.getCurrentItem().getType()) {
            case LAVA_BUCKET:
                MenuManager.openMenu(RREventMenu.class, playerMenuUtility.getOwner());
                break;
            case MUSIC_DISC_FAR:
                GMFunctions.ResetTheGame(playerMenuUtility.getOwner());
                break;
            case GOLDEN_APPLE:
                MenuManager.openMenu(RRItemMenu.class, playerMenuUtility.getOwner());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack eventMenu = makeItem(Material.LAVA_BUCKET, ColorTranslator.translateColorCodes("&e&lEvents Menu"));
        inventory.setItem(3, eventMenu);
        ItemStack settingsMenu = makeItem(Material.MUSIC_DISC_FAR, ColorTranslator.translateColorCodes("&a&lZresetuj Gre"));
        inventory.setItem(4, settingsMenu);
        ItemStack itemGiver = makeItem(Material.GOLDEN_APPLE, ColorTranslator.translateColorCodes("&d&lItem Giver"));
        inventory.setItem(5, itemGiver);
    }
}

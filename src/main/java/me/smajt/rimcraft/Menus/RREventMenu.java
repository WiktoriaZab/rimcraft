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

public class RREventMenu  extends Menu {
    public RREventMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ColorTranslator.translateColorCodes("&e&lEvents Menu");
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
        switch (e.getCurrentItem().getType()) {
            case DEAD_BUSH:
                GMFunctions.DroughtEvent(playerMenuUtility.getOwner());
                break;
            case WITHER_SKELETON_SKULL:
                GMFunctions.MalariaEvent(playerMenuUtility.getOwner());
                break;
            case ROTTEN_FLESH:
                GMFunctions.StarvationEvent();
                break;
            case FIREWORK_ROCKET:
                GMFunctions.ThunderEvent(playerMenuUtility.getOwner());
                break;
            case REDSTONE:
                GMFunctions.BloodMoonEvent(playerMenuUtility.getOwner());
                break;
            case ZOMBIE_SPAWN_EGG:
                GMFunctions.RandomEntitiesEvent(playerMenuUtility.getOwner());
                break;
            case PILLAGER_SPAWN_EGG:
                GMFunctions.RaidEvent(playerMenuUtility.getOwner());
                break;
            case CHEST:
                GMFunctions.AirDropEvent(playerMenuUtility.getOwner());
                break;
            case BLUE_ICE:
                GMFunctions.ColdWaveEvent();
                break;
            case LAVA_BUCKET:
                GMFunctions.HeatWaveEvent();
                break;
            case SPLASH_POTION:
                GMFunctions.PassionEvent();
                break;
            case RED_STAINED_GLASS_PANE:
                MenuManager.openMenu(RRMainMenu.class, playerMenuUtility.getOwner());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack drought = makeItem(Material.DEAD_BUSH, ColorTranslator.translateColorCodes("&e&lSusza"));
        inventory.setItem(0, drought);
        ItemStack malaria = makeItem(Material.WITHER_SKELETON_SKULL, ColorTranslator.translateColorCodes("&e&lMalaria"));
        inventory.setItem(1, malaria);
        ItemStack starvation = makeItem(Material.ROTTEN_FLESH, ColorTranslator.translateColorCodes("&e&lZatrucie Pokarmowe"));
        inventory.setItem(2, starvation);
        ItemStack thunder = makeItem(Material.FIREWORK_ROCKET, ColorTranslator.translateColorCodes("&e&lBurza z piorunami"));
        inventory.setItem(3, thunder);
        ItemStack bloodmoon = makeItem(Material.REDSTONE, ColorTranslator.translateColorCodes("&e&lBlood Moon"));
        inventory.setItem(4, bloodmoon);
        ItemStack randomGroup = makeItem(Material.ZOMBIE_SPAWN_EGG, ColorTranslator.translateColorCodes("&e&lLosowa Grupa"));
        inventory.setItem(5, randomGroup);
        ItemStack raid = makeItem(Material.PILLAGER_SPAWN_EGG, ColorTranslator.translateColorCodes("&e&lRaid"));
        inventory.setItem(6, raid);
        ItemStack airdrop = makeItem(Material.CHEST, ColorTranslator.translateColorCodes("&e&lKapsuła"));
        inventory.setItem(7, airdrop);
        ItemStack coldwave = makeItem(Material.BLUE_ICE, ColorTranslator.translateColorCodes("&e&lFala Chłodu"));
        inventory.setItem(8, coldwave);
        ItemStack heatwave = makeItem(Material.LAVA_BUCKET, ColorTranslator.translateColorCodes("&e&lFala Ciepła"));
        inventory.setItem(9, heatwave);
        ItemStack passion = makeItem(Material.SPLASH_POTION, ColorTranslator.translateColorCodes("&e&lPasja"));
        inventory.setItem(10, passion);



        ItemStack back = makeItem(Material.RED_STAINED_GLASS_PANE, ColorTranslator.translateColorCodes("&4&lCofnij"));
        inventory.setItem(getSlots() - 1, back);
    }
}

package me.smajt.rimcraft.Advancements.adv.Gatherer;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.visibilities.VanillaVisibility;
import me.smajt.rimcraft.Advancements.RimAdvancements;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Gatherer_1 extends BaseAdvancement implements VanillaVisibility{
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "gatherer_1");

    public Gatherer_1(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.OAK_LOG, "Zbieracz 1", AdvancementFrameType.TASK, true, true, 1f, 0f , "Zbierz 6 pieńków jakiegokolwiek drewna."), parent, 6);

        registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player)) {
                if(e.getBlock().getType() == Material.ACACIA_LOG || e.getBlock().getType() == Material.BIRCH_LOG || e.getBlock().getType() == Material.DARK_OAK_LOG || e.getBlock().getType() == Material.JUNGLE_LOG || e.getBlock().getType() == Material.OAK_LOG || e.getBlock().getType() == Material.SPRUCE_LOG)
                    incrementProgression(player);
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.STICK, 5));
        player.getInventory().addItem(new ItemStack(Material.APPLE, 3));
    }
}

package me.smajt.rimcraft.Advancements.adv.Farmer;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import com.fren_gor.ultimateAdvancementAPI.visibilities.VanillaVisibility;
import me.smajt.rimcraft.Advancements.RimAdvancements;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Farmerer_2 extends BaseAdvancement implements ParentGrantedVisibility {
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "farmerer_2");

    public Farmerer_2(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.WHEAT, "Farmer 2", AdvancementFrameType.TASK, true, true, 2f, 1f , "Zasadź i zbierz stos pszenicy."), parent, 64);

        registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player)) {
                if(e.getBlock().getType() == Material.WHEAT && e.getBlock().getBlockData().getAsString().contains("age=7"))
                    incrementProgression(player);
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.HAY_BLOCK, 12));
        player.getInventory().addItem(new ItemStack(Material.POTATO, 1));
    }
}

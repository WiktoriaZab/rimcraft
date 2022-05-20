package me.smajt.rimcraft.Advancements.adv.Farmer;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import me.smajt.rimcraft.Advancements.RimAdvancements;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Farmerer_3 extends BaseAdvancement implements ParentGrantedVisibility {
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "farmerer_3");

    public Farmerer_3(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.POTATO, "Farmer 3", AdvancementFrameType.TASK, true, true, 3f, 1f , "Zasadź i zbierz 24 ziemniaki."), parent, 24);

        registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player)) {
                if(e.getBlock().getType() == Material.POTATOES && e.getBlock().getBlockData().getAsString().contains("age=7"))
                    incrementProgression(player);
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.CARROT, 1));
    }
}

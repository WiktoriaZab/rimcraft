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

public class Farmerer_4 extends BaseAdvancement implements ParentGrantedVisibility {
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "farmerer_4");

    public Farmerer_4(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.CARROT, "Farmer 4", AdvancementFrameType.TASK, true, true, 4f, 1f , "Zasadź i zbierz 32 marchewki."), parent, 32);

        registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player)) {
                if(e.getBlock().getType() == Material.CARROTS && e.getBlock().getBlockData().getAsString().contains("age=7"))
                    incrementProgression(player);
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 3));
        player.getInventory().addItem(new ItemStack(Material.ENDER_EYE, 1));
    }
}

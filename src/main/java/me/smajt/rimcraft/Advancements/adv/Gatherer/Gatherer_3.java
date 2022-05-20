package me.smajt.rimcraft.Advancements.adv.Gatherer;

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

public class Gatherer_3 extends BaseAdvancement implements ParentGrantedVisibility {
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "gatherer_3");

    public Gatherer_3(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.RAW_IRON, "Zbieracz 3", AdvancementFrameType.TASK, true, true, 2f, 0f , "Zbierz 10 sztuk surowego żelaza."), parent, 10);

        /*registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player)) {
                if(e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.COBBLESTONE)
                    incrementProgression(player);
            }
        });*/
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.BONE, 5));
    }
}

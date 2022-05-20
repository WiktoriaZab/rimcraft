package me.smajt.rimcraft.Advancements.adv.Adventurer;

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

public class Adventurer_1 extends BaseAdvancement implements VanillaVisibility{
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "adventurer_1");

    public Adventurer_1(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.REDSTONE, "Blood Moon", AdvancementFrameType.CHALLENGE, true, true, 1f, 3f , "Doświadcz i przeżyj Blood Moon."), parent, 1);

        /*registerEvent(BlockBreakEvent.class, e -> {
            Player player = e.getPlayer();
            if (isVisible(player)) {
                if(e.getBlock().getType() == Material.WHEAT && e.getBlock().getBlockData().getAsString().contains("age=7"))
                    incrementProgression(player);
            }
        });*/
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
    }
}

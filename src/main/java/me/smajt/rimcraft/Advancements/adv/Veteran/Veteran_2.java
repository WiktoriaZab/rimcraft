package me.smajt.rimcraft.Advancements.adv.Veteran;

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
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Veteran_2 extends BaseAdvancement implements ParentGrantedVisibility {
    public static AdvancementKey KEY = new AdvancementKey(RimAdvancements.rimcraft_NAMESPACE, "veteran_2");

    public Veteran_2(Advancement parent) {
        super(KEY.getKey(), new AdvancementDisplay(Material.FIRE_CHARGE, "Z deszczu pod rynne", AdvancementFrameType.CHALLENGE, true, true, 2f, 2f , "Doświadcz Hyperthermii."), parent, 1);

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
        player.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
        player.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL, 1));
    }
}

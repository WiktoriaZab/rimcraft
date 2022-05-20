package me.smajt.rimcraft.Advancements;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.smajt.rimcraft.Advancements.adv.Adventurer.Adventurer_1;
import me.smajt.rimcraft.Advancements.adv.Farmer.Farmerer_1;
import me.smajt.rimcraft.Advancements.adv.Farmer.Farmerer_2;
import me.smajt.rimcraft.Advancements.adv.Farmer.Farmerer_3;
import me.smajt.rimcraft.Advancements.adv.Farmer.Farmerer_4;
import me.smajt.rimcraft.Advancements.adv.Gatherer.Gatherer_1;
import me.smajt.rimcraft.Advancements.adv.Gatherer.Gatherer_2;
import me.smajt.rimcraft.Advancements.adv.Gatherer.Gatherer_3;
import me.smajt.rimcraft.Advancements.adv.Veteran.Veteran_1;
import me.smajt.rimcraft.Advancements.adv.Veteran.Veteran_2;
import me.smajt.rimcraft.Rimcraft;
import org.bukkit.Material;

import java.util.ArrayList;

public class RimAdvancements{

    public static String rimcraft_NAMESPACE = "rim_craft";
    public static UltimateAdvancementAPI api = UltimateAdvancementAPI.getInstance(Rimcraft.getPlugin());
    public static AdvancementTab tab = api.createAdvancementTab(rimcraft_NAMESPACE);
    public static AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.NETHER_STAR, "The World of RimCraft", AdvancementFrameType.TASK, true, false, 0f, 0f , "Dołącz do świata RimCraft");
    public static RootAdvancement root = new RootAdvancement(tab, "root", rootDisplay, "textures/block/blue_concrete_powder.png");

    //advancements
    public static Gatherer_1 gather_1 = new Gatherer_1(root);
    public static Gatherer_2 gather_2 = new Gatherer_2(gather_1);
    public static Gatherer_3 gather_3 = new Gatherer_3(gather_2);

    public static Farmerer_1 farmerer_1 = new Farmerer_1(root);
    public static Farmerer_2 farmerer_2 = new Farmerer_2(farmerer_1);
    public static Farmerer_3 farmerer_3 = new Farmerer_3(farmerer_2);
    public static Farmerer_4 farmerer_4 = new Farmerer_4(farmerer_3);

    public static Adventurer_1 adventurer_1 = new Adventurer_1(root);

    public static Veteran_1 veteran_1 = new Veteran_1(root);
    public static Veteran_2 veteran_2 = new Veteran_2(veteran_1);

    public static BaseAdvancement[] allAdvancements = {gather_1, gather_2, gather_3, farmerer_1, farmerer_2, farmerer_3, farmerer_4, adventurer_1, veteran_1, veteran_2};
}

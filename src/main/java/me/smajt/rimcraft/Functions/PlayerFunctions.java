package me.smajt.rimcraft.Functions;

import me.smajt.rimcraft.Models.TempUser;
import me.smajt.rimcraft.Models.User;
import me.smajt.rimcraft.Rimcraft;
import me.smajt.rimcraft.Utils.TempUserStorageUtil;
import me.smajt.rimcraft.Utils.UserStorageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class PlayerFunctions {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public static void initPlayer(Player player){
        User userData = UserStorageUtil.findUser(player.getUniqueId());
        if(userData == null){
            UserStorageUtil.createUser(player, 20);
        }

        TempUser tempUserData = TempUserStorageUtil.findUser(player.getUniqueId());
        if (tempUserData == null)
            TempUserStorageUtil.createUser(player);

        PlayerFunctions.updateBoard(player);

        BukkitScheduler scheduler = Rimcraft.getPlugin().getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Rimcraft.getPlugin(), () -> PlayerFunctions.updateActionBar(player), 0L, 20L);
    }

    public static void updateBoard(Player player){
        User userData = UserStorageUtil.findUser(player.getUniqueId());

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("PlayerInfo", "dummy",
                ChatColor.translateAlternateColorCodes('&', "&7&l<< &6&lRim&2&lCraft &7&l>>"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = obj.getScore(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=");
        score.setScore(3);

        String ostatniEvent = Rimcraft.getPlugin().GameSettingsUtil.getConfig().getString("last-event");
        Score lastevent = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&7&l> Ost.Ev.:&d&l ") + ostatniEvent);
        lastevent.setScore(2);

        player.setScoreboard(board);
    }

    public static void updateAllPlayersBoard(){
        for (Player player : Bukkit.getOnlinePlayers()){
            User userData = UserStorageUtil.findUser(player.getUniqueId());

            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective obj = board.registerNewObjective("PlayerInfo", "dummy",
                    ChatColor.translateAlternateColorCodes('&', "&7&l<< &6&lRim&2&lCraft &7&l>>"));
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score = obj.getScore(ChatColor.DARK_GRAY + "=-=-=-=-=-=-=-=-=-=-=");
            score.setScore(3);

            String ostatniEvent = Rimcraft.getPlugin().GameSettingsUtil.getConfig().getString("last-event");
            Score lastevent = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&7&l> Ost.Ev.:&d&l ") + ostatniEvent);
            lastevent.setScore(2);

            player.setScoreboard(board);
        }
    }

    public static void updateActionBar(Player player){
        User userData = UserStorageUtil.findUser(player.getUniqueId());
        TempUser tempUserData = TempUserStorageUtil.findUser(player.getUniqueId());
        String temperatura = " C";

        if(tempUserData.getBodyTemp() >= 40){
            temperatura = "&c&l"+ df.format(tempUserData.getBodyTemp()) +" C";
        }
        else if(tempUserData.getBodyTemp() >= 37 && 40 > tempUserData.getBodyTemp()){
            temperatura = "&6&l"+ df.format(tempUserData.getBodyTemp()) +" C";
        }
        else if(tempUserData.getBodyTemp() < 37 && 33 <= tempUserData.getBodyTemp()){
            temperatura = "&b&l"+ df.format(tempUserData.getBodyTemp()) +" C";
        }
        else if(33 > tempUserData.getBodyTemp()){
            temperatura = "&1&l"+ df.format(tempUserData.getBodyTemp()) +" C";
        }

        player.sendActionBar(Component.text(ChatColor.translateAlternateColorCodes('&', "PLH &7&l<-> " + temperatura + " &7&l<->&r \uEFF5 &b&l") + userData.getThirst()));
    }

    public static int getRandomInt(int a,int b) {
        int min = (int) Math.ceil(a);
        int max = (int) Math.floor(b);
        return (int) (Math.floor(Math.random() * (max - min)) + min);
    }

    public static void downPlayer(Player player){
        player.showTitle(
                Title.title(Component.text(ChatColor.translateAlternateColorCodes('&', "&c&lZostałeś powalony !")), Component.text("Poczekaj aż ktoś cię uratuje."))
        );

    }
}

package com.snat.main.Ranks;

import com.snat.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.snat.main.Utils.color;
import static sun.audio.AudioPlayer.player;

public class RankListener implements Listener {


    private Main main;

    public RankListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        if (!player.hasPlayedBefore()) {

            main.getRankManager().setRank(player.getUniqueId(), Rank.MEMBER);

        }
        main.getNameTagManager().setNameTags(player);
        main.getNameTagManager().newTag(player);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        main.getNameTagManager().removeTag(e.getPlayer());



    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        Bukkit.broadcastMessage(color(main.getRankManager().getRank(player.getUniqueId()).getDisplay() + " &f" + player.getName() + "&8 | ") + ChatColor.GRAY + e.getMessage());




    }
}

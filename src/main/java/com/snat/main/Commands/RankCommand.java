package com.snat.main.Commands;

import com.snat.main.Main;
import com.snat.main.Ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.snat.main.Utils.color;

public class RankCommand implements CommandExecutor {

    //       /rank <player> <rank>

    private Main main;

    public RankCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp()) {

                if (args.length == 2) {
                    if (Bukkit.getOfflinePlayer(args[0]) != null) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        for (Rank rank : Rank.values()) {
                            if (rank.name().equalsIgnoreCase(args[1])) {
                                main.getRankManager().setRank(target.getUniqueId(), rank);

                                player.sendMessage(color("&aYou Changed " + target.getName() +
                                        "'s rank to " + rank.getDisplay() + "&a."));
                                if (target.isOnline()) {
                                    target.getPlayer().sendMessage(color("&a" + player.getName() + " set your rank to " + rank.getDisplay() + "&a."));

                                }

                                return false;
                            }
                        }


                        player.sendMessage(color("&cYou did not specify an actual Rank!"));
                    } else {
                        //Invalid Player
                        player.sendMessage(color("&cThis Player has never joined the server before!"));
                    }
                } else {
                    //Invalid Usage
                    player.sendMessage(color("&cInvalid Usage! Please use &a/rank <player> <rank>"));
                }

            } else {
                //Not Opped
                player.sendMessage(color("&cYou must be opped to use that command!"));
            }
        }
        return false;
    }
}

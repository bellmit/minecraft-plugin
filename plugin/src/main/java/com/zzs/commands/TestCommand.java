package com.zzs.commands;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * @author mm013
 * @Date 2021/6/8 16:14
 */
public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (command.getName().equals("test")) {
            player.getStatistic(Statistic.PLAYER_KILLS);
            player.sendMessage("PLAYER_KILLS：" + player.getStatistic(Statistic.PLAYER_KILLS));
            player.sendMessage("KILL_ENTITY：" + player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE));
        }
        return false;
    }
}

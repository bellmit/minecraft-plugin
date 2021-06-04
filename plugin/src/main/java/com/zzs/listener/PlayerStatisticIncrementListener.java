package com.zzs.listener;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

/**
 * @author mm013
 * @Date 2021/6/4 10:25
 */
public class PlayerStatisticIncrementListener implements Listener {


    @EventHandler
    public void StatisticIncrement(PlayerStatisticIncrementEvent event) {
        if (event.getStatistic() != null) {
            if (event.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                Player player = event.getPlayer();
                int statistic = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                int second = statistic / 20;
                int hour = second / 3600;
                if (hour == 24) {

                }
            }
        }
    }
}

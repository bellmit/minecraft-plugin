package com.zzs.listener;

import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
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
    SqlSession sqlSession = SqlSessionUtil.getSqlSession();
    AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);

    @EventHandler
    public void StatisticIncrement(PlayerStatisticIncrementEvent event) {
        Player player = event.getPlayer();
        if (event.getStatistic() != null) {
            Achievement achievement = achievementMapper.selectById(player.getUniqueId().toString());
//            player.sendMessage(achievement.toString());
            if (!achievement.getIsBeginner()) {
                int statistic = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                int second = statistic / 20;
                int hour = second / 3600;
                if (hour == 24) {
                    achievement.setIsBeginner(Boolean.TRUE);
                    achievementMapper.updateById(achievement);
                    player.sendMessage("§f已获得称号  " + "§a【初学者】");
                }
            }
               /* if (player.getStatistic(Statistic.FISH_CAUGHT) == 1000) {
                    Achievement achievement = this.setParams(player);
                    achievement.setIsFisher(Boolean.TRUE);
                    achievementMapper.updateById(achievement);
                    SqlSessionUtil.closerSqlSession(sqlSession);
                    player.sendMessage("§f已获得称号  " + "§a【渔夫】");
                    return;
                }
                if (player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE) >= 500 && player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE) >= 300) {
                    Achievement achievement = this.setParams(player);
                    achievement.setIsMiner(Boolean.TRUE);
                    achievementMapper.updateById(achievement);
                    SqlSessionUtil.closerSqlSession(sqlSession);
                    player.sendMessage("§f已获得称号  " + "§a【矿工】");
                    return;
                }*/
//            if (!achievement.getIsFarmer()) {
//                player.getStatistic(Statistic.MINE_BLOCK, Material.);
//            }
        }
    }

    private Achievement setParams(Player player) {
        Achievement achievement = new Achievement();
        achievement.setUuid(player.getUniqueId().toString());
        return achievement;
    }
}

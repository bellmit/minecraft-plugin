package com.zzs.listener;

import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Material;
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
        Player player = event.getPlayer();
        if (event.getStatistic() != null) {
            int timeCount = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
            //当玩家游戏时间达到24h。游戏中每20/s
            if (timeCount == 1728000) {
                this.updateAchievementStatus(player, "【初学者】");
                return;
            }

            //钓鱼获得次数
            int fishCount = player.getStatistic(Statistic.FISH_CAUGHT);
            if (fishCount == 1) {
                fishCount += 1;
                //更改变量防止重复运行该代码造成数据库连接过多
                player.setStatistic(Statistic.FISH_CAUGHT, fishCount);
                this.updateAchievementStatus(player, "【渔夫】");
                return;
            }

            this.isAccomplishMiner(player);

            this.isAccomplishFarmer(player);

        }
    }

    /**
     * 判断是否达成农夫称号
     * 小麦*500 && 西瓜*600 && 南瓜**300
     *
     * @param player
     */
    private void isAccomplishFarmer(Player player) {
        //小麦采集次数
        int wheatCount = player.getStatistic(Statistic.MINE_BLOCK, Material.WHEAT);
        //西瓜采集次数
        int melonCount = player.getStatistic(Statistic.MINE_BLOCK, Material.MELON);
        //南瓜采集次数
        int pumpkinCount = player.getStatistic(Statistic.MINE_BLOCK, Material.PUMPKIN);
        if (!(wheatCount < 500) && !(melonCount < 600) && !(pumpkinCount < 300)) {
            if (wheatCount >= 500 && melonCount == 600 && pumpkinCount == 300) {
                if (wheatCount == 500) {
                    wheatCount++;
                }
                melonCount++;
                pumpkinCount++;
            } else if (wheatCount > 500 && melonCount == 600 & pumpkinCount == 300) {
                melonCount++;
                pumpkinCount++;
            } else if (wheatCount > 500 && melonCount > 600 & pumpkinCount == 300) {
                pumpkinCount++;
            } else if (wheatCount > 500 && melonCount == 600 & pumpkinCount > 300) {
                melonCount++;
            } else if (wheatCount == 500 && melonCount > 600 & pumpkinCount == 300) {
                wheatCount++;
                pumpkinCount++;
            } else if (wheatCount == 500 && melonCount > 600 & pumpkinCount > 300) {
                wheatCount++;
            } else if (wheatCount == 500 && melonCount == 600 & pumpkinCount > 300) {
                melonCount++;
            }
            player.setStatistic(Statistic.MINE_BLOCK, Material.WHEAT, wheatCount);
            player.setStatistic(Statistic.MINE_BLOCK, Material.MELON, melonCount);
            player.setStatistic(Statistic.MINE_BLOCK, Material.PUMPKIN, pumpkinCount);
            this.updateAchievementStatus(player, "【农夫】");
        }
    }

    /**
     * 判断是否达成矿工称号
     * 铁矿*500，金矿*300
     *
     * @param player 玩家
     */
    private void isAccomplishMiner(Player player) {
        //开采铁矿次数
        int ironOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE);
        //开采金矿次数
        int goldOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE);
        if (!(ironOreCount < 500) && !(goldOreCount < 300)) {
            if (ironOreCount >= 500 && goldOreCount == 300) {
                if (ironOreCount == 500) {
                    ironOreCount++;
                }
                goldOreCount++;
            } else if (ironOreCount == 500 && goldOreCount > 300) {
                ironOreCount++;
            }
            player.setStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE, ironOreCount);
            player.setStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE, goldOreCount);
            this.updateAchievementStatus(player, "【矿工】");
        }
    }

    private void updateAchievementStatus(Player player, String message) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
        Achievement achievement = new Achievement();
        achievement.setUuid(player.getUniqueId().toString());
        switch (message) {
            case "【初学者】":
                achievement.setIsBeginner(Boolean.TRUE);
                break;
            case "【渔夫】":
                achievement.setIsFisher(Boolean.TRUE);
                break;
            case "【矿工】":
                achievement.setIsMiner(Boolean.TRUE);
                break;
        }
        achievementMapper.updateById(achievement);
        SqlSessionUtil.closerSqlSession(sqlSession);
        player.sendMessage("§f已获得称号  " + "§a" + message);
    }
}

package com.zzs.listener;

import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

/**
 * 玩家数据监听器
 *
 * @author mm013
 * @Date 2021/6/4 10:25
 */
public class PlayerStatisticIncrementListener implements Listener {

    @EventHandler
    public void StatisticIncrement(PlayerStatisticIncrementEvent event) {
        Player player = event.getPlayer();
        if (event.getStatistic() != null) {
            int timeCount = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
            //初学者
            this.isAccomplishBeginner(player, timeCount);
            //渔夫
            this.isAccomplishFisher(player);
            //矿工
            this.isAccomplishMiner(player);
            //农夫
            this.isAccomplishFarmer(player);
            //学识者
            isAccomplishKnowledgePeople(player, timeCount);
            //钻石大亨
            this.isAccomplishDiamondBigShort(player);
            //猎尸者
            this.isAccomplishHuntingCorpse(player);
            //探险家
            this.isAccomplishExplorer(player);
            //伐木工
            this.isAccomplishTimberjack(player);
            //附魔师
            this.isAccomplishEnchanter(player);
            //巫师
//            this.isAccomplishSorcerer(player);
//            //屠夫
//            this.isAccomplishButcher(player);
//            //倾国倾城
//            this.isAccomplishTheEmpressDowager(player);
//            //B站主播
//            this.isAccomplishBiliAnchor(player);
//            //元老
//            this.isAccomplishOldHead(player);
//            //小财主
//            this.isAccomplishSmallRichMan(player);
//            //杀戮者
//            this.isAccomplishPlayerKiller(player);
//            //巡查组
//            this.isAccomplishPatrolGroup(player);
//            //虎牙主播
//            this.isAccomplishHuYaAnchor(player);
//            //空前绝后
//            this.isAccomplishACompleteOneOff(player);
//            //财大气粗
//            this.isAccomplishOstentatious(player);
//            //头颅收集者
//            this.isAccomplishHeadCollector(player);
//            //吉祥物
//            this.isAccomplishMascot(player);
//            //绝代风华
//            this.isAccomplishTimelessIcon(player);
//            //恒古尊耀
//            this.isAccomplishHengGuZunYao(player);

        }
    }

    /**
     * 是否达成附魔师称号
     * 附魔物品*1000
     *
     * @param player
     */
    private void isAccomplishEnchanter(Player player) {
        int itemEnchantedCount = player.getStatistic(Statistic.ITEM_ENCHANTED);
        if (itemEnchantedCount == 1000) {
            itemEnchantedCount++;
            player.setStatistic(Statistic.ITEM_ENCHANTED, itemEnchantedCount);
            this.updateAchievementStatus(player, "【附魔师】");
        }
    }

    /**
     * 是否达成伐木工称号
     * 橡树木*640  白桦木*640  从林木*640
     *
     * @param player
     */
    private void isAccomplishTimberjack(Player player) {
        //橡树木
        int oakLogCount = player.getStatistic(Statistic.MINE_BLOCK, Material.OAK_LOG);
        //白桦木
        int birchLogCount = player.getStatistic(Statistic.MINE_BLOCK, Material.BIRCH_LOG);
        //从林木
        int jungleLogCount = player.getStatistic(Statistic.MINE_BLOCK, Material.JUNGLE_LOG);
        if (oakLogCount >= 640 && birchLogCount >= 640 && jungleLogCount == 640) {
            if (oakLogCount == 640) {
                oakLogCount++;
                player.setStatistic(Statistic.MINE_BLOCK, Material.OAK_LOG, oakLogCount);
            }
            if (birchLogCount == 640) {
                birchLogCount++;
                player.setStatistic(Statistic.MINE_BLOCK, Material.BIRCH_LOG, birchLogCount);
            }
            jungleLogCount++;
            player.setStatistic(Statistic.MINE_BLOCK, Material.JUNGLE_LOG, jungleLogCount);
            this.updateAchievementStatus(player, "【伐木工】");
        } else if (oakLogCount > 640 && birchLogCount == 640 && jungleLogCount > 640) {
            birchLogCount++;
            this.updateAchievementStatus(player, "【伐木工】");
        } else if (oakLogCount == 640 && birchLogCount > 640 && jungleLogCount > 640) {
            oakLogCount++;
            this.updateAchievementStatus(player, "【伐木工】");
        }
    }

    /**
     * 是否达成探险家称号
     * 行走30000米，获取的行走距离单位为cm
     *
     * @param player
     */
    private void isAccomplishExplorer(Player player) {
        //行走距离(cm)
        int walkDistance = player.getStatistic(Statistic.WALK_ONE_CM);
        if (walkDistance == 3000000) {
            walkDistance++;
            player.setStatistic(Statistic.WALK_ONE_CM, walkDistance);
            this.updateAchievementStatus(player, "【探险家】");
        }
    }

    /**
     * 是否达成猎尸者称号
     * 僵尸*50    僵尸骷髅*50    僵尸猪人*30
     *
     * @param player
     */
    private void isAccomplishHuntingCorpse(Player player) {
        int zombieKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE);
        int skeletonKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON);
        int zombifiedPigKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIFIED_PIGLIN);
        if (zombieKills >= 50 && skeletonKills >= 50 && zombifiedPigKills == 30) {
            if (zombieKills == 50) {
                zombieKills++;
                player.setStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE, zombieKills);
            }
            if (skeletonKills == 50) {
                skeletonKills++;
                player.setStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON, skeletonKills);
            }
            zombifiedPigKills++;
            player.setStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIFIED_PIGLIN, zombifiedPigKills);
            this.updateAchievementStatus(player, "【猎尸者】");
        } else if (zombieKills > 50 && skeletonKills == 50 && zombifiedPigKills > 30) {
            skeletonKills++;
            player.setStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON, skeletonKills);
            this.updateAchievementStatus(player, "【猎尸者】");
        } else if (zombieKills == 50 && skeletonKills > 50 && zombifiedPigKills > 30) {
            zombieKills++;
            player.setStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE, zombieKills);
            this.updateAchievementStatus(player, "【猎尸者】");
        }
    }

    /**
     * 是否达成钻石大亨称号
     * 钻石开采次数*640
     *
     * @param player
     */
    private void isAccomplishDiamondBigShort(Player player) {
        int diamondOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE);
        if (diamondOreCount == 640) {
            diamondOreCount++;
            player.setStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE, diamondOreCount);
            this.updateAchievementStatus(player, "钻石大亨");
        }
    }

    /**
     * 是否达成学识者称号
     * 累计在线7天,游戏中每20/s
     *
     * @param player
     * @param timeCount 累计在线时间
     */
    private void isAccomplishKnowledgePeople(Player player, int timeCount) {
        if (timeCount == 12096000) {
            this.updateAchievementStatus(player, "【学识者】");
        }
    }

    /**
     * 是否达成农夫称号
     * 小麦*500 西瓜*600 南瓜**300
     *
     * @param player
     */
    private void isAccomplishFarmer(Player player) {
        //小麦采集次数
        int wheatCount = player.getStatistic(Statistic.MINE_BLOCK, Material.WHEAT);
        //西瓜采集次数
        int melonCount = player.getStatistic(Statistic.PICKUP, Material.MELON_SLICE);
        //南瓜采集次数
        int pumpkinCount = player.getStatistic(Statistic.MINE_BLOCK, Material.PUMPKIN);

        if (wheatCount >= 500 && melonCount >= 600 && pumpkinCount == 300) {
            if (wheatCount == 500) {
                wheatCount++;
                player.setStatistic(Statistic.MINE_BLOCK, Material.WHEAT, wheatCount);
            }
            if (melonCount == 600) {
                melonCount++;
                player.setStatistic(Statistic.MINE_BLOCK, Material.MELON, melonCount);
            }
            pumpkinCount++;
            player.setStatistic(Statistic.MINE_BLOCK, Material.PUMPKIN, pumpkinCount);
            this.updateAchievementStatus(player, "【农夫】");
        } else if (wheatCount > 500 && melonCount == 600 && pumpkinCount > 300) {
            melonCount++;
            player.setStatistic(Statistic.MINE_BLOCK, Material.MELON, melonCount);
            this.updateAchievementStatus(player, "【农夫】");
        } else if (wheatCount == 500 && melonCount > 600 && pumpkinCount > 300) {
            wheatCount++;
            player.setStatistic(Statistic.MINE_BLOCK, Material.WHEAT, wheatCount);
            this.updateAchievementStatus(player, "【农夫】");
        }
    }

    /**
     * 是否达成矿工称号
     * 铁矿*500，金矿*300
     *
     * @param player 玩家
     */
    private void isAccomplishMiner(Player player) {
        //开采铁矿次数
        int ironOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE);
        //开采金矿次数
        int goldOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE);
        if (ironOreCount >= 500 && goldOreCount == 300) {
            if (ironOreCount == 500) {
                ironOreCount++;
                player.setStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE, ironOreCount);
            }
            player.setStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE, goldOreCount);
            goldOreCount++;
            this.updateAchievementStatus(player, "【矿工】");
        } else if (ironOreCount == 500 && goldOreCount > 300) {
            ironOreCount++;
            player.setStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE, ironOreCount);
            this.updateAchievementStatus(player, "【矿工】");
        }
    }

    /**
     * 是否达成渔夫称号
     * 钓到的鱼数 * 1000
     *
     * @param player
     */
    private void isAccomplishFisher(Player player) {
        //钓鱼获得次数
        int fishCount = player.getStatistic(Statistic.FISH_CAUGHT);
        if (fishCount == 1000) {
            fishCount++;
            //更改变量防止重复运行该代码造成数据库连接过多
            player.setStatistic(Statistic.FISH_CAUGHT, fishCount);
            this.updateAchievementStatus(player, "【渔夫】");
        }
    }

    /**
     * 是否达成初学者称号
     * 累计在线24h,游戏中每20/s
     *
     * @param player
     * @param timeCount
     */
    private void isAccomplishBeginner(Player player, int timeCount) {
        if (timeCount == 1728000) {
            this.updateAchievementStatus(player, "【初学者】");
        }
    }

    /**
     * 修改称号获得状态
     *
     * @param player
     * @param message
     */
    private void updateAchievementStatus(Player player, String message) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
        Achievement achievement = new Achievement();
        achievement.setUuid(player.getUniqueId().toString());
        switch (message) {
            //绿色颜色
            case "【初学者】":
                achievement.setIsBeginner(Boolean.TRUE);
                break;
            case "【渔夫】":
                achievement.setIsFisher(Boolean.TRUE);
                break;
            case "【矿工】":
                achievement.setIsMiner(Boolean.TRUE);
                break;
            case "【农夫】":
                achievement.setIsFarmer(Boolean.TRUE);
                break;
            //蓝色颜色
            case "【学识者】":
                achievement.setIsKnowledgePeople(Boolean.TRUE);
                break;
            case "【钻石大亨】":
                achievement.setIsDiamondBigShort(Boolean.TRUE);
                break;
            case "【猎尸者】":
                achievement.setIsHuntingCorpse(Boolean.TRUE);
                break;
            case "【探险家】":
                achievement.setIsExplorer(Boolean.TRUE);
                break;
            case "【伐木工】":
                achievement.setIsTimberjack(Boolean.TRUE);
                break;
            case "【附魔师】":
                achievement.setIsEnchanter(Boolean.TRUE);
                break;
            case "【巫师】":
                achievement.setIsSorcerer(Boolean.TRUE);
                break;
            case "【屠夫】":
                achievement.setIsButcher(Boolean.TRUE);
                break;
            //粗字体粉色颜色
            case "【倾国倾城】":
                achievement.setIsTheEmpressDowager(Boolean.TRUE);
                break;
            case "【B站主播】":
                achievement.setIsBiliAnchor(Boolean.TRUE);
                break;
            //粗字体紫色
            case "【元老】":
                achievement.setIsOldHead(Boolean.TRUE);
                break;
            case "【小财主】":
                achievement.setIsSmallRichMan(Boolean.TRUE);
                break;
            case "【杀戮者】":
                achievement.setIsPlayerKiller(Boolean.TRUE);
                break;
            case "【巡查组】":
                achievement.setIsPatrolGroup(Boolean.TRUE);
                break;
            //粗字体金色
            case "【虎牙主播】":
                achievement.setIsHuYaAnchor(Boolean.TRUE);
                break;
            case "【空前绝后】":
                achievement.setIsACompleteOneOff(Boolean.TRUE);
                break;
            case "【财大气粗】":
                achievement.setIsOstentatious(Boolean.TRUE);
                break;
            case "【头颅收集者】":
                achievement.setIsHeadCollector(Boolean.TRUE);
                break;
            //粗字体彩色
            case "【吉祥物】":
                achievement.setIsMascot(Boolean.TRUE);
                break;
            case "【绝代风华】":
                achievement.setIsTimelessIcon(Boolean.TRUE);
                break;
            case "【恒古尊耀】":
                achievement.setIsHengGuZunYao(Boolean.TRUE);
                break;
        }
        achievementMapper.updateById(achievement);
        SqlSessionUtil.commitSql(sqlSession);
        player.sendMessage("§f已获得称号  " + "§a" + message);
    }
}

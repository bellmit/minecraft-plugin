package com.zzs.commands;

import com.zzs.util.CommonMethodUtil;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author mm013
 * @Date 2021/6/4 16:19
 */
public class PlayTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = CommonMethodUtil.isPlayer(sender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("nowTime")) {
            int statistic = player.getStatistic(Statistic.FISH_CAUGHT);
            player.sendMessage("鱼捕捉数：" + statistic);
//            SqlSession sqlSession = SqlSessionUtil.getSqlSession();
//            AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
//            Achievement achievement = new Achievement();
//            achievement.setUuid(player.getUniqueId().toString());
//            achievementMapper.insert(achievement);
//            sqlSession.commit();
            return true;
        }

        return false;
    }
}

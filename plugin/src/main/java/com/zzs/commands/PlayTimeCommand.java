package com.zzs.commands;

import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.CommonMethodUtil;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
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
            int statistic = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
            SqlSession sqlSession = SqlSessionUtil.getSqlSession();
            AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
            Achievement achievement = new Achievement();
            achievement.setUuid(player.getUniqueId().toString());
            achievementMapper.insert(achievement);
            sqlSession.commit();
            return true;
        }
        return false;
    }
}

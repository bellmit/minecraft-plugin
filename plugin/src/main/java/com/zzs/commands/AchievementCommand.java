package com.zzs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * 手动修改称号状态
 *
 * @author mountain
 * @since 2021/6/12 18:07
 */
public class AchievementCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
//        AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
//        if (strings.length != 1) {
//            return false;
//        }
//        QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
//        achievementQueryWrapper.eq("user_name", strings[0]);
//        Achievement selectOne = achievementMapper.selectOne(achievementQueryWrapper);
//        if (selectOne == null) {
//            commandSender.sendMessage(Const.SYSTEM_PREFIX + "未找到该用户名");
//            return false;
//        }
//        Achievement achievement = new Achievement();
//
//        if (command.getName().equals("updateTheEmpressDowager")) {
//            achievement.setIsTheEmpressDowager(Boolean.TRUE);
//            achievementMapper.update(achievement, achievementQueryWrapper);
//            SqlSessionUtil.commitSql(sqlSession);
//            commandSender.sendMessage(Const.SYSTEM_PREFIX + "执行成功");
//            return true;
//        }
        return false;
    }
}

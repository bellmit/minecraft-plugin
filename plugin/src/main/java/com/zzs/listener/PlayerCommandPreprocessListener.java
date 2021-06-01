package com.zzs.listener;

import com.zzs.dao.UserDao;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * @author mountain
 * @since 2021/6/2 0:46
 */
public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Boolean isLogin = userDao.findIsLoginByUuid(player.getUniqueId().toString());
        if (!isLogin && !event.getMessage().contains("/login") && !event.getMessage().contains("/register")) {
            player.sendMessage("§c您还未登录！无权使用该命令！");
            event.setMessage("/o");
        }
    }
}

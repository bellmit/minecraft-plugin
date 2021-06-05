package com.zzs.listener;

import com.zzs.dao.UserMapper;
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
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        Boolean isLogin = userDao.findIsLoginByUuid(player.getUniqueId().toString());
        if (!isLogin && !player.isOp() && !event.getMessage().contains("/login") && !event.getMessage().contains("/register")) {
            player.sendMessage("§c您还未登录！无权使用该命令！");
            event.setMessage("/o");
        }
    }
}

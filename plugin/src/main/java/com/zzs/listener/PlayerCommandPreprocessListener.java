package com.zzs.listener;

import com.zzs.dao.UserMapper;
import com.zzs.entity.User;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * 玩家命令监听器
 *
 * @author mountain
 * @since 2021/6/2 0:46
 */
public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(player.getUniqueId().toString());
        if (!event.getMessage().contains("/login") && !event.getMessage().contains("/register") && !player.isOp()) {
            if (user == null) {
                player.sendMessage("§c您还未注册！无权使用该命令！");
                event.setMessage("/o");
            }
            if (!user.getIsLogin()) {
                player.sendMessage("§c您还未登录！无权使用该命令！");
                event.setMessage("/o");
            }
        }
        SqlSessionUtil.closeSqlSession(sqlSession);
    }
}


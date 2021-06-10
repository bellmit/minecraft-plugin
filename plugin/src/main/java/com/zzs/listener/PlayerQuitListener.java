package com.zzs.listener;

import com.zzs.dao.UserMapper;
import com.zzs.entity.User;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author mountain
 * @since 2021/5/31 20:50
 */
public class PlayerQuitListener implements Listener {
    /**
     * 玩家退出事件
     *
     * @param event
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(player.getUniqueId().toString());
        if (!user.getIsLogin()) {
            //如果玩家未登录情况下退出则不保存数据（加入的时候清空了背包防止保存覆盖）
            player.loadData();
            return;
        }
        user.setIsLogin(Boolean.FALSE);
        userMapper.updateById(user);
        player.saveData();
        SqlSessionUtil.closeSqlSession(sqlSession);
    }
}

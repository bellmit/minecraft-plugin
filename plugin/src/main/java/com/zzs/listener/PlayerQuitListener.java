package com.zzs.listener;

import com.zzs.dao.UserDao;
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
        player.saveData();
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.updateIsLoginByUuid(player.getUniqueId().toString(), Boolean.FALSE);
        sqlSession.commit();
    }
}

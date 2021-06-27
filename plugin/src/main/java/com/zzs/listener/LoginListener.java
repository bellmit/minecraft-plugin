package com.zzs.listener;

import com.zzs.MainPlugin;
import com.zzs.dao.UserMapper;
import com.zzs.entity.User;
import com.zzs.util.Const;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/**
 * @author mountain
 * @since 2021/5/3 21:40
 */
public class LoginListener implements Listener {
    public LoginListener(MainPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * 玩家加入事件
     *
     * @param event
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setFlySpeed(0);
        player.setWalkSpeed(0);
        player.getInventory().clear();
        player.setInvulnerable(Boolean.TRUE);
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage("§6〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        player.sendMessage("§6〓                          " + "§e§l欢迎加入" + "                      §6〓");
        player.sendMessage("§6〓                          " + "§a§l四季甜橙" + "                      §6〓");
        player.sendMessage("§6〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        String[] s1 = {Const.SYSTEM_PREFIX + "请输入 §2/register  " + "§f<" + "§2密码" + "§f>" + " " + "§f<" + "§2再次确认密码" + "§f>" + " 注册", "如 /register 123456 123456",
                Const.SYSTEM_PREFIX + "请输入 §2/login " + "§f<" + "§2密码" + "§f>" + " 登录", "如 /login 123456"};
        player.sendMessage(s1);
    }

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
        if (user == null) {
            return;
        }
        if (!user.getIsLogin()) {
            //如果玩家未登录情况下退出则不保存数据（加入的时候清空了背包防止保存覆盖）
            player.loadData();
            return;
        }
        user.setIsLogin(Boolean.FALSE);
        userMapper.updateById(user);
        player.saveData();
        SqlSessionUtil.commitSql(sqlSession);
//        Bukkit.getServer().broadcastMessage(Const.SYSTEM_PREFIX + player.getDisplayName() + "§f离开了游戏");
    }
}

package com.zzs.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


/**
 * @author mountain
 * @since 2021/5/3 21:40
 */
public class PlayerJoinListener implements Listener {

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
        player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓欢迎加入〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        String[] s1 = {"按T输入登录命令 §a/login [密码]", "如 /login abc 123456",
                "按T输入注册命令 §a/register  [密码] [再次确认密码]", "如 /register abc 123456 123456"};
        player.sendMessage(s1);
    }
}

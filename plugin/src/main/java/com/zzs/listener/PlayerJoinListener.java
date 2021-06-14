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
        player.sendMessage("§6〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        player.sendMessage("§6〓                          " + "§e§l欢迎加入" + "                      §6〓");
        player.sendMessage("§6〓                          " + "§a§l四季甜橙" + "                      §6〓");
        player.sendMessage("§6〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        String[] s1 = {"请输入 §2/register  " + "§f<" + "§2密码" + "§f>" + " " + "§f<" + "§2再次确认密码" + "§f>" + " 注册", "如 /register 123456 123456",
                "请输入 §2/login " + "§f<" + "§2密码" + "§f>" + " 登录", "如 /login 123456"};
        player.sendMessage(s1);
    }
}

package com.zzs.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


/**
 * @author mountain
 * @since 2021/5/3 21:40
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setFlySpeed(0);
        player.setWalkSpeed(0);
        player.getInventory().clear();
        player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓欢迎加入〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        String[] s1 = {"登录命令 §a/login [用户名] [密码]", "如 /login abc 123456",
                "注册命令 §a/register [用户名] [密码] [再次确认密码]", "如 /register abc 123456 123456"};
        player.sendMessage(s1);
    }
}

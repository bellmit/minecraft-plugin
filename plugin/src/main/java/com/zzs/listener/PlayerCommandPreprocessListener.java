package com.zzs.listener;

import com.zzs.util.Const;
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
        if (event.getMessage().contains("/nickname") && !player.isOp()) {
            player.sendMessage(Const.SYSTEM_PREFIX + "§c无权使用该命令！");
            event.setMessage("/0");
        }
    }
}


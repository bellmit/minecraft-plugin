package com.zzs.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 玩家放置方块事件
 *
 * @author mountain
 * @since 2021/6/16 4:04
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            if (player.getWorld().getName().equals("main_city") && !player.isOp()) {
                //禁止放置
                event.setCancelled(true);
            }
        }
    }

}

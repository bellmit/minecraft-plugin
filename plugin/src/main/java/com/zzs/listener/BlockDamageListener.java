package com.zzs.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

/**
 * 玩家损坏方块事件
 *
 * @author mountain
 * @since 2021/6/16 3:27
 */
public class BlockDamageListener implements Listener {
    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            if (player.getWorld().getName().equals("main_city") && !player.isOp()) {
                event.setCancelled(true);
            }
        }
    }
}

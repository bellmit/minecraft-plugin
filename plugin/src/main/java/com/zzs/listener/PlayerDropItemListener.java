package com.zzs.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * @author mountain
 * @since 2021/6/1 22:52
 */
public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType().equals(Material.CLOCK)) {
            event.getItemDrop().setPickupDelay(0);
        }
    }
}

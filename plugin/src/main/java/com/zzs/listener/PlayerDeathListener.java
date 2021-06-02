package com.zzs.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @author mm013
 * @Date 2021/6/2 10:21
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlaryerDeath(PlayerDeathEvent event) {
        event.setKeepInventory(Boolean.TRUE);
        event.getDrops().clear();
        Bukkit.getServer().broadcastMessage(event.getDeathMessage());
    }

}

package com.zzs.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author mountain
 * @since 2021/5/31 23:38
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Bukkit.getServer().broadcastMessage("触发了点击事件");
        if (event.getCurrentItem().getType().equals(Material.CLOCK)) {

        }
    }
}

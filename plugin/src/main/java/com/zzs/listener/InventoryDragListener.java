package com.zzs.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * @author mm013
 * @Date 2021/6/10 10:27
 */
public class InventoryDragListener implements Listener {

    @EventHandler
    public void onItemDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("菜单栏")) {
            event.setCancelled(Boolean.TRUE);
        }
        Bukkit.getServer().broadcastMessage("触发拖动事件");
    }
}

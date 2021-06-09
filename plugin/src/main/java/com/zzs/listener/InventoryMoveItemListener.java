package com.zzs.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author mountain
 * @since 2021/6/9 23:17
 */
public class InventoryMoveItemListener implements Listener {

    @EventHandler
    public void inventoryMoveItem(InventoryMoveItemEvent event) {
        Inventory destination = event.getDestination();
//        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
//        ItemMeta itemMeta = itemStack.getItemMeta();
//        itemMeta.setDisplayName("个人信息");
//        itemStack.setItemMeta(itemMeta);
        Bukkit.getServer().broadcastMessage("物品栏大小：" + destination.getContents());
    }
}

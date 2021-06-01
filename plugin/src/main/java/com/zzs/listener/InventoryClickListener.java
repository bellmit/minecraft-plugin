package com.zzs.listener;

import com.zzs.util.CommonMethodUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author mountain
 * @since 2021/5/31 23:38
 */
public class InventoryClickListener implements Listener {

    /**
     * 玩家点击事件
     *
     * @param event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem().getType().equals(Material.CLOCK) && event.getClick().isRightClick()) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = CommonMethodUtil.createMenu();
            player.openInventory(inventory);
        }
    }
}

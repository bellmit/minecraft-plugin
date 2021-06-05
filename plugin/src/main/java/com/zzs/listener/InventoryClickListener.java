package com.zzs.listener;

import com.zzs.util.CommonMethodUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if (event.getClick().equals(ClickType.RIGHT) && currentItem != null) {
            if (currentItem.getItemMeta().getDisplayName().equals("菜单")) {
                Inventory inventory = CommonMethodUtil.createMenu();
                player.openInventory(inventory);
            }
            if (currentItem.getItemMeta().getDisplayName().equals("世界传送")) {
                Inventory inventory = this.createTransferList();
                player.openInventory(inventory);
            }
        }

        if (event.getRawSlot() == 0 && currentItem.getItemMeta().getDisplayName().equals("个人信息")) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    private Inventory createTransferList() {
        Inventory inventory = Bukkit.createInventory(null, 9, "所有世界");
        ItemStack endStone = new ItemStack(Material.END_STONE);
        ItemMeta endStoneItemMeta = endStone.getItemMeta();
        endStoneItemMeta.setDisplayName("末地");
        endStoneItemMeta.setLore(Arrays.asList("传送到末地世界"));
        endStone.setItemMeta(endStoneItemMeta);
        inventory.addItem(endStone);
        return inventory;
    }
}

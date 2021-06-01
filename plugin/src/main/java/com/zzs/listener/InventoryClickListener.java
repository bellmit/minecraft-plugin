package com.zzs.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mountain
 * @since 2021/5/31 23:38
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem().getType().equals(Material.CLOCK) && event.getClick().isRightClick()) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = Bukkit.createInventory(null, 9, "菜单栏");
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("个人信息");
            List<String> infoLore = new ArrayList<>();
            infoLore.add("等级：xxx");
            infoLore.add("金钱：xxx");
            itemMeta.setLore(infoLore);
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
            player.openInventory(inventory);
        }
    }

    public void onClick1(InventoryClickEvent event) {
        // 获取被点击的容器的对象
        Inventory inv = event.getInventory();
        // 因为Inventory底层的实现是由一个数组来进行管理的, 所以我们的首项就是0
        if (event.getRawSlot() == 0) {
            // 获取点击容器的玩家的对象
            Player player = (Player) event.getWhoClicked();
            // 对该玩家进行传送的操作
            player.teleport(player.getLocation().add(1D, 1D, 1D));
            // 因为我们不想让玩家拿走该物品所以我们使用
            //  方法来取消该事件的执行
            event.setCancelled(true);
            // 让玩家关闭当前的容器界面
            player.closeInventory();
        }
    }
}

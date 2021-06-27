package com.zzs.listener;

import com.zzs.util.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 当玩家对一个对象或空气进行交互时触发本事件.
 *
 * @author mm013
 * @Date 2021/6/10 11:54
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item != null) {
            if (item.getItemMeta() != null && event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                //手持菜单右键时打开菜单栏
                if (item.getItemMeta().getDisplayName().equals("菜单")) {
                    Inventory menu = CommonUtil.createMenu();
                    player.openInventory(menu);
                }
            }
        }

    }
}

package com.zzs.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;


/**
 * @author mountain
 * @since 2021/5/3 21:40
 */
public class PlayerJoinListener implements Listener {

//    private final Tutorial plugin;
//
//
//    public PlayerLoginListener(Tutorial plugin) {
//        this.plugin = plugin;
//    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setFlySpeed(0);
        player.setWalkSpeed(0);
        player.getInventory().clear();
        player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓欢迎加入〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
        String[] s1 = {"登录命令 §a/login [用户名] [密码]", "如 /login abc 123456",
                "注册命令 §a/register [用户名] [密码] [再次确认密码]", "如 /register abc 123456 123456"};
        player.sendMessage(s1);
    }

    public void onClick(InventoryClickEvent event) {
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

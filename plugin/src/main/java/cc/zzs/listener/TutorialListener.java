package cc.zzs.listener;

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
public class TutorialListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("欢迎"+event.getPlayer().getName()+"加入---------------");
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

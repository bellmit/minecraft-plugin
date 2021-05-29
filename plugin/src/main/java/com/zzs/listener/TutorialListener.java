package com.zzs.listener;

import com.zzs.dao.UserDao;
import com.zzs.entity.User;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mountain
 * @since 2021/5/3 21:40
 */
public class TutorialListener implements Listener {
    @Autowired

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findByNameAndUuid(event.getName(), event.getUniqueId().toString());
        if (user == null) {
            userDao.registerAccount(event.getName(), event.getAddress().toString(), event.getUniqueId().toString());
        }
        sqlSession.commit();
        event.allow();
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

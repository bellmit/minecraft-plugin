package com.zzs.commands;

import com.zzs.dao.UserDao;
import com.zzs.util.CommonMethodUtil;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author mountain
 * @since 2021/6/2 0:31
 */
public class RegisterCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = CommonMethodUtil.isPlayer(commandSender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("register")) {
            if (!(strings.length == 3)) {
                player.sendMessage("§c请输入正确的参数个数！参数之间使用空格隔开！");
                return true;
            }
            if (!strings[0].equals(player.getName())) {
                player.sendMessage("§c用户名与实际使用名称不相符！");
                return true;
            }
            if (!strings[1].equals(strings[2])) {
                player.sendMessage("§c两次密码输入不一致！");
                return true;
            }
            if (strings[1].length() < 6) {
                player.sendMessage("§c密码长度不能小于6位！");
                return true;
            }
            SqlSession sqlSession = SqlSessionUtil.getSqlSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            String userName = userDao.findUserNameByUserName(strings[0]);
            if (strings[0].equals(userName)) {
                player.sendMessage("§c该用户名已被占用！");
                return true;
            }
            userDao.registerAccount(strings[0], strings[1], player.getAddress().toString().replaceAll("/", ""), player.getUniqueId().toString());
            sqlSession.commit();
            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.1F);
            PlayerInventory inventory = player.getInventory();
            ItemStack itemStack = new ItemStack(Material.CLOCK);
            inventory.setItem(8, itemStack);
            player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓注册成功〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
            return true;
        }
        return false;
    }
}

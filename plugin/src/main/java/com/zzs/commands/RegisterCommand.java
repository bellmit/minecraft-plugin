package com.zzs.commands;

import com.zzs.dao.UserMapper;
import com.zzs.entity.User;
import com.zzs.util.CommonMethodUtil;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
            if (!(strings.length == 2)) {
                player.sendMessage("§c请查看正确的命令使用规则！参数之间使用空格隔开！");
                return false;
            }
            if (!strings[0].equals(strings[1])) {
                player.sendMessage("§c两次密码输入不一致！");
                return true;
            }
            if (strings[0].length() < 6) {
                player.sendMessage("§c密码长度不能小于6位！");
                return true;
            }
            SqlSession sqlSession = SqlSessionUtil.getSqlSession();
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            User user = userDao.findUserByUuid(player.getUniqueId().toString());
            if (user != null) {
                if (user.getIsLogin()) {
                    player.sendMessage("§c禁止重复注册！");
                    return true;
                }
                if (user.getUserName().equals(player.getName())) {
                    player.sendMessage("§c该用户名已被占用！");
                    return true;
                }
            }
            userDao.registerAccount(player.getName(), strings[0], player.getAddress().toString().replaceAll("/", ""), player.getUniqueId().toString());
            sqlSession.commit();
            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.1F);
            player.setInvulnerable(Boolean.FALSE);
            player.setGameMode(GameMode.CREATIVE);
            player.setGameMode(GameMode.SURVIVAL);
            PlayerInventory inventory = player.getInventory();
            ItemStack itemStack = new ItemStack(Material.CLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("菜单");
            itemMeta.setLore(Arrays.asList("右键打开菜单栏"));
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
            player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓注册成功〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
            return true;
        }
        return false;
    }
}

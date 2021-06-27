package com.zzs.commands;

import com.zzs.dao.AchievementMapper;
import com.zzs.dao.UserMapper;
import com.zzs.entity.Achievement;
import com.zzs.entity.User;
import com.zzs.util.CommonUtil;
import com.zzs.util.Const;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Bukkit;
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
        Player player = CommonUtil.isPlayer(commandSender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("register")) {
            if (!(strings.length == 2)) {
                player.sendMessage(Const.SYSTEM_PREFIX + "§c请查看正确的命令使用规则！参数之间使用空格隔开！");
                return false;
            }
            if (!strings[0].equals(strings[1])) {
                player.sendMessage(Const.SYSTEM_PREFIX + "§c两次密码输入不一致！");
                return true;
            }
            if (strings[0].length() < 6) {
                player.sendMessage(Const.SYSTEM_PREFIX + "§c密码长度不能小于6位！");
                return true;
            }
            SqlSession sqlSession = SqlSessionUtil.getSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectById(player.getUniqueId().toString());
            if (user != null) {
                if (user.getIsLogin()) {
                    player.sendMessage(Const.SYSTEM_PREFIX + "§c禁止重复注册！");
                    return true;
                }
                if (user.getUserName().equals(player.getName())) {
                    player.sendMessage("Const.SYSTEM_PREFIX+§c该用户名已被占用！");
                    return true;
                }
            }
            User userEntity = new User();
            userEntity.setUuid(player.getUniqueId().toString())
                    .setIpAddress(player.getAddress().toString())
                    .setUserName(player.getName())
                    .setPassword(strings[0]);
            userMapper.insert(userEntity);

            AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
            Achievement achievement = new Achievement();
            achievement.setUuid(player.getUniqueId().toString());
            achievement.setUserName(player.getName());
            achievementMapper.insert(achievement);
            SqlSessionUtil.commitSql(sqlSession);

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
            Bukkit.getServer().broadcastMessage(Const.SYSTEM_PREFIX + player.getDisplayName() + "§f加入了游戏");
            player.sendMessage(Const.SYSTEM_PREFIX + "§a注册成功");
            return true;
        }
        return false;
    }
}

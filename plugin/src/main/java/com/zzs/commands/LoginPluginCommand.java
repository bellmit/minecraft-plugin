package com.zzs.commands;

import com.zzs.Tutorial;
import com.zzs.dao.UserDao;
import com.zzs.entity.User;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * @author mountain
 * @since 2021/5/4 0:09
 */
public class LoginPluginCommand implements CommandExecutor {

    private final Tutorial plugin;

    public LoginPluginCommand(Tutorial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("你必须是一名玩家");
            return true;
        }
        // 如果已经判断好sender是一名玩家之后,将其强转为Player对象,把它作为一个"玩家"来处理
        Player player = (Player) commandSender;
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        if (command.getName().equalsIgnoreCase("login")) {
            User user = userDao.findByNameAndPassword(strings[0], strings[1]);
            if (user == null) {
                player.sendMessage("§c您输入的用户名或密码有误，请重新输入");
                return true;
            }
            PlayerMoveEvent.getHandlerList().unregister(plugin);
            player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓欢迎回来〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
            return true;
        }
        if (command.getName().equalsIgnoreCase("register")) {
            String userName = userDao.findByUserName(strings[0]);
            if (strings[0].equals(userName)) {
                player.sendMessage("§c该用户名已被占用");
                return true;
            }
            if (!strings[1].equals(strings[2])) {
                player.sendMessage("§c两次密码输入不一致");
                return true;
            }
            userDao.registerAccount(strings[0], strings[1], player.getAddress().toString().replaceAll("/", ""), player.getUniqueId().toString());
            sqlSession.commit();
            player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓注册成功〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
            PlayerMoveEvent.getHandlerList().unregister(plugin);
            return true;
        }
        if (command.getName().equalsIgnoreCase("diamondSword")) {
            // 钻石剑
            ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§g金色传说");
            itemMeta.setLore(Arrays.asList("§f这是一把不稳定的钻石剑", "使用后果自行承担"));

            AttributeModifier attributeModifier = new AttributeModifier("神奇的", 5000L, AttributeModifier.Operation.ADD_NUMBER);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attributeModifier);
            //锋利
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
            //火焰效果
            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 6, true);
            //不死
            itemMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 6, true);
            //节肢动物
            itemMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 6, true);
            //横扫之刃
            itemMeta.addEnchant(Enchantment.SWEEPING_EDGE, 6, true);
            itemStack.setItemMeta(itemMeta);
            PlayerInventory inventory = player.getInventory();
            inventory.addItem(itemStack);
            player.sendMessage("生成测试钻石剑成功");
            return true;
        }
        return false;
    }
}

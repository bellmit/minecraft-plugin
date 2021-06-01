package com.zzs.commands;

import com.zzs.dao.UserDao;
import com.zzs.entity.User;
import com.zzs.util.CommonMethodUtil;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author mountain
 * @since 2021/5/4 0:09
 */
public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = CommonMethodUtil.isPlayer(commandSender);
        if (player == null) {
            return false;
        }
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        if (command.getName().equalsIgnoreCase("login")) {
            if (!(strings.length == 2)) {
                player.sendMessage("§c请输入正确的参数个数！参数之间使用空格隔开！");
                return true;
            }
            User user = userDao.findByUuidAndPassword(player.getUniqueId().toString(), strings[1]);
            if (user == null) {
                player.sendMessage("§c您输入的用户名或密码有误，请重新输入！");
                return true;
            }
            player.loadData();
            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.1F);
            userDao.updateIsLoginByUuid(player.getUniqueId().toString(), Boolean.TRUE);
            sqlSession.commit();
            player.sendMessage("§9〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓欢迎回来〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
            return true;
        }

        if (command.getName().equalsIgnoreCase("diamondSword")) {
            // 钻石剑
            ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§6金色传说");
            itemStack.setItemMeta(itemMeta);
            itemMeta.setLore(Arrays.asList("§5传说中的石中剑"));

            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "开发者", 500L, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
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
            return true;
        }
        return false;
    }
}
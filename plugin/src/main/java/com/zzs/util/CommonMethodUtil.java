package com.zzs.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用方法工具
 *
 * @author mountain
 * @since 2021/6/2 0:07
 */
public class CommonMethodUtil {

    /**
     * 创建菜单栏
     *
     * @return
     */
    public static Inventory createMenu() {
        Inventory inventory = Bukkit.createInventory(null, 54, "菜单栏");
        //个人信息
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("个人信息");
        List<String> infoLore = new ArrayList<>();
        infoLore.add("等级：xxx");
        infoLore.add("金钱：xxx");
        itemMeta.setLore(infoLore);
        itemStack.setItemMeta(itemMeta);
        inventory.addItem(itemStack);
        //传送菜单
        ItemStack beaconStack = new ItemStack(Material.END_CRYSTAL);
        ItemMeta beaconMeta = beaconStack.getItemMeta();
        beaconMeta.setDisplayName("传送菜单");
        beaconMeta.setLore(Arrays.asList("点击显示所有世界"));
        beaconStack.setItemMeta(beaconMeta);
        inventory.setItem(2, beaconStack);

        //称号簿
        ItemStack bookStack = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = bookStack.getItemMeta();
        bookMeta.setDisplayName("§5称号簿");
        bookStack.setItemMeta(bookMeta);
        inventory.setItem(4, bookStack);
        return inventory;
    }

    /**
     * 判断是否玩家
     *
     * @param commandSender
     * @return
     */
    public static Player isPlayer(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("你必须是一名玩家");
            return null;
        }
        return (Player) commandSender;
    }
}

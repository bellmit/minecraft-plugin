package com.zzs.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * 通用方法工具
 *
 * @author mountain
 * @since 2021/6/2 0:07
 */
public class CommonUtil {

    public static String getPropertiesParams(String params) throws IOException {
        Properties properties = new Properties();
        properties.load(new BufferedReader(new InputStreamReader(CommonUtil.class.getClassLoader().getResourceAsStream("config.properties")))
        );

        String property = properties.getProperty(params);
        return property;
    }


    /**
     * 发送物品信息到展示栏
     *
     * @param player
     * @param message
     * @param item
     */
    public static void sendItemTooltipMessage(Player player, String message, ItemStack item) {
        String itemJson = convertItemStackToJson(item);

        StringBuilder builder = new StringBuilder();
        builder.append(player.getDisplayName() + "展示了物品 ");
        builder.append(itemJson);

        // Prepare a BaseComponent array with the itemJson as a text component
        BaseComponent[] hoverEventComponents = new BaseComponent[]{
                new TextComponent(itemJson) // The only element of the hover events basecomponents is the item json
        };

        // Create the hover event
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents);

        /* And now we create the text component (this is the actual text that the player sees)
         * and set it's hover event to the item event */
        TextComponent component = new TextComponent(message);
        component.setHoverEvent(event);

        TextComponent textComponent = new TextComponent("");
        textComponent.addExtra(player.getDisplayName() + "展示了 ");


        // Finally, send the message to the player
        player.spigot().sendMessage(textComponent, component);
    }

    /**
     * 将物品对象转json
     *
     * @param itemStack
     * @return
     */
    public static String convertItemStackToJson(ItemStack itemStack) {
        // ItemStack methods to get a net.minecraft.server.ItemStack object for serialization
        Class<?> craftItemStackClazz = ReflectionUtil.getOBCClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = ReflectionUtil.getMethod(craftItemStackClazz, "asNMSCopy", ItemStack.class);

        // NMS Method to serialize a net.minecraft.server.ItemStack to a valid Json string
        Class<?> nmsItemStackClazz = ReflectionUtil.getNMSClass("ItemStack");
        Class<?> nbtTagCompoundClazz = ReflectionUtil.getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = ReflectionUtil.getMethod(nmsItemStackClazz, "save", nbtTagCompoundClazz);

        Object nmsNbtTagCompoundObj; // This will just be an empty NBTTagCompound instance to invoke the saveNms method
        Object nmsItemStackObj; // This is the net.minecraft.server.ItemStack object received from the asNMSCopy method
        Object itemAsJsonObject; // This is the net.minecraft.server.ItemStack after being put through saveNmsItem method

        try {
            nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
            nmsItemStackObj = asNMSCopyMethod.invoke(null, itemStack);
            itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, nmsNbtTagCompoundObj);
        } catch (Throwable t) {
            Bukkit.getLogger().log(Level.SEVERE, "failed to serialize itemstack to nms item", t);
            return null;
        }

        // Return a string representation of the serialized object
        return itemAsJsonObject.toString();
    }


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
        ItemStack bookStack = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta bookMeta = bookStack.getItemMeta();
        bookMeta.setDisplayName("称号簿");
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

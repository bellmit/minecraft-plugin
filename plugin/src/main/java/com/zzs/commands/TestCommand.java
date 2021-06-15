package com.zzs.commands;

import com.zzs.util.CommonMethodUtil;
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
 * @author mm013
 * @Date 2021/6/8 16:14
 */
public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = CommonMethodUtil.isPlayer(sender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("test")) {
            PlayerInventory inventory = player.getInventory();
            ItemStack itemStack = new ItemStack(Material.CLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("菜单");
            itemMeta.setLore(Arrays.asList("右键打开菜单栏"));
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
            player.chat("/nick " + player.getDisplayName());
            return true;
        }
        return false;
    }
}

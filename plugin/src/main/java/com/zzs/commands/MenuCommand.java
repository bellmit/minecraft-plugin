package com.zzs.commands;

import com.zzs.util.CommonUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author mountain
 * @since 2021/6/1 23:50
 */
public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = CommonUtil.isPlayer(commandSender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("menu")) {
            Inventory inventory = CommonUtil.createMenu();
            player.openInventory(inventory);
            return true;
        }
        return false;
    }
}

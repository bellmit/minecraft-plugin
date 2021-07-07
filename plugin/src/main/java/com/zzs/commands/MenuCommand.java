package com.zzs.commands;

import com.zzs.MainPlugin;
import com.zzs.util.CommonUtil;
import com.zzs.util.Const;
import org.bukkit.World;
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
    private final MainPlugin plugin;

    public MenuCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

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
        if (command.getName().equalsIgnoreCase("tpWorld")) {
            if (strings.length != 1) {
                player.sendMessage(Const.SYSTEM_PREFIX + "参数有误");
                return true;
            }
            World world = plugin.getServer().getWorld(strings[0]);
            if (world == null) {
                player.sendMessage(Const.SYSTEM_PREFIX + "该世界不存在");
                return true;
            }
            player.teleport(world.getSpawnLocation());
            return true;
        }
        return false;
    }
}

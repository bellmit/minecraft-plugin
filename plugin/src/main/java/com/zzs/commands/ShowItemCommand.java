package com.zzs.commands;

import com.zzs.MainPlugin;
import com.zzs.util.CommonUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * @author mountain
 * @since 2021/6/30 22:20
 */
public class ShowItemCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public ShowItemCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = CommonUtil.isPlayer(commandSender);
        if (player == null) {
            return false;
        }
        //显示玩家手持物品到聊天栏中
        if (command.getName().equalsIgnoreCase("show")) {
            ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);
            CommonUtil.sendItemTooltipMessage(player, item.getItemMeta().getDisplayName(), item);
            return true;
        }
        return false;
    }
}

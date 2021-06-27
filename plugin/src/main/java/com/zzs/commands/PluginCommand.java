package com.zzs.commands;

import com.zzs.MainPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author mountain
 * @since 2021/6/16 21:16
 */
public class PluginCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public PluginCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        return false;
    }
}

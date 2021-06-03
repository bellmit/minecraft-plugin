package com.zzs.commands;

import com.zzs.Tutorial;
import com.zzs.util.CommonMethodUtil;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author mm013
 * @Date 2021/6/3 10:59
 */
public class WorldCommand implements CommandExecutor {

    private final Tutorial plugin;

    public WorldCommand(Tutorial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player player = CommonMethodUtil.isPlayer(commandSender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("createWorld")) {
            if (!(args.length == 1)) {
                player.sendMessage("§c请查看正确的命令使用规则");
                return false;
            }
            WorldCreator creator = new WorldCreator(args[0]);
            creator.type(WorldType.FLAT);
            World world = plugin.getServer().createWorld(creator);
            //是否生成生物
            world.setGameRule(GameRule.DO_MOB_SPAWNING, Boolean.FALSE);
            //是否死亡不掉落
            world.setGameRule(GameRule.KEEP_INVENTORY, Boolean.TRUE);
            player.sendMessage("创建" + args[0] + "世界成功");
            return true;
        }

        if (command.getName().equals("warp")) {
            player.teleport(plugin.getServer().getWorld(args[0]).getSpawnLocation());
            player.sendMessage("成功传送到" + args[0] + "世界");
            return true;
        }

        return false;
    }
}

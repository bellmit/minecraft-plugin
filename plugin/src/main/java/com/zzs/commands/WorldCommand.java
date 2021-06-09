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

import java.util.List;

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
                player.sendMessage("§c请查看正确的命令使用规则！");
                return false;
            }
            WorldCreator creator = new WorldCreator(args[0]);
            creator.type(WorldType.FLAT);
            World world = plugin.getServer().createWorld(creator);
            //是否生成生物
            world.setGameRule(GameRule.DO_MOB_SPAWNING, Boolean.FALSE);
            //是否死亡不掉落
            world.setGameRule(GameRule.KEEP_INVENTORY, Boolean.TRUE);
            //管理员命令记录
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, Boolean.TRUE);
            //死亡直接重生
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, Boolean.TRUE);
            //流浪商人
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, Boolean.FALSE);
            //跌落伤害
            world.setGameRule(GameRule.FALL_DAMAGE, Boolean.FALSE);
            //火焰伤害
            world.setGameRule(GameRule.FIRE_DAMAGE, Boolean.FALSE);
            //窒息伤害
            world.setGameRule(GameRule.DROWNING_DAMAGE, Boolean.FALSE);
            //自动保存
            world.setAutoSave(true);
            player.sendMessage("创建" + world.getName() + "世界成功");
            return true;
        }

        if (command.getName().equals("worlds")) {
            List<World> worlds = plugin.getServer().getWorlds();
            player.sendMessage("§b〓〓〓〓〓〓〓〓〓〓〓〓〓〓世界名称列表〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
            worlds.forEach(s -> player.sendMessage(s.getName()));
            return true;
        }

        if (command.getName().equalsIgnoreCase("warp")) {
            if (!(args.length == 1)) {
                player.sendMessage("§c请查看正确的命令使用规则！");
                return false;
            }
            List<World> worlds = plugin.getServer().getWorlds();
            worlds.forEach(s -> {
                if (s.equals(args[0])) {
                    World world = plugin.getServer().getWorld(args[0]);
                    player.teleport(world.getSpawnLocation());
                    player.sendMessage("成功传送到：" + world.getName());
                } else {
                    player.sendMessage("§c请输入正确的世界名称！");
                }
            });
            return true;
        }
        return false;
    }
}

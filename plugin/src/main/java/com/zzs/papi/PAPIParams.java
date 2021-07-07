package com.zzs.papi;

import com.zzs.MainPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author mountain
 * @since 2021/7/6 21:52
 */
public class PAPIParams extends PlaceholderExpansion {
    private MainPlugin plugin;

    public PAPIParams(MainPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * 初始化世界名称，转成中文
     *
     * @param world
     * @return
     */
    public static String initWorldName(World world) {
        String worldName;
        switch (world.getName()) {
            case "main_city":
                worldName = "主城";
                break;
            case "world":
                worldName = "生存世界";
                break;
            case "world_the_end":
                worldName = "地狱";
                break;
            case "world_nether":
                worldName = "末地";
                break;
            default:
                worldName = world.getName();
        }
        return worldName;
    }

    /**
     * 这里指是不是要让papi扩展持久化
     * 不持久化则使用reload指令之后你的变量就没了
     *
     * @return
     */
    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "loginsystem";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) {
            return "";
        }
        if (params.equals("world_name")) {
            World world = player.getWorld();
            String worldName = initWorldName(world);
            return plugin.getConfig().getString("world_name", worldName);
        }
        return null;
    }
}

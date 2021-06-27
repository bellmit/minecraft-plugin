package com.zzs.listener;

import com.zzs.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author mm013
 * @Date 2021/6/5 17:19
 */
public class ServerLoadListener implements Listener {

    private final MainPlugin plugin;

    public ServerLoadListener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        List<String> strings = Arrays.asList("main_city", "akira_rakani");
        if (strings.size() != 0) {
            strings.forEach(s -> {
                WorldCreator creator = WorldCreator.name(s);
                plugin.getLogger().info("开始载入世界：" + s);
                Bukkit.createWorld(creator);
            });
        }
    }
}

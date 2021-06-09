package com.zzs.listener;

import com.zzs.Tutorial;
import org.bukkit.Server;
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

    private final Tutorial plugin;

    public ServerLoadListener(Tutorial plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        Server server = plugin.getServer();
        List<String> strings = Arrays.asList("main_city", "world_1", "world_2", "akira_rakani", "jin_gan_dian");
        strings.forEach(s -> {
            WorldCreator creator = new WorldCreator(s);
            plugin.getLogger().info("开始载入世界：" + s);
            server.createWorld(creator);
        });
    }
}

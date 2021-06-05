package com.zzs.listener;

import com.zzs.Tutorial;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

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
        WorldCreator creator = new WorldCreator("world_main_city");
        server.createWorld(creator);
    }
}

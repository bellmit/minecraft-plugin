package com.zzs.listener;

import com.zzs.MainPlugin;
import com.zzs.papi.PAPIParams;
import com.zzs.util.CommonUtil;
import com.zzs.util.Const;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

/**
 * 玩家聊天事件
 *
 * @author mountain
 * @since 2021/6/30 23:22
 */
public class PlayerChatListener implements Listener {
    private final MainPlugin plugin;

    private Player player;

    public PlayerChatListener(MainPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR,
            ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        World world = player.getWorld();
        String c = String.valueOf(message.charAt(0));
        if (!c.equals("/")) {
            try {
                String worldName = "§a" + PAPIParams.initWorldName(world);
                TextComponent worldComponent = new TextComponent(String.format(Const.FORMAT + " ", worldName));

                String at_present_world = String.format(CommonUtil.getPropertiesParams("at_present_world"), worldName);
                worldComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(at_present_world)));
                worldComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpWorld " + world.getName()));

                TextComponent playerComponent = new TextComponent();
                playerComponent.addExtra(player.getDisplayName());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format(Const.PLAYER_NAME, player.getName() + "\n"));
                stringBuilder.append(String.format(Const.PLAYER_HEATH, player.getHealth() + "\n"));
                stringBuilder.append(String.format(Const.PLAYER_HUNGER, player.getSaturation() + "\n"));
                stringBuilder.append("§c点击请求TP到他身边");

                playerComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(stringBuilder.toString())));
                playerComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa " + player.getUniqueId()));

                TextComponent messageComponent = new TextComponent();
                messageComponent.addExtra(" §6-> ");
                messageComponent.addExtra(message);

                plugin.getServer().spigot().broadcast(worldComponent, playerComponent, messageComponent);
            } catch (IOException e) {
                plugin.getLogger().info("警告：获取config文件属性失败");
                e.printStackTrace();
            }
            event.setCancelled(true);
        }

    }

}

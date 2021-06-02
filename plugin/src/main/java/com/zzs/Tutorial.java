package com.zzs;

import com.zzs.commands.LoginCommand;
import com.zzs.commands.MenuCommand;
import com.zzs.commands.RegisterCommand;
import com.zzs.listener.*;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author mountain
 * @since 2021/5/3 19:43
 */
public class Tutorial extends JavaPlugin {
    @SneakyThrows
    @Override
    public void onEnable() {
        getLogger().info("====================login-plugin插件开始启用====================");
        //获取监听器
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);

        //调用注册命令方法
        this.getCommand("diamondSword").setExecutor(new LoginCommand());
        this.getCommand("register").setExecutor(new RegisterCommand());
        this.getCommand("login").setExecutor(new LoginCommand());
        this.getCommand("menu").setExecutor(new MenuCommand());
    }
}

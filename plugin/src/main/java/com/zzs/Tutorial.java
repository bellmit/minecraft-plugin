package com.zzs;

import com.zzs.commands.*;
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
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerLoadListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerStatisticIncrementListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryMoveItemListener(), this);


        //调用注册命令方法
        this.getCommand("diamondSword").setExecutor(new LoginCommand());
        this.getCommand("register").setExecutor(new RegisterCommand());
        this.getCommand("login").setExecutor(new LoginCommand());
        this.getCommand("menu").setExecutor(new MenuCommand());
        this.getCommand("createWorld").setExecutor(new WorldCommand(this));
        this.getCommand("worlds").setExecutor(new WorldCommand(this));
        this.getCommand("nowTime").setExecutor(new PlayTimeCommand());
        this.getCommand("test").setExecutor(new TestCommand());
    }
}

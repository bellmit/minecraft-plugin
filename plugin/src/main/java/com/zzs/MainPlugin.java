package com.zzs;

import com.zzs.commands.AchievementCommand;
import com.zzs.commands.LoginCommand;
import com.zzs.commands.MenuCommand;
import com.zzs.commands.TestCommand;
import com.zzs.listener.*;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author mountain
 * @since 2021/5/3 19:43
 */
public class MainPlugin extends JavaPlugin {
    @SneakyThrows
    @Override
    public void onEnable() {
        getLogger().info("====================loginSystem插件开始启用====================");
        //获取监听器
//        new LoginListener(this);
        new MenuClickListener(this);
        new NpcClickListener(this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerLoadListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerStatisticIncrementListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);

        //调用注册命令方法
        this.getCommand("diamondSword").setExecutor(new LoginCommand());
//        this.getCommand("register").setExecutor(new RegisterCommand());
//        this.getCommand("login").setExecutor(new LoginCommand());
        this.getCommand("menu").setExecutor(new MenuCommand());
        this.getCommand("updateTheEmpressDowager").setExecutor(new AchievementCommand());
        this.getCommand("test").setExecutor(new TestCommand(this));
    }
}

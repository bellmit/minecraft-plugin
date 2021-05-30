package com.zzs;

import com.zzs.commands.LoginPluginCommand;
import com.zzs.listener.LoginListener;
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
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);

        //调用注册命令方法
        this.getCommand("diamondSword").setExecutor(new LoginPluginCommand(this));
        this.getCommand("register").setExecutor(new LoginPluginCommand(this));
        this.getCommand("login").setExecutor(new LoginPluginCommand(this));
    }
}

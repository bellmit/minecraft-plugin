package com.zzs;

import com.zzs.commands.MyPluginCommandExecutor;
import com.zzs.listener.TutorialListener;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mybatis.spring.annotation.MapperScan;


/**
 * @author mountain
 * @since 2021/5/3 19:43
 */
@MapperScan("com/zzs/dao")
public class Tutorial extends JavaPlugin {
    @SneakyThrows
    @Override
    public void onEnable() {
        getLogger().info("====================test-plugin插件开始启用====================");
        //获取监听器
        Bukkit.getPluginManager().registerEvents(new TutorialListener(), this);
        //调用注册命令方法
        this.getCommand("diamondSword").setExecutor(new MyPluginCommandExecutor(this));
        this.getCommand("createCow").setExecutor(new MyPluginCommandExecutor(this));
    }
}

package com.zzs;

import com.zzs.commands.AchievementCommand;
import com.zzs.commands.MenuCommand;
import com.zzs.commands.ShowItemCommand;
import com.zzs.commands.TestCommand;
import com.zzs.listener.*;
import com.zzs.papi.PAPIParams;
import com.zzs.util.SqlSessionUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
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
        Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI"); //用服务端获取PAPI插件
        if (plugin == null) { //如果是null，意味着插件没有安装，因为服务器获取不到PAPI
            getLogger().info("服务器未安装PAPI插件");
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIParams(this).register();
        }
        //获取监听器
        new MenuClickListener(this);
        new NpcClickListener(this);
        new PlayerChatListener(this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerLoadListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerStatisticIncrementListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);

        //调用注册命令方法
        this.getCommand("menu").setExecutor(new MenuCommand(this));
        this.getCommand("tpWorld").setExecutor(new MenuCommand(this));
        this.getCommand("updateTheEmpressDowager").setExecutor(new AchievementCommand());
        this.getCommand("test").setExecutor(new TestCommand(this));
        this.getCommand("diamondSword").setExecutor(new TestCommand(this));
        this.getCommand("show").setExecutor(new ShowItemCommand(this));

      /*  BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                //20tick为1秒
                String format = String.format(Const.FORMAT, "§a扫地娘");
                getServer().broadcastMessage(format+"-> 开始清理地面杂物-------");

            }
        }, 0L, 12000L);*/

        this.initMybatisPlus();
    }

    public void initMybatisPlus() {
        SqlSessionUtil.getSqlSessionFactory();
    }
}

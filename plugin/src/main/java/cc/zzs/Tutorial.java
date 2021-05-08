package cc.zzs;

import cc.zzs.commands.MyPluginCommandExecutor;
import cc.zzs.listener.TutorialListener;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author mountain
 * @since 2021/5/3 19:43
 */
public class Tutorial extends JavaPlugin {


    @SneakyThrows
    @Override
    public void onEnable() {
        getLogger().info("命令测试插件开始调用");
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        config.set("health ", 30);
        config.set("id", 1);
        saveConfig();

        //获取监听器
        Bukkit.getPluginManager().registerEvents(new TutorialListener(), this);
        //调用注册命令方法
        this.getCommand("diamondSword").setExecutor(new MyPluginCommandExecutor(this));
        this.getCommand("createCow").setExecutor(new MyPluginCommandExecutor(this));
    }
}

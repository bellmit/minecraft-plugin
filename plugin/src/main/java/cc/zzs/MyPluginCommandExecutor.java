package cc.zzs;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * @author mountain
 * @since 2021/5/4 0:09
 */
public class MyPluginCommandExecutor implements CommandExecutor {

    private final Tutorial plugin;

    public MyPluginCommandExecutor(Tutorial plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("你必须是一名玩家");
            return true;
        }
            // 如果已经判断好sender是一名玩家之后,将其强转为Player对象,把它作为一个"玩家"来处理
            Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("createCow")) {
            Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.COW);
            // 设置牛的自定义名的可见性
            entity.setCustomNameVisible(true);
            // 设置牛的自定义名
            entity.setCustomName("§6一头测试奶牛");
            return true;
        }
        if (command.getName().equalsIgnoreCase("diamondSword")) {
            // 钻石剑
            ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1, (short)777);
            itemStack.addUnsafeEnchantment(null,100);

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§7测试钻石剑");
            itemMeta.setLore(Arrays.asList("§f这是一把不稳定的钻石剑","使用后果自行承担"));
            //锋利
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
            //火焰效果
            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 4, true);
            //不死
            itemMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 4, true);
            //节肢动物
            itemMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 4, true);
            //横扫之刃
            itemMeta.addEnchant(Enchantment.SWEEPING_EDGE, 4, true);
//            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 20, true);
//            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 20, true);
//            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 20, true);


            itemStack.setItemMeta(itemMeta);
            PlayerInventory inventory = player.getInventory();
            inventory.addItem(itemStack);
            player.sendMessage("生成测试钻石剑成功");
            return true;
        }

        return false;
    }
}

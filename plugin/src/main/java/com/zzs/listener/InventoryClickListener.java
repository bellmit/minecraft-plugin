package com.zzs.listener;

import com.zzs.Tutorial;
import com.zzs.util.CommonMethodUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * @author mountain
 * @since 2021/5/31 23:38
 */
public class InventoryClickListener implements Listener {

    private final Tutorial plugin;

    public InventoryClickListener(Tutorial plugin) {
        this.plugin = plugin;
    }

    /**
     * 玩家点击事件
     *
     * @param event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if (!event.getClick().equals(ClickType.RIGHT) && currentItem != null) {
            ItemMeta itemMeta = currentItem.getItemMeta();
            List<String> stringList = Arrays.asList("个人信息", "传送菜单", "主城", "生存世界", "资源世界", "地狱", "末地");
            stringList.forEach(s -> {
                //非右键则撤销玩家操作
                if (itemMeta.getDisplayName().contains(s)) {
                    event.setCancelled(true);
                }
            });
        }
        if (event.getClick().equals(ClickType.RIGHT) && currentItem != null) {
            String displayName = currentItem.getItemMeta().getDisplayName();
            switch (displayName) {
                case "个人信息":
                    event.setCancelled(true);
                    break;
                case "菜单":
                    Inventory menu = CommonMethodUtil.createMenu();
                    player.openInventory(menu);
                    break;
                case "传送菜单":
                    Inventory teleportMenu = this.createTransferList();
                    player.openInventory(teleportMenu);
                    break;
                case "主城":
                    World world_main_city = plugin.getServer().getWorld("world_main_city");
                    player.teleport(world_main_city.getSpawnLocation());
                    break;
                case "生存世界":
                    World world = plugin.getServer().getWorld("world");
                    player.teleport(world.getSpawnLocation());
                    break;
                case "资源世界":
                    World world_resource = plugin.getServer().getWorld("world_resource");
                    player.teleport(world_resource.getSpawnLocation());
                    break;
                case "地狱":
                    World world_nether = plugin.getServer().getWorld("world_nether");
                    player.teleport(world_nether.getSpawnLocation());
                    break;
                case "末地":
                    World world_the_end = plugin.getServer().getWorld("world_the_end");
                    player.teleport(world_the_end.getSpawnLocation());
                    break;
            }
        }
    }

    private Inventory createTransferList() {
        Inventory inventory = Bukkit.createInventory(null, 9, "所有世界");
        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemMeta beaconItemMeta = beacon.getItemMeta();
        beaconItemMeta.setDisplayName("主城");
        beacon.setItemMeta(beaconItemMeta);
        inventory.setItem(0, beacon);

        ItemStack grassBlock = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta grassBlockItemMeta = grassBlock.getItemMeta();
        grassBlockItemMeta.setDisplayName("生存世界");
        grassBlock.setItemMeta(grassBlockItemMeta);
        inventory.setItem(2, grassBlock);

        ItemStack diamondOre = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta diamondOreItemMeta = diamondOre.getItemMeta();
        diamondOreItemMeta.setDisplayName("资源世界");
        diamondOre.setItemMeta(diamondOreItemMeta);
        inventory.setItem(4, diamondOre);

        ItemStack netherrack = new ItemStack(Material.NETHERRACK);
        ItemMeta netherrackItemMeta = netherrack.getItemMeta();
        netherrackItemMeta.setDisplayName("地狱");
        netherrack.setItemMeta(netherrackItemMeta);
        inventory.setItem(6, netherrack);

        ItemStack endStone = new ItemStack(Material.END_STONE);
        ItemMeta endStoneItemMeta = endStone.getItemMeta();
        endStoneItemMeta.setDisplayName("末地");
        endStone.setItemMeta(endStoneItemMeta);
        inventory.setItem(8, endStone);
        return inventory;
    }
}

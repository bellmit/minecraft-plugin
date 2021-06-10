package com.zzs.listener;

import com.zzs.Tutorial;
import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.CommonMethodUtil;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
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
 * 玩家点击监听器
 *
 * @author mountain
 * @since 2021/5/31 23:38
 */
public class InventoryClickListener implements Listener {

    private final Tutorial plugin;

    List<String> worldList = Arrays.asList("生存世界", "主城", "world_1", "world_2", "akira_rakani", "jin_gan_dian");

    public InventoryClickListener(Tutorial plugin) {
        this.plugin = plugin;
    }

    List<String> menuList = Arrays.asList("个人信息", "传送菜单");
    private Player player;

    /**
     * 玩家点击事件
     *
     * @param event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if (!event.getClick().equals(ClickType.LEFT) && currentItem != null) {
            if (currentItem.hasItemMeta()) {
                ItemMeta itemMeta = currentItem.getItemMeta();
                menuList.forEach(s -> {
                    if (itemMeta.getDisplayName().contains(s)) {
                        event.setCancelled(true);
                    }
                });
                worldList.forEach(s -> {
                    if (itemMeta.getDisplayName().contains(s)) {
                        event.setCancelled(true);
                    }
                });
            }
        }

        //打开背包状态下右键打开菜单栏
        if (event.getClick().equals(ClickType.RIGHT) && currentItem != null) {
            ItemMeta itemMeta = currentItem.getItemMeta();
            if (itemMeta != null) {
                itemMeta.getDisplayName().equals("菜单");
                Inventory menu = CommonMethodUtil.createMenu();
                player.openInventory(menu);
            }
        }

        //点击触发传送
        if (event.getClick().equals(ClickType.LEFT) && currentItem != null) {
            ItemMeta itemMeta = currentItem.getItemMeta();
            if (itemMeta != null) {
                String displayName = itemMeta.getDisplayName();
                switch (displayName) {
                    case "个人信息":
                        event.setCancelled(true);
                        break;
                    case "称号簿":
                        this.createAchievementView();
                        break;
                    case "传送菜单":
                        Inventory teleportMenu = this.createTransferView(worldList);
                        player.openInventory(teleportMenu);
                        break;
                    case "主城":
                        World world_main_city = plugin.getServer().getWorld("main_city");
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
                    case "world_1":
                        World world_1 = plugin.getServer().getWorld("world_1");
                        player.teleport(world_1.getSpawnLocation());
                        break;
                    case "world_2":
                        World world_2 = plugin.getServer().getWorld("world_2");
                        player.teleport(world_2.getSpawnLocation());
                        break;
                    case "akira_rakani":
                        World akira_rakani = plugin.getServer().getWorld("akira_rakani");
                        player.teleport(akira_rakani.getSpawnLocation());
                        break;
                    case "jin_gan_dian":
                        World jin_gan_dian = plugin.getServer().getWorld("jin_gan_dian");
                        player.teleport(jin_gan_dian.getSpawnLocation());
                        break;
                }
            }
        }

        //防止玩家将物品转移到菜单物品栏内
        if (event.getView().getTitle().equals("菜单栏") || event.getView().getTitle().equals("所有世界")) {
            event.setCancelled(true);
        }
    }

    /**
     * 创建称号预览
     */
    private void createAchievementView() {
        Inventory inventory = Bukkit.createInventory(null, 54, "称号簿");
        //绿色称号
        this.createAchievement(inventory, "§a【萌新】");
        this.createAchievement(inventory, "§a【初学者】");
        this.createAchievement(inventory, "§a【渔夫】");
        this.createAchievement(inventory, "§a【矿工】");
        this.createAchievement(inventory, "§a【农夫】");
        //蓝色称号
        this.createAchievement(inventory, "§9【学识者】");
        this.createAchievement(inventory, "§9【钻石大亨】");
        this.createAchievement(inventory, "§9【探险家】");
        this.createAchievement(inventory, "§9【伐木工】");
        this.createAchievement(inventory, "§9【附魔师】");
        this.createAchievement(inventory, "§9【巫师】");


    }

    private Inventory createAchievement(Inventory inventory, String achievementName) {
        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(achievementName);
        this.setAchievementLore(achievementName, itemMeta);


        itemStack.setItemMeta(itemMeta);
        inventory.addItem(itemStack);
        return inventory;
    }

    /**
     * 设置称号进度内容
     *
     * @param achievementName
     * @param itemMeta
     */
    private ItemMeta setAchievementLore(String achievementName, ItemMeta itemMeta) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
        Achievement achievement = achievementMapper.selectById(player.getUniqueId());
        switch (achievementName) {
            case "【萌新】":
                itemMeta.setLore(Arrays.asList("§d小小新星"));
                break;
            case "【初学者】":
                if (achievement.getIsBeginner()) {
                    itemMeta.setLore(Arrays.asList("累计在线24小时", "§a已获得"));
                    break;
                }
                itemMeta.setLore(Arrays.asList("累计在线24小时", "§7未获得"));
                break;
            case "【渔夫】":
                if (achievement.getIsFisher()) {
                    itemMeta.setLore(Arrays.asList("累计钓上1000条鱼", "§a已获得"));
                    break;
                }
                int fishCount = player.getStatistic(Statistic.FISH_CAUGHT);
                itemMeta.setLore(Arrays.asList("累计钓上1000条鱼", "§7未获得", "§o1000 / " + fishCount));
                break;
            case "【矿工】":
                if (achievement.getIsMiner()) {
                    itemMeta.setLore(Arrays.asList("挖掘 500个铁矿 300个金矿", "§a已获得"));
                    break;
                }
                int ironOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE);
                int goldOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE);
                itemMeta.setLore(Arrays.asList("挖掘 500个铁矿 300个金矿", "铁矿：§o500 / " + ironOreCount, "金矿：§o300 / " + goldOreCount));
                break;
            case "【农夫】":
                break;
            //蓝色颜色
            case "【学识者】":
                break;
            case "【钻石大亨】":
                break;
            case "【猎尸者】":
                break;
            case "【探险家】":
                break;
            case "【伐木工】":
                break;
            case "【附魔师】":
                break;
            case "【巫师】":
                break;
            case "【屠夫】":
                break;
            //粗字体粉色颜色
            case "【倾国倾城】":
                break;
            case "【B站主播】":
                break;
            //粗字体紫色
            case "【元老】":
                break;
            case "【小财主】":
                break;
            case "【杀戮者】":
                break;
            case "【巡查组】":
                break;
            //粗字体金色
            case "【虎牙主播】":
                break;
            case "【空前绝后】":
                break;
            case "【财大气粗】":
                break;
            case "【头颅收集者】":
                break;
            //粗字体彩色
            case "【吉祥物】":
                break;
            case "【绝代风华】":
                break;
            case "【恒古尊耀】":
                break;

        }

        return itemMeta;
    }

    /**
     * 创建世界传送列表
     *
     * @param worldList 世界名称列表
     * @return
     */
    private Inventory createTransferView(List<String> worldList) {
        Inventory inventory = Bukkit.createInventory(null, 54, "所有世界");

        worldList.forEach(s -> {
            ItemStack beacon = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta beaconItemMeta = beacon.getItemMeta();
            beaconItemMeta.setDisplayName(s);
            beacon.setItemMeta(beaconItemMeta);
            inventory.addItem(beacon);
        });


//        ItemStack grassBlock = new ItemStack(Material.GRASS_BLOCK);
//        ItemMeta grassBlockItemMeta = grassBlock.getItemMeta();
//        grassBlockItemMeta.setDisplayName("生存世界");
//        grassBlock.setItemMeta(grassBlockItemMeta);
//        inventory.setItem(2, grassBlock);
//
//        ItemStack diamondOre = new ItemStack(Material.DIAMOND_ORE);
//        ItemMeta diamondOreItemMeta = diamondOre.getItemMeta();
//        diamondOreItemMeta.setDisplayName("资源世界");
//        diamondOre.setItemMeta(diamondOreItemMeta);
//        inventory.setItem(4, diamondOre);
//
//        ItemStack netherrack = new ItemStack(Material.NETHERRACK);
//        ItemMeta netherrackItemMeta = netherrack.getItemMeta();
//        netherrackItemMeta.setDisplayName("地狱");
//        netherrack.setItemMeta(netherrackItemMeta);
//        inventory.setItem(6, netherrack);
//
//        ItemStack endStone = new ItemStack(Material.END_STONE);
//        ItemMeta endStoneItemMeta = endStone.getItemMeta();
//        endStoneItemMeta.setDisplayName("末地");
//        endStone.setItemMeta(endStoneItemMeta);
//        inventory.setItem(8, endStone);
        return inventory;
    }
}

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
import java.util.LinkedList;
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

    List<String> menuList = Arrays.asList("个人信息", "传送菜单", "称号簿");
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
                if (itemMeta.getDisplayName().equals("菜单")) {
                    Inventory menu = CommonMethodUtil.createMenu();
                    player.openInventory(menu);
                }
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
                    case "§5称号簿":
                        Inventory achievementView = this.createAchievementView();
                        player.openInventory(achievementView);
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
     * 创建称号簿视图
     */
    private Inventory createAchievementView() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
        Achievement achievement = achievementMapper.selectById(player.getUniqueId().toString());
        Inventory inventory = Bukkit.createInventory(null, 54, "称号簿");
        //绿色称号
        List<String> greenAchievementList = Arrays.asList("§a【萌新】", "§a【初学者】", "§a【渔夫】", "§a【矿工】", "§a【农夫】");
        this.createAchievement(inventory, achievement, greenAchievementList);
        //蓝色称号
        List<String> blueAchievementList = Arrays.asList("§9【学识者】", "§9【钻石大亨】", "§9【探险家】", "§9【伐木工】", "§9【附魔师】", "§9【巫师】");
        this.createAchievement(inventory, achievement, blueAchievementList);
        return inventory;
    }


    /**
     * 为仓库栏添加称号
     *
     * @param inventory       仓库栏
     * @param achievement     称号
     * @param achievementList 称号列表
     * @return
     */
    private Inventory createAchievement(Inventory inventory, Achievement achievement, List<String> achievementList) {
        //物品栏每一栏9格
        int firstRow = 0, secondRow = 9, thirdRow = 18, fourthRow = 36, fifthRow = 36, sixthRow = 45, seventhRow = 54;
        for (String s : achievementList) {
            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(s);
            this.setAchievementLore(s, achievement, itemMeta);
            itemStack.setItemMeta(itemMeta);
            String substring = s.substring(0, 2);
            switch (substring) {
                case "§a":
                    inventory.setItem(firstRow, itemStack);
                    firstRow++;
                    break;
                case "§9":
                    inventory.setItem(secondRow, itemStack);
                    secondRow++;
            }
        }
        return inventory;
    }

    /**
     * 设置称号详细属性
     *
     * @param achievementName 称号名称
     * @param itemMeta        实体属性
     */
    private ItemMeta setAchievementLore(String achievementName, Achievement achievement, ItemMeta itemMeta) {
        switch (achievementName) {
            case "§a【萌新】":
                List<String> newPeopleList = new LinkedList<>();
                newPeopleList.add("  §d小小新星,初见萌新的标志");
                itemMeta.setLore(newPeopleList);
                break;
            case "§a【初学者】":
                List<String> beginnerList = new LinkedList<>();
                beginnerList.add("  §d略知一二,对事物有了一定认知");
                beginnerList.add("§f达成条件：累计在线24小时");
                if (!achievement.getIsBeginner()) {

                }
                itemMeta.setLore(beginnerList);
                break;
            case "§a【渔夫】":
                List<String> fisherList = new LinkedList<>();
                fisherList.add("  §d悠哉悠哉的垂钓者");
                fisherList.add("§f达成条件：钓上1000条鱼");
                if (!achievement.getIsFisher()) {
                    int fishCount = player.getStatistic(Statistic.FISH_CAUGHT);
                    fisherList.add("§9当前进度： " + fishCount + "/1000");
                }
                itemMeta.setLore(fisherList);
                break;
            case "§a【矿工】":
                List<String> minerList = new LinkedList<>();
                minerList.add("  §d黑乎乎的矿洞工人。");
                minerList.add("§f达成条件：挖掘 铁矿*500 金矿*300");
                if (!achievement.getIsMiner()) {
                    int ironOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE);
                    int goldOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE);
                    minerList.add("§9铁矿： " + ironOreCount + "/500");
                    minerList.add("§9金矿： " + goldOreCount + "/300");
                }
                itemMeta.setLore(minerList);
                break;
            case "§a【农夫】":
                LinkedList<String> farmerList = new LinkedList<>();
                farmerList.add("  §d汗滴禾下土,粒粒皆辛苦");
                farmerList.add("§f达成条件：收获 小麦*500 西瓜*600 南瓜*300");
                if (!achievement.getIsFarmer()) {
                    int wheatCount = player.getStatistic(Statistic.MINE_BLOCK, Material.WHEAT);
                    int melonCount = player.getStatistic(Statistic.PICKUP, Material.MELON_SLICE);
                    int pumpkinCount = player.getStatistic(Statistic.MINE_BLOCK, Material.PUMPKIN);
                    farmerList.add("§9小麦： " + wheatCount + "/500");
                    farmerList.add("§9西瓜： " + melonCount + "/600");
                    farmerList.add("§9南瓜： " + pumpkinCount + "/300");
                }
                itemMeta.setLore(farmerList);
                break;
            //蓝色颜色
            case "【§9学识者】":
                break;
            case "【§9钻石大亨】":

                break;
            case "【§9猎尸者】":
                break;
            case "【§9探险家】":
                break;
            case "【§9伐木工】":
                break;
            case "【§9附魔师】":
                break;
            case "【§9巫师】":
                break;
            case "【§9屠夫】":
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

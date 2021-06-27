package com.zzs.listener;

import com.zzs.MainPlugin;
import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.CommonUtil;
import com.zzs.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 玩家点击监听器
 *
 * @author mountain
 * @since 2021/5/31 23:38
 */
public class MenuClickListener implements Listener {
    static List<String> achievementBookList = Arrays.asList("§a常见", "§9普通", "§5稀有", "§c超稀有", "§d专属", "§6传说", "§b珍§c藏");
    private final MainPlugin plugin;
    List<String> worldList = Arrays.asList("生存世界", "主城", "资源世界", "地狱", "akira_rakani", "末地");

    List<String> menuList = Arrays.asList("个人信息", "传送菜单", "称号簿");

    public MenuClickListener(MainPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Player player;

    /**
     * 创建称号簿视图
     *
     * @param player
     * @param title  容器标题
     * @return
     */
    public static void createAchievementView(Player player, String title) {
        Inventory inventory = Bukkit.createInventory(null, 27, title);
        //创建称号种类
        achievementBookList.forEach(s -> {
            ItemStack itemStack = new ItemStack(Material.BOOK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(s);
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
        });
        player.openInventory(inventory);
    }

    /**
     * 为仓库栏添加称号
     *
     * @param achievementList 称号列表
     * @param player
     * @param title           容器标题
     * @return
     */
    public static void createAchievement(List<String> achievementList, Player player, String title) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        AchievementMapper achievementMapper = sqlSession.getMapper(AchievementMapper.class);
        Achievement achievement = achievementMapper.selectById(player.getUniqueId().toString());
        //去颜色取文字当标题
        String menuTitle = title.substring(2);
        if (menuTitle.contains("珍")) {
            menuTitle = "珍藏";
        }
        Inventory inventory = Bukkit.createInventory(null, 27, menuTitle);
        //物品栏每一栏9格
        int firstRow = 0;
        for (String s : achievementList) {
            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(s);
            setAchievementLore(s, achievement, itemMeta, player);
            String substring = s.substring(0, 2);
            itemStack.setItemMeta(itemMeta);
            switch (substring) {
                case "§a":
                case "§9":
                    inventory.setItem(firstRow, itemStack);
                    firstRow++;
                    break;
                case "§d":
                case "§5":
                case "§c":
                case "§6":
                    appendBoldType(s, itemMeta, substring);
                    inventory.setItem(firstRow, itemStack);
                    firstRow++;
                    break;
                case "§0":
                    StringBuilder builder = new StringBuilder();
                    builder.append(s.substring(2));
                    itemMeta.setDisplayName(builder.toString());
                    inventory.setItem(firstRow, itemStack);
                    firstRow++;
                    break;
            }
        }
        player.openInventory(inventory);
    }

    /**
     * 添加粗体效果
     *
     * @param s
     * @param itemMeta
     * @param substring
     */
    private static void appendBoldType(String s, ItemMeta itemMeta, String substring) {
        StringBuilder builder = new StringBuilder();
        builder.append(substring);
        builder.append("§l").append(s.substring(2));
        itemMeta.setDisplayName(builder.toString());
    }

    /**
     * 设置称号详细属性
     *
     * @param achievementName 称号名称
     * @param itemMeta        实体属性
     */
    public static ItemMeta setAchievementLore(String achievementName, Achievement achievement, ItemMeta itemMeta, Player player) {
        switch (achievementName) {
            case "§a【萌新】":
                List<String> newPeopleList = new LinkedList<>();
                newPeopleList.add("  §d小小新星,初见萌新的标志");
                newPeopleList.add("");
                newPeopleList.add("§f达成条件：成功注册的玩家");
                addFinishText(newPeopleList, player);
                itemMeta.setLore(newPeopleList);
                break;
            case "§a【初学者】":
                List<String> beginnerList = new LinkedList<>();
                beginnerList.add("  §d略知一二,对事物有了一定认知");
                beginnerList.add("");
                beginnerList.add("§f达成条件：累计在线24小时");
                if (achievement.getIsBeginner()) {
                    beginnerList.add("§8当前进度: 24/24小时");
                    addFinishText(beginnerList, player);
                } else {
                    int timeCount = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                    Long hour = Long.valueOf(timeCount / 20 / 3600);
                    beginnerList.add("§7当前进度: " + hour + "/24小时");
                }
                itemMeta.setLore(beginnerList);
                break;
            case "§a【渔夫】":
                List<String> fisherList = new LinkedList<>();
                fisherList.add("  §d悠哉悠哉的垂钓者");
                fisherList.add("");
                fisherList.add("§f达成条件：钓上1000条鱼");
                int fishCount = player.getStatistic(Statistic.FISH_CAUGHT);
                if (achievement.getIsFisher()) {
                    fisherList.add("§8当前进度： 1000/1000");
                    addFinishText(fisherList, player);
                } else {
                    fisherList.add("§7当前进度： " + fishCount + "/1000");
                }
                itemMeta.setLore(fisherList);
                break;
            case "§a【矿工】":
                List<String> minerList = new LinkedList<>();
                minerList.add("  §d灰头土脸的矿洞工人。");
                minerList.add("");
                minerList.add("§f达成条件：挖掘铁矿和金矿");
                if (achievement.getIsMiner()) {
                    minerList.add("§8铁矿： 500/500");
                    minerList.add("§8金矿： 300/300");
                    addFinishText(minerList, player);
                } else {
                    int ironOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE);
                    int goldOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE);
                    minerList.add("§7铁矿： " + ironOreCount + "/500");
                    minerList.add("§7金矿： " + goldOreCount + "/300");
                }
                itemMeta.setLore(minerList);
                break;
            case "§a【农夫】":
                LinkedList<String> farmerList = new LinkedList<>();
                farmerList.add("  §d汗滴禾下土,粒粒皆辛苦");
                farmerList.add("");
                farmerList.add("§f达成条件：收获指定农作物");
                if (achievement.getIsFarmer()) {
                    farmerList.add("§8小麦： 500/500");
                    farmerList.add("§8西瓜： 600/600");
                    farmerList.add("§8南瓜： 300/300");
                    addFinishText(farmerList, player);
                } else {
                    int wheatCount = player.getStatistic(Statistic.MINE_BLOCK, Material.WHEAT);
                    int melonCount = player.getStatistic(Statistic.PICKUP, Material.MELON_SLICE);
                    int pumpkinCount = player.getStatistic(Statistic.MINE_BLOCK, Material.PUMPKIN);
                    farmerList.add("§7小麦： " + wheatCount + "/500");
                    farmerList.add("§7西瓜： " + melonCount + "/600");
                    farmerList.add("§7南瓜： " + pumpkinCount + "/300");
                }
                itemMeta.setLore(farmerList);
                break;
            //蓝色颜色
            case "§9【学识者】":
                LinkedList<String> knowledgePeopleList = new LinkedList<>();
                knowledgePeopleList.add("  §d游荡知识的海洋,上知天文下知地理");
                knowledgePeopleList.add("");
                knowledgePeopleList.add("§f达成条件：累计在线7天");
                if (achievement.getIsKnowledgePeople()) {
                    knowledgePeopleList.add("§8当前进度: 7/7天");
                    addFinishText(knowledgePeopleList, player);
                } else {
                    int timeCount = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                    int day = timeCount / 20 / 3600 / 24;
                    knowledgePeopleList.add("§7当前进度: " + day + "/7天");
                }
                itemMeta.setLore(knowledgePeopleList);
                break;
            case "§9【钻石大亨】":
                LinkedList<String> diamondBigShortList = new LinkedList<>();
                diamondBigShortList.add("  §d幸运与肝力并兼的下矿者");
                diamondBigShortList.add("");
                diamondBigShortList.add("§f达成条件：采掘钻石原矿");
                if (achievement.getIsDiamondBigShort()) {
                    diamondBigShortList.add("§8当前进度： 640/640");
                    addFinishText(diamondBigShortList, player);
                } else {
                    int diamondOreCount = player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE);
                    diamondBigShortList.add("§7当前进度： " + diamondOreCount + "/640");
                }
                itemMeta.setLore(diamondBigShortList);
                break;
            case "§9【猎尸者】":
                LinkedList<String> huntingCorpseList = new LinkedList<>();
                huntingCorpseList.add("  §d僵尸怪物的噩梦");
                huntingCorpseList.add("");
                huntingCorpseList.add("§f达成条件：击杀指定怪物");
                if (achievement.getIsHuntingCorpse()) {
                    huntingCorpseList.add("§8僵尸: 50/50");
                    huntingCorpseList.add("§8僵尸骷髅: 50/50");
                    huntingCorpseList.add("§8僵尸猪人: 30/30");
                    addFinishText(huntingCorpseList, player);
                } else {
                    int zombieKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE);
                    int skeletonKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SKELETON);
                    int zombifiedPigKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIFIED_PIGLIN);
                    huntingCorpseList.add("§7僵尸: " + zombieKills + "/50");
                    huntingCorpseList.add("§7僵尸骷髅: " + skeletonKills + "/50");
                    huntingCorpseList.add("§7僵尸猪人: " + zombifiedPigKills + "/30");
                }
                itemMeta.setLore(huntingCorpseList);
                break;
            case "§9【探险家】":
                LinkedList<String> explorerList = new LinkedList<>();
                explorerList.add("  §d对未知地域的好奇,使你无法停下脚步");
                explorerList.add("");
                explorerList.add("§f达成条件：行走距离达到30000米");
                if (achievement.getIsExplorer()) {
                    explorerList.add("§8当前进度 30000/30000");
                    addFinishText(explorerList, player);
                } else {
                    //行走距离(cm)
                    int walkDistance = player.getStatistic(Statistic.WALK_ONE_CM);
                    //换算成米
                    walkDistance = walkDistance / 100;
                    explorerList.add("§7当前进度 " + walkDistance + "/30000");
                }
                itemMeta.setLore(explorerList);
                break;
            case "§9【伐木工】":
                LinkedList<String> timberjackList = new LinkedList<>();
                timberjackList.add("  §d要想富先撸树");
                timberjackList.add("");
                timberjackList.add("§f达成条件: 砍伐获得指定数量树木");
                if (achievement.getIsTimberjack()) {
                    timberjackList.add("§8橡树木: 640/640");
                    timberjackList.add("§8白桦木: 640/640");
                    timberjackList.add("§8丛林木: 640/640");
                    addFinishText(timberjackList, player);
                } else {
                    //橡树木
                    int oakLogCount = player.getStatistic(Statistic.MINE_BLOCK, Material.OAK_LOG);
                    //白桦木
                    int birchLogCount = player.getStatistic(Statistic.MINE_BLOCK, Material.BIRCH_LOG);
                    //从林木
                    int jungleLogCount = player.getStatistic(Statistic.MINE_BLOCK, Material.JUNGLE_LOG);
                    timberjackList.add("§7橡树木: " + oakLogCount + "/640");
                    timberjackList.add("§7白桦木: " + birchLogCount + "/640");
                    timberjackList.add("§7丛林木: " + jungleLogCount + "/640");
                }
                itemMeta.setLore(timberjackList);
                break;
            case "§9【附魔师】":
                LinkedList<String> enchanterList = new LinkedList<>();
                enchanterList.add("  §d将书籍知识运用到实际中的伟大附魔师");
                enchanterList.add("");
                enchanterList.add("§f达成条件: 累计附魔达到指定次数");
                if (achievement.getIsEnchanter()) {
                    enchanterList.add("§8当前进度: 50/50");
                    addFinishText(enchanterList, player);
                } else {
                    int itemEnchantedCount = player.getStatistic(Statistic.ITEM_ENCHANTED);
                    enchanterList.add("§7当前进度: " + itemEnchantedCount + "/50");
                }
                itemMeta.setLore(enchanterList);
                break;
            /*case "§9【巫师】":
                break;*/
            case "§9【屠夫】":
                LinkedList<String> butcherList = new LinkedList<>();
                butcherList.add("  §d身上的杀气散发,使他们变得焦躁不安");
                butcherList.add("");
                butcherList.add("§f达成条件: 击杀指定动物种类");
                if (achievement.getIsButcher()) {
                    butcherList.add("§8羊: 50/50");
                    butcherList.add("§8牛: 50/50");
                    butcherList.add("§8鸡: 50/50");
                    addFinishText(butcherList, player);
                } else {
                    int sheepKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.SHEEP);
                    int cowKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.COW);
                    int chickenKills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.CHICKEN);
                    butcherList.add("§7羊: " + sheepKills + "/50");
                    butcherList.add("§7牛: " + cowKills + "/50");
                    butcherList.add("§7鸡: " + chickenKills + "/50");
                }
                itemMeta.setLore(butcherList);
                break;
            case "§d【倾国倾城】":
                LinkedList<String> aCompleteOneOffList = new LinkedList<>();
                aCompleteOneOffList.add("  §d认证专属，认证请找服主/管理");
                aCompleteOneOffList.add("");
                aCompleteOneOffList.add("§f达成条件: 女村民限定");
                if (achievement.getIsTheEmpressDowager()) {
                    addFinishText(aCompleteOneOffList, player);
                }
                itemMeta.setLore(aCompleteOneOffList);
                break;
            case "§d【B站主播】":
                LinkedList<String> biliAnchorList = new LinkedList<>();
                biliAnchorList.add("  §d认证专属，认证请找服主/管理");
                biliAnchorList.add("");
                biliAnchorList.add("§f达成条件: 在B站有自己的直播间");
                if (achievement.getIsBiliAnchor()) {
                    addFinishText(biliAnchorList, player);
                }
                itemMeta.setLore(biliAnchorList);
                break;
            case "【元老】":
                LinkedList<String> oldHeadList = new LinkedList<>();
                oldHeadList.add("  §d服务器终极老玩家,随时可见。");
                oldHeadList.add("");
                oldHeadList.add("§f达成条件: ");
                if (achievement.getIsOldHead()) {
                    oldHeadList.add("  -累计签到：60/60天");
                    oldHeadList.add("  -拥有金币：500000/500000");
                    oldHeadList.add("  -拥有领地：2/2");
                    addFinishText(oldHeadList, player);
                } else {
                    oldHeadList.add("  -累计签到：0/60天");
                    oldHeadList.add("  -拥有金币：0/500000");
                    oldHeadList.add("  -拥有领地：0/2");
                }
                itemMeta.setLore(oldHeadList);
                break;
            case "【小财主】":
                LinkedList<String> smallRichManList = new LinkedList<>();
                smallRichManList.add("  §d腰缠万贯的阔佬,小意思。");
                smallRichManList.add("");
                smallRichManList.add("§f达成条件: 主城商店购买");
                if (achievement.getIsSmallRichMan()) {
                    smallRichManList.add("§8当前进度: 1/1");
                    addFinishText(smallRichManList, player);
                } else {
                    smallRichManList.add("§7当前进度: 0/1");
                }
                itemMeta.setLore(smallRichManList);
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
     * 添加称号完成时的文本内容
     *
     * @param list
     * @param player
     * @return
     */
    private static void addFinishText(List<String> list, Player player) {
        String title = player.getOpenInventory().getTitle();
        if (title.equals("称号簿")) {
            //从称号簿进入时
            list.add("");
            list.add("");
            list.add("  §a已获得,可前往主城NPC§d 称号使者 §a变更称号");
        } else {
            //从称号使者点击时
            list.add("");
            list.add("");
            list.add("  §a点击称号即可佩带");
        }
    }

    /**
     * 玩家点击事件
     *
     * @param event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        //打开背包状态下右键打开菜单栏
        if (event.getClick().equals(ClickType.RIGHT) && currentItem != null) {
            ItemMeta itemMeta = currentItem.getItemMeta();
            if (itemMeta != null) {
                if (itemMeta.getDisplayName().equals("菜单")) {
                    Inventory menu = CommonUtil.createMenu();
                    player.openInventory(menu);
                }
            }
        }

        //左键点击触发
        if (event.getClick().equals(ClickType.LEFT) && currentItem != null) {
            ItemMeta itemMeta = currentItem.getItemMeta();
            if (itemMeta != null) {
                String displayName = itemMeta.getDisplayName();
                //主菜单
                switch (displayName) {
                    case "个人信息":
                        event.setCancelled(true);
                        break;
                    case "称号簿":
                        this.createAchievementView(player, "称号簿");
                        break;
                    case "§a常见":
                        //绿色称号
                        List<String> greenAchievementList = Arrays.asList("§a【萌新】", "§a【初学者】", "§a【渔夫】", "§a【矿工】", "§a【农夫】");
                        createAchievement(greenAchievementList, player, displayName);
                        break;
                    case "§9普通":
                        //蓝色称号
                        List<String> blueAchievementList = Arrays.asList("§9【学识者】", "§9【钻石大亨】", "§9【猎尸者】", "§9【探险家】", "§9【伐木工】", "§9【附魔师】"/*, "§9【巫师】"*/);
                        createAchievement(blueAchievementList, player, displayName);
                        break;
                    case "§5稀有":
                        //粗紫色
                        List<String> boldPurpleAchievementList = Arrays.asList("§5【元老】", "§5【头颅收集者】");
                        createAchievement(boldPurpleAchievementList, player, displayName);
                        break;
                    case "§d专属":
                        //粗粉色
                        List<String> boldPinkAchievementList = Arrays.asList("§d【虎牙主播】", "§d【B站主播】", "§d【倾国倾城】");
                        createAchievement(boldPinkAchievementList, player, displayName);
                        break;
                    case "§c超稀有":
                        //粗红色
                        List<String> boldRedAchievementList = Arrays.asList("§c【杀戮者】", "§c【巡查组】");
                        createAchievement(boldRedAchievementList, player, displayName);
                        break;
                    case "§6传说":
                        //粗金色
                        List<String> boldGoldenAchievementList = Arrays.asList("§6【财大气粗】", "§6【小财主】", "§6【空前绝后】");
                        createAchievement(boldGoldenAchievementList, player, displayName);
                        break;
                    case "§b珍§c藏":
                        //粗彩色
                        List<String> boldColoursAchievementList = Arrays.asList("§0§a【§c§l吉§e§l祥§d§l物§a】", "§0§a【§9§l绝§e§l代§d§l风§b§l华§a】", "§0§a【§4§l恒§1§l古§2§l尊§5§l耀§a】");
                        createAchievement(boldColoursAchievementList, player, displayName);
                        break;
                    case "传送菜单":
                        Inventory teleportMenu = this.createTransferView();
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
                    /*case "资源世界":
                        World world_resource = plugin.getServer().getWorld("world_resource");
                        player.teleport(world_resource.getSpawnLocation());
                        break;*/
                    case "地狱":
                        World world_nether = plugin.getServer().getWorld("world_nether");
                        player.teleport(world_nether.getSpawnLocation());
                        break;
                    case "末地":
                        World world_the_end = plugin.getServer().getWorld("world_the_end");
                        player.teleport(world_the_end.getSpawnLocation());
                        break;
                    case "akira_rakani":
                        World akira_rakani = plugin.getServer().getWorld("akira_rakani");
                        player.teleport(akira_rakani.getSpawnLocation());
                        break;
                }
            }
        }

        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("菜单栏");
        titleList.add("所有世界");
        titleList.add("称号簿");
        achievementBookList.forEach(s -> {
            String menuTitle = s.substring(2);
            if (menuTitle.contains("珍")) {
                menuTitle = "珍藏";
            }
            titleList.add(menuTitle);
        });

        //防止玩家将物品转移到菜单物品栏内
        titleList.forEach(s -> {
            if (event.getView().getTitle().contains(s)) {
                event.setCancelled(true);
            }
        });
    }

    /**
     * 创建世界传送列表
     *
     * @return
     */
    private Inventory createTransferView() {
        Inventory inventory = Bukkit.createInventory(null, 54, "所有世界");
        ItemStack main = new ItemStack(Material.BEACON);
        ItemMeta mainItemMeta = main.getItemMeta();
        mainItemMeta.setDisplayName("主城");
        main.setItemMeta(mainItemMeta);
        inventory.addItem(main);

        ItemStack grassBlock = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta grassBlockItemMeta = grassBlock.getItemMeta();
        grassBlockItemMeta.setDisplayName("生存世界");
        grassBlock.setItemMeta(grassBlockItemMeta);
        inventory.setItem(2, grassBlock);

       /* ItemStack diamondOre = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta diamondOreItemMeta = diamondOre.getItemMeta();
        diamondOreItemMeta.setDisplayName("资源世界");
        diamondOre.setItemMeta(diamondOreItemMeta);
        inventory.setItem(4, diamondOre);*/

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

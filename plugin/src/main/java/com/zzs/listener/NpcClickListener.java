package com.zzs.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zzs.MainPlugin;
import com.zzs.dao.AchievementMapper;
import com.zzs.entity.Achievement;
import com.zzs.util.Const;
import com.zzs.util.SqlSessionUtil;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


/**
 * @author mountain
 * @since 2021/6/23 17:02
 */
public class NpcClickListener implements Listener {
    private final MainPlugin plugin;

    private Player player;

    public NpcClickListener(MainPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onNpcRightClickEvent(NPCRightClickEvent event) {
        player = event.getClicker();
        if (event.getNPC().getFullName().equals("§e称号使者")) {
            MenuClickListener.createAchievementView(player, "称号");
        }
    }

    @EventHandler
    public void onClickAchievement(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        if (event.getClick().equals(ClickType.LEFT) && currentItem != null) {
            //称号使者打开左键可佩带
            ItemMeta itemMeta = currentItem.getItemMeta();
            if (itemMeta != null) {
                String displayName = itemMeta.getDisplayName();
                if (player != null) {
                    InventoryView openInventory = player.getOpenInventory();
                    if (openInventory != null) {
                        MenuClickListener.achievementBookList.forEach(s -> {
                            String menuTitle = s.substring(2);
                            if (menuTitle.contains("珍")) {
                                menuTitle = "珍藏";
                            }

                            if (openInventory.getTitle().equals(menuTitle) && !CollectionUtils.isEmpty(itemMeta.getLore())) {
                                List<String> lore = itemMeta.getLore();
                                switch (displayName) {
                                    case "§a[萌新]":
                                        this.useAchievementName(Boolean.TRUE, displayName, openInventory, lore);
                                        break;
                                    case "§a[初学者]":
                                        Boolean is_beginner = this.getAchievementStatus("is_beginner").getIsBeginner();
                                        this.useAchievementName(is_beginner, displayName, openInventory, lore);
                                        break;
                                    case "§a[渔夫]":
                                        Boolean is_fisher = this.getAchievementStatus("is_fisher").getIsFisher();
                                        this.useAchievementName(is_fisher, displayName, openInventory, lore);
                                        break;
                                    case "§a[矿工]":
                                        Boolean is_miner = this.getAchievementStatus("is_miner").getIsMiner();
                                        this.useAchievementName(is_miner, displayName, openInventory, lore);
                                        break;
                                    case "§a[农夫]":
                                        Boolean is_farmer = this.getAchievementStatus("is_farmer").getIsFarmer();
                                        this.useAchievementName(is_farmer, displayName, openInventory, lore);
                                        break;
                                    //蓝色颜色
                                    case "§9[学识者]":
                                        Boolean is_knowledge_people = this.getAchievementStatus("is_knowledge_people").getIsKnowledgePeople();
                                        this.useAchievementName(is_knowledge_people, displayName, openInventory, lore);
                                        break;
                                    case "§9[钻石大亨]":
                                        Boolean is_diamond_big_short = this.getAchievementStatus("is_diamond_big_short").getIsDiamondBigShort();
                                        this.useAchievementName(is_diamond_big_short, displayName, openInventory, lore);
                                        break;
                                    case "§9[猎尸者]":
                                        Boolean is_hunting_corpse = this.getAchievementStatus("is_hunting_corpse").getIsHuntingCorpse();
                                        this.useAchievementName(is_hunting_corpse, displayName, openInventory, lore);
                                        break;
                                    case "§9[探险家]":
                                        Boolean is_explorer = this.getAchievementStatus("is_explorer").getIsExplorer();
                                        this.useAchievementName(is_explorer, displayName, openInventory, lore);
                                        break;
                                    case "§9[伐木工]":
                                        Boolean is_timberjack = this.getAchievementStatus("is_timberjack").getIsTimberjack();
                                        this.useAchievementName(is_timberjack, displayName, openInventory, lore);
                                        break;
                                    case "§9[附魔师]":
                                        Boolean is_enchanter = this.getAchievementStatus("is_enchanter").getIsEnchanter();
                                        this.useAchievementName(is_enchanter, displayName, openInventory, lore);
                                        break;
            /*case "§9[巫师]":
                break;*/
                                    case "§9[屠夫]":
                                        Boolean is_butcher = this.getAchievementStatus("is_butcher").getIsButcher();
                                        this.useAchievementName(is_butcher, displayName, openInventory, lore);
                                        break;
                                    //粗字体粉色颜色
                                    case "[倾国倾城]":
                                        Boolean is_the_empress_dowager = this.getAchievementStatus("is_the_empress_dowager").getIsTheEmpressDowager();
                                        this.useAchievementName(is_the_empress_dowager, displayName, openInventory, lore);
                                        break;
                                    case "[B站主播]":
                                        Boolean is_bili_anchor = this.getAchievementStatus("is_bili_anchor").getIsBiliAnchor();
                                        this.useAchievementName(is_bili_anchor, displayName, openInventory, lore);
                                        break;
                                    //粗字体紫色
                                    case "[元老]":
                                        Boolean is_old_head = this.getAchievementStatus("is_old_head").getIsOldHead();
                                        this.useAchievementName(is_old_head, displayName, openInventory, lore);
                                        break;
                                    case "[小财主]":
                                        Boolean is_small_rich_man = this.getAchievementStatus("is_small_rich_man").getIsSmallRichMan();
                                        this.useAchievementName(is_small_rich_man, displayName, openInventory, lore);
                                        break;
                                    case "[杀戮者]":
                                        Boolean is_player_killer = this.getAchievementStatus("is_player_killer").getIsPlayerKiller();
                                        this.useAchievementName(is_player_killer, displayName, openInventory, lore);
                                        break;
                                    case "[巡查组]":
                                        Boolean is_patrol_group = this.getAchievementStatus("is_patrol_group").getIsPatrolGroup();
                                        this.useAchievementName(is_patrol_group, displayName, openInventory, lore);
                                        break;
                                    //粗字体金色
                                    case "[虎牙主播]":
                                        Boolean is_hu_ya_anchor = this.getAchievementStatus("is_hu_ya_anchor").getIsHuYaAnchor();
                                        this.useAchievementName(is_hu_ya_anchor, displayName, openInventory, lore);
                                        break;
                                    case "[空前绝后]":
                                        Boolean is_a_complete_one_off = this.getAchievementStatus("is_a_complete_one_off").getIsACompleteOneOff();
                                        this.useAchievementName(is_a_complete_one_off, displayName, openInventory, lore);
                                        break;
                                    case "[财大气粗]":
                                        Boolean is_ostentatious = this.getAchievementStatus("is_ostentatious").getIsOstentatious();
                                        this.useAchievementName(is_ostentatious, displayName, openInventory, lore);
                                        break;
                                    case "[头颅收集者]":
                                        Boolean is_head_collector = this.getAchievementStatus("is_head_collector").getIsHeadCollector();
                                        this.useAchievementName(is_head_collector, displayName, openInventory, lore);
                                        break;
                                    //粗字体彩色
                                    case "[吉祥物]":
                                        Boolean is_mascot = this.getAchievementStatus("is_mascot").getIsMascot();
                                        this.useAchievementName(is_mascot, displayName, openInventory, lore);
                                        break;
                                    case "[绝代风华]":
                                        Boolean is_timeless_icon = this.getAchievementStatus("is_timeless_icon").getIsTimelessIcon();
                                        this.useAchievementName(is_timeless_icon, displayName, openInventory, lore);
                                        break;
                                    case "[恒古尊耀]":
                                        Boolean is_heng_gu_zun_yao = this.getAchievementStatus("is_heng_gu_zun_yao").getIsHengGuZunYao();
                                        this.useAchievementName(is_heng_gu_zun_yao, displayName, openInventory, lore);
                                        break;
                                }
                            }
                        });
                    }
                }

                //防止玩家移动物品到称号栏内
                if (event.getView().getTitle().equals("称号")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * 佩带称号
     *
     * @param achievementStatus 称号获得状态
     * @param displayName       命名牌物品的自定义名字
     * @param openInventory     打开的容器
     * @param lore
     */
    private void useAchievementName(Boolean achievementStatus, String displayName, InventoryView openInventory, List<String> lore) {
        if (achievementStatus) {
            if (lore.contains("  §a点击称号即可佩带")) {
                player.setDisplayName(displayName + "§f " + player.getName());
                player.setPlayerListName(displayName + "§f " + player.getName());
                player.performCommand("nickname " + player.getDisplayName());
            }
            if (lore.contains("  §a已获得,可前往主城NPC§d 称号使者 §a变更称号")) {
                player.sendMessage(Const.SYSTEM_PREFIX + "佩戴称号可前往主城寻找NPC§d 称号使者");
            }
        } else {
            player.sendMessage(Const.SYSTEM_PREFIX + "§a您还未获得该称号,请查看称号获得条件");
        }
        openInventory.close();
    }

    /**
     * 获取称号获得状态
     */
    private Achievement getAchievementStatus(String columnName) {
        SqlSessionFactory sqlSessionFactory = SqlSessionUtil.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AchievementMapper achievementMapper = session.getMapper(AchievementMapper.class);
            QueryWrapper<Achievement> wrapper = new QueryWrapper<>();
            wrapper.eq("uuid", player.getUniqueId().toString());
            wrapper.select(columnName);
            Achievement achievement = achievementMapper.selectOne(wrapper);
            return achievement;
        }
    }

}

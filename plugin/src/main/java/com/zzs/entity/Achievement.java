package com.zzs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 称号
 *
 * @author mm013
 * @Date 2021/6/4 9:40
 */
@TableName(value = "t_achievement")
@Data
public class Achievement implements Serializable {
    @TableId(value = "uuid")
    private String uuid;

    private String userName;

    /**
     * 当前使用称号
     */
    public enum useAchievement {
        /**
         * 萌新
         */
        NEW_PEOPLE,
        /**
         * 初学者
         */
        BEGINNER,
        /**
         * 渔夫
         */
        FISHER,
        /**
         * 矿工
         */
        MINER,
        /**
         * 农夫
         */
        FARMER,
        /**
         * 学识者
         */
        KNOWLEDGE_PEOPLE,
        /**
         * 钻石大亨
         */
        DIAMOND_BIG_SHORT,
        /**
         * 猎尸者
         */
        HUNTING_CORPSE,
        /**
         * 探险家
         */
        EXPLORER,
        /**
         * 伐木工
         */
        TIMBERJACK,
        /**
         * 附魔师
         */
        ENCHANTER,
        /**
         * 巫师
         */
        SORCERER,
        /**
         * 屠夫
         */
        BUTCHER,
        /**
         * 倾国倾城
         */
        THE_EMPRESS_DOWAGER,
        /**
         * B站主播
         */
        BILI_ANCHOR,
        /**
         * 元老
         */
        OLD_HEAD,
        /**
         * 小财主
         */
        SMALL_RICH_MAN,
        /**
         * 杀戮者
         */
        PLAYER_KILLER,
        /**
         * 巡查组
         */
        PATROL_GROUP,
        /**
         * 虎牙主播
         */
        HU_YA_ANCHOR,
        /**
         * 空前绝后
         */
        A_COMPLETE_ONE_OFF,
        /**
         * 财大气粗
         */
        OSTENTATIOUS,
        /**
         * 头颅收集者
         */
        HEAD_COLLECTOR,
        /**
         * 吉祥物
         */
        MASCOT,
        /**
         * 风华绝代
         */
        TIMELESS_ICON,
        /**
         * 恒古尊耀
         */
        HENG_GU_ZUN_YAO

    }

    /**
     * 【萌新】
     */
    private Boolean isNewPeople;
    /**
     * 【初学者】
     */
    private Boolean isBeginner;
    /**
     * 【渔夫】
     */
    private Boolean isFisher;
    /**
     * 【矿工】
     */
    private Boolean isMiner;
    /**
     * 【农夫】
     */
    private Boolean isFarmer;
    /**
     * 【学识者】
     */
    private Boolean isKnowledgePeople;
    /**
     * 【钻石大亨】
     */
    private Boolean isDiamondBigShort;
    /**
     * 【猎尸者】
     */
    private Boolean isHuntingCorpse;
    /**
     * 【探险家】
     */
    private Boolean isExplorer;
    /**
     * 【伐木工】
     */
    private Boolean isTimberjack;
    /**
     * 【附魔师】
     */
    private Boolean isEnchanter;
    /**
     * 【巫师】
     */
    private Boolean isSorcerer;
    /**
     * 【屠夫】
     */
    private Boolean isButcher;
    /**
     * 【倾国倾城】
     */
    private Boolean isTheEmpressDowager;
    /**
     * 【B站主播】
     */
    private Boolean isBiliAnchor;
    /**
     * 【元老】
     */
    private Boolean isOldHead;
    /**
     * 【小财主】
     */
    private Boolean isSmallRichMan;
    /**
     * 【杀戮者】
     */
    private Boolean isPlayerKiller;
    /**
     * 【巡查组】
     */
    private Boolean isPatrolGroup;
    /**
     * 【虎牙主播】
     */
    private Boolean isHuYaAnchor;
    /**
     * 【空前绝后】
     */
    private Boolean isACompleteOneOff;
    /**
     * 【财大气粗】
     */
    private Boolean isOstentatious;
    /**
     * 【头颅收集者】
     */
    private Boolean isHeadCollector;
    /**
     * 【吉祥物】
     */
    private Boolean isMascot;
    /**
     * 【绝代风华】
     */
    private Boolean isTimelessIcon;
    /**
     * 【恒古尊耀】
     */
    private Boolean isHengGuZunYao;
}

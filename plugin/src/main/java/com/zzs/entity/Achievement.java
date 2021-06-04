package com.zzs.entity;

import lombok.Data;

/**
 * 称号
 *
 * @author mm013
 * @Date 2021/6/4 9:40
 */
@Data
public class Achievement {
    private Long id;

    private String uuid;

    public enum achievement {
        /**
         * 【萌新】
         */
        NEW_PEOPLE,
        /**
         * 【初学者】
         */
        BEGINNER,
        /**
         * 【渔夫】
         */
        FISHER,
        /**
         * 【矿工】
         */
        MINER,
        /**
         * 【农夫】
         */
        FARMER,
        /**
         * 【学识者】
         */
        KNOWLEDGE_PEOPLE,
        /**
         * 【钻石大亨】
         */
        DIAMOND_BIG_SHORT,
        /**
         * 【猎尸者】
         */
        RESIN_IS_HUNTING,
        /**
         * 【探险家】
         */
        EXPLORER,
        /**
         * 【伐木工】
         */
        TIMBERJACK,
        /**
         * 【附魔师】
         */
        ENCHANTER,
        /**
         * 【巫师】
         */
        SORCERER,
        /**
         * 【屠夫】
         */
        BUTCHER,
        /**
         * 【倾国倾城】
         */
        BEAUTY_WHICH_OVERTHROWS_STATES_AND_CICTES,
        /**
         * 【B站主播】
         */
        BILIBILI_ANCHOR,
        /**
         * 【虎牙主播】
         */
        HUYA_ANCHOR,
        /**
         * 【元老】
         */
        OLD_HEAD,
        /**
         * 【小财主】
         */
        SMALL_RICH_MAN,
        /**
         * 【杀戮者】
         */
        PLAYER_KILLER,
        /**
         * 【巡查组】
         */
        PATROL_GROUP,
        /**
         * 【空前绝后】
         */
        THE_FIRST_AND_THE_LAST,
        /**
         * 【财大气粗】
         */
        OSTENTATIOUS,
        /**
         * 【头颅收集者】
         */
        HEAD_COLLECTOR,
        /**
         * 【吉祥物】
         */
        MASCOT,
        /**
         * 【绝代风华】
         */
        TIMELESS_ICON,
        /**
         * 【恒古尊耀】
         */
        HENG_GU_ZUN_YAO

    }
}

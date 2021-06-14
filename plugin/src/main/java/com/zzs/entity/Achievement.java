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

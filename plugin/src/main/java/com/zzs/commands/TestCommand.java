package com.zzs.commands;

import com.zzs.MainPlugin;
import com.zzs.util.CommonUtil;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author mm013
 * @Date 2021/6/8 16:14
 */
public class TestCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public TestCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = CommonUtil.isPlayer(sender);
        if (player == null) {
            return false;
        }
        if (command.getName().equalsIgnoreCase("diamondSword")) {
            // 钻石剑
            ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§6金色传说");
            itemStack.setItemMeta(itemMeta);
            itemMeta.setLore(Arrays.asList("§5传说中的石中剑"));

            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "开发者", 500L, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attributeModifier);
            //锋利
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
            //火焰效果
            itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 6, true);
            //不死
            itemMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 6, true);
            //节肢动物
            itemMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 6, true);
            //横扫之刃
            itemMeta.addEnchant(Enchantment.SWEEPING_EDGE, 6, true);
            itemStack.setItemMeta(itemMeta);
            PlayerInventory inventory = player.getInventory();
            inventory.addItem(itemStack);
            return true;
        }
        if (command.getName().equalsIgnoreCase("test")) {
            PlayerInventory inventory = player.getInventory();
            ItemStack itemStack = new ItemStack(Material.CLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("菜单");
            itemMeta.setLore(Arrays.asList("右键打开菜单栏"));
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
            return true;
        }
        return false;
    }
}

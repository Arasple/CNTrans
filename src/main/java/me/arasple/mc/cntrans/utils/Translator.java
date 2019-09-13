package me.arasple.mc.cntrans.utils;

import com.google.common.collect.Lists;
import com.luhuiguo.chinese.ChineseUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Arasple
 * @date 2019/9/13 22:24
 */
public class Translator {

    public static void translateItemStack(ItemStack bukkitStack, String toLocale) {
        if (bukkitStack == null || bukkitStack.getType() == Material.AIR) {
            return;
        }
        ItemMeta meta = bukkitStack.getItemMeta();

        if (meta != null) {
            if (meta.hasLore() && meta.getLore() != null && meta.getLore().size() > 0) {
                List<String> lores = Lists.newArrayList();
                meta.getLore().forEach(l -> lores.add(translateString(l, toLocale)));
                meta.setLore(lores);
            }
            if (meta.hasDisplayName()) {
                String tran = translateString(meta.getDisplayName(), toLocale);
                meta.setDisplayName(meta.getDisplayName().charAt(0) != ChatColor.COLOR_CHAR ? ChatColor.RESET + tran : tran);
            }
            bukkitStack.setItemMeta(meta);
        }
    }

    public static String translateString(String string, String toLocale) {
        return "zh_cn".equals(toLocale) ? ChineseUtils.toSimplified(string) : ChineseUtils.toTraditional(string);
    }

}

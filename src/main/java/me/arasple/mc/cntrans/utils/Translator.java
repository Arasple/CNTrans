package me.arasple.mc.cntrans.utils;

import com.google.common.collect.Lists;
import com.luhuiguo.chinese.ChineseUtils;
import me.arasple.mc.cntrans.CNTrans;
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

    private static int count = 0;

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
        count++;
        return "zh_cn".equals(toLocale) ? toSimplified(string) : toTraditional(string);
    }

    private static String toSimplified(String string) {
        for (String key : CNTrans.getTranslations().getConfigurationSection(serverLocale).getKeys(false)) {
            string = string.replace(key, CNTrans.getTranslations().getString(serverLocale + "." + key));
        }
        return ChineseUtils.toSimplified(string);
    }

    private static String toTraditional(String string) {
        for (String key : CNTrans.getTranslations().getConfigurationSection(serverLocale).getKeys(false)) {
            string = string.replace(key, CNTrans.getTranslations().getString(serverLocale + "." + key));
        }
        return ChineseUtils.toTraditional(string);
    }

    private static String serverLocale = CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "ZH_CN").toUpperCase();

    public static int getAndRestCount() {
        int num = count;
        count = 0;
        return num;
    }

}

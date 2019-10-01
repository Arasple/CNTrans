package me.arasple.mc.cntrans.bstats;

import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.inject.TSchedule;
import me.arasple.mc.cntrans.CNTrans;
import me.arasple.mc.cntrans.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.util.NumberConversions;

/**
 * @author Arasple
 */
public class Metrics {

    private static MetricsBukkit metrics;

    @TSchedule
    public static void init() {
        metrics = new MetricsBukkit(CNTrans.getPlugin());

        // 更新检测器
        metrics.addCustomChart(new MetricsBukkit.SimplePie("update_checker", () -> CNTrans.getSettings().getBoolean("GENERAL.CHECK-UPDATE", true) ? "Enabled" : "Disabled"));
        // 服务器内容语言
        metrics.addCustomChart(new MetricsBukkit.SimplePie("server_locale", () -> "zh_cn".equalsIgnoreCase(CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "zh_cn")) ? "zh_CN" : "zh_TW"));
        // 客户端语言切换时提醒
        metrics.addCustomChart(new MetricsBukkit.SimplePie("notify_client", () -> CNTrans.getSettings().getBoolean("NOTIFY", true) ? "Enabled" : "Disabled"));
        // 翻译 - 聊天
        metrics.addCustomChart(new MetricsBukkit.SimplePie("translate_chat", () -> CNTrans.getSettings().getBoolean("TRANSLATIONS.CHAT", true) ? "Enabled" : "Disabled"));
        // 翻译 - 物品
        metrics.addCustomChart(new MetricsBukkit.SimplePie("translate_itemstack", () -> CNTrans.getSettings().getBoolean("TRANSLATIONS.ITEM", true) ? "Enabled" : "Disabled"));
        // 翻译 - Title
        metrics.addCustomChart(new MetricsBukkit.SimplePie("translate_title", () -> CNTrans.getSettings().getBoolean("TRANSLATIONS.TITLE", true) ? "Enabled" : "Disabled"));
        // 翻译 - Tablist
        metrics.addCustomChart(new MetricsBukkit.SimplePie("translate_tablist", () -> CNTrans.getSettings().getBoolean("TRANSLATIONS.TABLIST", true) ? "Enabled" : "Disabled"));
        // 翻译 - 记分板
        metrics.addCustomChart(new MetricsBukkit.SimplePie("translate_gui_only", () -> CNTrans.getSettings().getBoolean("OPTIONS.GUI-ONLY", true) ? "Enabled" : "Disabled"));
        // 翻译 - 木牌
        metrics.addCustomChart(new MetricsBukkit.SimplePie("translate_sign", () -> CNTrans.getSettings().getBoolean("TRANSLATIONS.SIGN", true) ? "Enabled" : "Disabled"));
        // 为多少名在线玩家提供翻译中
        metrics.addCustomChart(new MetricsBukkit.SingleLineChart("players_using_translator", () -> NumberConversions.toInt(Bukkit.getOnlinePlayers().stream().filter(p -> {
            String serverLocale = CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "zh_cn");
            String locale = LocalPlayer.get(p).getString("CNTrans.LOCALE", serverLocale);

            return !serverLocale.equals(locale);
        }).count())));
        // 翻译了多少次
        metrics.addCustomChart(new MetricsBukkit.SingleLineChart("translation_counts", Translator::getAndRestCount));
    }


    public static MetricsBukkit getMetrics() {
        return metrics;
    }

}
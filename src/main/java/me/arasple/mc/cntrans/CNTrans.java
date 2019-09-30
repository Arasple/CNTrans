package me.arasple.mc.cntrans;

import com.luhuiguo.chinese.ChineseUtils;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.dependency.Dependency;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.module.locale.logger.TLogger;
import org.bukkit.event.Listener;

/**
 * @author Arasple
 */
@CNTransPlugin.Version(5.06)
@Dependency(maven = "com.luhuiguo:chinese-utils:1.0")
public final class CNTrans extends CNTransPlugin implements Listener {

    @TInject("settings.yml")
    private static TConfig settings;
    @TInject("§6CN§eTrans")
    private static TLogger logger;

    @Override
    public void onStarting() {
        ChineseUtils.toTraditional("初始化词库");
        TLocale.sendToConsole("PLUGIN.ENABLED", getDescription().getVersion());
    }

    @Override
    public void onStopping() {
        TLocale.sendToConsole("PLUGIN.DISABLED");
    }

    public static TLogger getTLogger() {
        return logger;
    }

    public static TConfig getSettings() {
        return settings;
    }

}

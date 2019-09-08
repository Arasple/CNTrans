package me.arasple.mc.cntrans;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.dependency.Dependency;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * @author Arasple
 */
@CNTransPlugin.Version(5.05)
@Dependency(maven = "com.github.houbb:opencc4j:1.1.0")
@Dependency(maven = "com.huaban:jieba-analysis:1.0.2")
public final class CNTrans extends CNTransPlugin implements Listener {

    @TInject("config.yml")
    private static TConfig config;

    @Override
    public void onStarting() {
        ZhConverterUtil.convertToTraditional("初始化词库");
        CommandBuilder.create("zh_CN", this)
                .execute((sender, args) -> {
                    if (sender instanceof Player) {
                        if (LocalPlayer.get((Player) sender).get("CNTrans.LOCALE", "zh_cn") == "zh_cn") {
                            TLocale.sendTo(sender, "CN-CHANGED.SWITCHED");
                        } else {
                            LocalPlayer.get((Player) sender).set("CNTrans.LOCALE", "zh_cn");
                            TLocale.sendTo(sender, "CN-CHANGED.ZHCN");
                        }
                    }
                })
                .build();
        CommandBuilder.create("zh_TW", this)
                .execute((sender, args) -> {
                    if (sender instanceof Player) {
                        if (LocalPlayer.get((Player) sender).get("CNTrans.LOCALE", "zh_cn") == "zh_tw") {
                            TLocale.sendTo(sender, "CN-CHANGED.SWITCHED");
                        } else {
                            LocalPlayer.get((Player) sender).set("CNTrans.LOCALE", "zh_tw");
                            TLocale.sendTo(sender, "CN-CHANGED.ZHTW");
                        }
                    }
                })
                .build();
    }

    public static TConfig getCfg() {
        return config;
    }

}

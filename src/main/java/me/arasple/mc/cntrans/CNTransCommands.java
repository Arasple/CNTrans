package me.arasple.mc.cntrans;

import io.izzel.taboolib.module.command.base.BaseCommand;
import io.izzel.taboolib.module.command.base.BaseMainCommand;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.inject.TSchedule;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Arasple
 * @date 2019/9/12 18:20
 * -
 * /cntrans switch *在 繁体中文/简体中文之间 切换
 * /zh_CN 切换到简体中文
 * /zh_TW 切换到繁体中文
 */
@BaseCommand(name = "cntrans", aliases = "cnlang", permission = "cntrans.use")
public class CNTransCommands extends BaseMainCommand {

    @TSchedule
    public static void init() {
        CommandBuilder.create("zh_CN", CNTrans.getPlugin())
                .execute((sender, args) -> {
                    if (sender instanceof Player) {
                        if (LocalPlayer.get((Player) sender).get("CNTrans.LOCALE", "zh_cn") == "zh_cn") {
                            TLocale.sendTo(sender, "COMMANDS.SWITCHED");
                        } else {
                            LocalPlayer.get((Player) sender).set("CNTrans.LOCALE", "zh_cn");
                            TLocale.sendTo(sender, "COMMANDS.ZHCN");
                        }
                    }
                })
                .permission("cntrans.use")
                .build();
        CommandBuilder.create("zh_TW", CNTrans.getPlugin())
                .execute((sender, args) -> {
                    if (sender instanceof Player) {
                        if (LocalPlayer.get((Player) sender).get("CNTrans.LOCALE", "zh_cn") == "zh_tw") {
                            TLocale.sendTo(sender, "COMMANDS.SWITCHED");
                        } else {
                            LocalPlayer.get((Player) sender).set("CNTrans.LOCALE", "zh_tw");
                            TLocale.sendTo(sender, "COMMANDS.ZHTW");
                        }
                    }
                })
                .permission("cntrans.use")
                .build();
    }

    @Override
    public String getCommandTitle() {
        return TLocale.asString("PLUGIN.COMMAND-TITLE");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            TLocale.sendTo(sender, "COMMANDS.NOT-PLAYER");
            return true;
        }
        if (args.length >= 1) {
            if ("switch".equals(args[0].toLowerCase())) {
                if (LocalPlayer.get((Player) sender).get("CNTrans.LOCALE", "zh_cn") == "zh_tw") {
                    LocalPlayer.get((Player) sender).set("CNTrans.LOCALE", "zh_cn");
                    TLocale.sendTo(sender, "COMMANDS.ZHCN");
                } else {
                    LocalPlayer.get((Player) sender).set("CNTrans.LOCALE", "zh_tw");
                    TLocale.sendTo(sender, "COMMANDS.ZHTW");
                }
            }
        }
        return true;
    }

}

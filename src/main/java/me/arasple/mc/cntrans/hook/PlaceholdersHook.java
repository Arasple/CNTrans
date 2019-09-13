package me.arasple.mc.cntrans.hook;

import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.inject.THook;
import me.arasple.mc.cntrans.CNTrans;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Arasple
 * @date 2019/9/13 11:25
 */
@THook
public class PlaceholdersHook extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "cntrans";
    }

    @Override
    public String getAuthor() {
        return "Arasple";
    }

    @Override
    public String getVersion() {
        return CNTrans.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return null;
        }

        if ("locale".equals(identifier)) {
            return "zh_cn".equals(LocalPlayer.get(player).getString("CNTrans.LOCALE", "zh_cn")) ? "简体中文" : "繁体中文";
        }

        if ("count".equals(identifier)) {
            return String.valueOf(
                    Bukkit.getOnlinePlayers().stream().filter(p -> {
                        String serverLocale = CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "zh_cn");
                        String locale = LocalPlayer.get(p).getString("CNTrans.LOCALE", serverLocale);

                        return !serverLocale.equals(locale);
                    }).count()
            );
        }

        return null;
    }

}

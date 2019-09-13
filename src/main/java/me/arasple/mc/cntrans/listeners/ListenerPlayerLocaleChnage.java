package me.arasple.mc.cntrans.listeners;

import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

/**
 * @author Arasple
 * @date 2019/9/8 12:44
 */
public class ListenerPlayerLocaleChnage implements Listener {

    @EventHandler
    public void onLocaleChnage(PlayerLocaleChangeEvent e) {
        Player p = e.getPlayer();
        String locale = e.getLocale();
        String selectedLocale = LocalPlayer.get(p).getString("CNTrans.LOCALE");

        LocalPlayer.get(p).set("CNTrans.CLIENT-LOCALE", locale);
        if ("zh_cn".equalsIgnoreCase(locale) && !"zh_cn".equalsIgnoreCase(selectedLocale)) {
            TLocale.sendTo(p, "NOTIFIES.ZHCN");
        } else if ("zh_tw".equalsIgnoreCase(locale) && !"zh_tw".equalsIgnoreCase(selectedLocale)) {
            TLocale.sendTo(p, "NOTIFIES.ZHTW");
        }
    }

}

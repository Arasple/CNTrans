package me.arasple.mc.cntrans.listeners;

import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.inject.TListener;
import me.arasple.mc.cntrans.CNTrans;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Arasple
 * @date 2019/9/8 12:44
 */
@TListener
public class ListenerPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!LocalPlayer.get(p).isSet("CNTrans.CLIENT-LOCALE")) {
            LocalPlayer.get(p).set("CNTrans.CLIENT-LOCALE", p.spigot().getLocale());
        }
        if (!LocalPlayer.get(p).isSet("CNTrans.LOCALE")) {
            LocalPlayer.get(p).set("CNTrans.LOCALE", "zh_cn".equalsIgnoreCase(CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "zh_cn")) ? "zh_cn" : "zh_tw");
        }
    }

}

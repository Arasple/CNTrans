package me.arasple.mc.cntrans;

import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.inject.TListener;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.module.packet.TPacketHandler;
import io.izzel.taboolib.module.packet.TPacketListener;
import me.arasple.mc.cntrans.packets.PacketProcessor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

/**
 * @author Arasple
 * @date 2019/9/8 12:44
 */
@TListener(
        register = "init"
)
public class CNTransListener implements Listener {

    public void init() {
        TPacketHandler.addListener(CNTrans.getPlugin(), new TPacketListener() {
            @Override
            public boolean onSend(Player player, Object packet) {
                return PacketProcessor.getPacketProcessor().process(player, packet);
            }
        });
    }

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

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!LocalPlayer.get(p).isSet("CNTrans.CLIENT-LOCALE")) {
            LocalPlayer.get(p).set("CNTrans.CLIENT-LOCALE", p.getLocale());
        }
        if (!LocalPlayer.get(p).isSet("CNTrans.LOCALE")) {
            LocalPlayer.get(p).set("CNTrans.LOCALE", "zh_cn".equalsIgnoreCase(CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "zh_cn")) ? "zh_cn" : "zh_tw");
        }
    }

}

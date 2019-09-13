package me.arasple.mc.cntrans.listeners;

import io.izzel.taboolib.module.inject.TSchedule;
import io.izzel.taboolib.module.packet.TPacketHandler;
import io.izzel.taboolib.module.packet.TPacketListener;
import me.arasple.mc.cntrans.CNTrans;
import me.arasple.mc.cntrans.packets.PacketProcessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Arasple
 * @date 2019/9/13 11:58
 */
public class ListenerPackets {

    @TSchedule
    public static void init() {
        TPacketHandler.addListener(CNTrans.getPlugin(), new TPacketListener() {
            @Override
            public boolean onSend(Player player, Object packet) {
                return PacketProcessor.getPacketProcessor().process(player, packet);
            }
        });

        try {
            Class.forName("org.bukkit.event.player.PlayerLocaleChangeEvent");

            Bukkit.getPluginManager().registerEvents(new ListenerPlayerLocaleChnage(), CNTrans.getPlugin());
        } catch (ClassNotFoundException ignored) {
        }
    }

}

package me.arasple.mc.cntrans.packets;

import io.izzel.taboolib.module.inject.TSchedule;
import io.izzel.taboolib.module.lite.SimpleVersionControl;
import me.arasple.mc.cntrans.CNTrans;
import me.arasple.mc.cntrans.packets.internal.AbstractPacketProcessor;

/**
 * @author Arasple
 * @date 2019/8/30 16:17
 */
public class PacketProcessor {

    private static AbstractPacketProcessor packetProcessor;

    @TSchedule
    public static void init() {
        try {
            packetProcessor = (AbstractPacketProcessor) SimpleVersionControl.createNMS("me.arasple.mc.cntrans.packets.internal.InternalPacketProcessor").mapping().translate(CNTrans.getPlugin()).newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static AbstractPacketProcessor getPacketProcessor() {
        return packetProcessor;
    }

}

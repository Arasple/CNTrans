package me.arasple.mc.cntrans.packets;

import io.izzel.taboolib.module.inject.TInject;
import me.arasple.mc.cntrans.packets.internal.AbstractPacketProcessor;

/**
 * @author Arasple
 * @date 2019/8/30 16:17
 */
public class PacketProcessor {

    @TInject(asm = "me.arasple.mc.cntrans.packets.internal.InternalPacketProcessor")
    private static AbstractPacketProcessor packetProcessor;

    public static AbstractPacketProcessor getPacketProcessor() {
        return packetProcessor;
    }

}

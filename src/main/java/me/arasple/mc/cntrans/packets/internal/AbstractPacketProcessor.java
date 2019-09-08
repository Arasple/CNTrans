package me.arasple.mc.cntrans.packets.internal;

import org.bukkit.entity.Player;

/**
 * @author Arasple
 * @date 2019/8/30 16:16
 */
public interface AbstractPacketProcessor {

    /**
     * 翻译数据包
     *
     * @param player 玩家
     * @param packet 包
     */
    void process(Player player, Object packet);

}

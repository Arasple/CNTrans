package me.arasple.mc.cntrans.packets.internal;

import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.lite.SimpleReflection;
import me.arasple.mc.cntrans.CNTrans;
import me.arasple.mc.cntrans.utils.Translator;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * @author Arasple
 * @date 2019/8/30 16:17
 */
public class InternalPacketProcessor implements AbstractPacketProcessor {

    static {
        SimpleReflection.saveField(PacketPlayOutChat.class);
        SimpleReflection.saveField(PacketPlayOutTitle.class);
        SimpleReflection.saveField(PacketPlayOutScoreboardObjective.class);
        SimpleReflection.saveField(PacketPlayOutWindowItems.class);
        SimpleReflection.saveField(PacketPlayOutPlayerListHeaderFooter.class);
        SimpleReflection.saveField(PacketPlayOutSetSlot.class);
    }

    @Override
    public boolean process(Player player, Object packet) {
        if (packet == null) {
            return true;
        }

        String serverLocale = CNTrans.getSettings().getString("GENERAL.SERVER-LANGUAGE", "zh_cn");
        String locale = LocalPlayer.get(player).getString("CNTrans.LOCALE", serverLocale);

        if (locale.equals(serverLocale)) {
            return true;
        }

        // 聊天框处理
        if (CNTrans.getSettings().getBoolean("TRANSLATIONS.CHAT", true) && packet instanceof PacketPlayOutChat) {
            Object ic = SimpleReflection.getFieldValue(PacketPlayOutChat.class, packet, "a");
            if (ic != null) {
                String raw = IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) ic);
                SimpleReflection.setFieldValue(PacketPlayOutChat.class, packet, "a", IChatBaseComponent.ChatSerializer.a(Translator.translateString(raw, locale)));
            }
        }
        // 容器物品处理
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.ITEM", true) && (packet instanceof PacketPlayOutWindowItems || packet instanceof PacketPlayOutSetSlot)) {

            if (CNTrans.getSettings().getBoolean("OPTIONS.GUI-ONLY") && player.getOpenInventory().getTopInventory().getLocation() != null) {
                return true;
            }

            if (packet instanceof PacketPlayOutWindowItems) {
                Object items = SimpleReflection.getFieldValue(PacketPlayOutWindowItems.class, packet, "b");
                try {
                    ((List<ItemStack>) items).forEach(i -> Translator.translateItemStack(CraftItemStack.asCraftMirror(i), locale));
                } catch (Throwable e) {
                    try {
                        ((NonNullList) items).forEach(i -> Translator.translateItemStack(CraftItemStack.asCraftMirror((ItemStack) i), locale));
                    } catch (Throwable e2) {
                        Arrays.asList((ItemStack[]) items).forEach(i -> Translator.translateItemStack(CraftItemStack.asCraftMirror(i), locale));
                    }
                }
            } else if (packet instanceof PacketPlayOutSetSlot) {
                Object itemStack = SimpleReflection.getFieldValue(PacketPlayOutSetSlot.class, packet, "c");
                Translator.translateItemStack(CraftItemStack.asCraftMirror((ItemStack) itemStack), locale);
            }
        }
        // TABLIST 处理
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.TABLIST", true) && packet instanceof PacketPlayOutPlayerListHeaderFooter) {
            Object header = SimpleReflection.getFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "header");
            Object footer = SimpleReflection.getFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "footer");
            if (header != null) {
                SimpleReflection.setFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "header", IChatBaseComponent.ChatSerializer.a(Translator.translateString(IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) header), locale)));
            }
            if (footer != null) {
                SimpleReflection.setFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "footer", IChatBaseComponent.ChatSerializer.a(Translator.translateString(IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) header), locale)));
            }
        }
        // SCOREBOARD 处理
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.SCOREBOARD", true) && packet instanceof PacketPlayOutScoreboardObjective) {
            if (packet instanceof PacketPlayOutScoreboardObjective) {
                Object b = SimpleReflection.getFieldValue(PacketPlayOutScoreboardObjective.class, packet, "b");
                try {
                    SimpleReflection.setFieldValue(PacketPlayOutScoreboardObjective.class, packet, "b", IChatBaseComponent.ChatSerializer.a(Translator.translateString(IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) b), locale)));
                } catch (ClassCastException e) {
                    SimpleReflection.setFieldValue(PacketPlayOutScoreboardObjective.class, packet, "b", IChatBaseComponent.ChatSerializer.a(Translator.translateString(String.valueOf(b), locale)));
                }
            }
        }
        // TITLE
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.TITLE", true) && packet instanceof PacketPlayOutTitle) {
            PacketPlayOutTitle.EnumTitleAction action = (PacketPlayOutTitle.EnumTitleAction) SimpleReflection.getFieldValue(PacketPlayOutTitle.class, packet, "a");
            if (action != PacketPlayOutTitle.EnumTitleAction.TIMES && action != PacketPlayOutTitle.EnumTitleAction.RESET && action != PacketPlayOutTitle.EnumTitleAction.CLEAR) {
                Object ic = SimpleReflection.getFieldValue(PacketPlayOutTitle.class, packet, "b");
                SimpleReflection.setFieldValue(PacketPlayOutTitle.class, packet, "b", IChatBaseComponent.ChatSerializer.a(Translator.translateString(IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) ic), locale)));
            }
        }
        // SIGNS
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.SIGN", true)) {
        }

        return true;
    }

}

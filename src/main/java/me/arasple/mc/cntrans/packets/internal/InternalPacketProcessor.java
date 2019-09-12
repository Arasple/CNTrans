package me.arasple.mc.cntrans.packets.internal;

import com.google.common.collect.Lists;
import com.luhuiguo.chinese.ChineseUtils;
import io.izzel.taboolib.module.db.local.LocalPlayer;
import io.izzel.taboolib.module.lite.SimpleReflection;
import me.arasple.mc.cntrans.CNTrans;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

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
            IChatBaseComponent ic = (IChatBaseComponent) SimpleReflection.getFieldValue(PacketPlayOutChat.class, packet, "a");
            ChatMessageType type = (ChatMessageType) SimpleReflection.getFieldValue(PacketPlayOutChat.class, packet, "b");
            if (ic != null) {
                String raw = IChatBaseComponent.ChatSerializer.a(ic);
                SimpleReflection.setFieldValue(PacketPlayOutChat.class, packet, "a", IChatBaseComponent.ChatSerializer.a(translateString(raw, locale)));
            }
        }
        // 容器物品处理
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.ITEM", true) && (packet instanceof PacketPlayOutWindowItems || packet instanceof PacketPlayOutSetSlot)) {

            if (CNTrans.getSettings().getBoolean("OPTIONS.GUI-ONLY") && player.getOpenInventory().getTopInventory().getLocation() != null) {
                return true;
            }

            if (packet instanceof PacketPlayOutWindowItems) {
                List<ItemStack> items = (List<ItemStack>) SimpleReflection.getFieldValue(PacketPlayOutWindowItems.class, packet, "b");
                items.forEach(i -> translateItemStack(i.getBukkitStack(), locale));
            } else if (packet instanceof PacketPlayOutSetSlot) {
                ItemStack itemStack = (ItemStack) SimpleReflection.getFieldValue(PacketPlayOutSetSlot.class, packet, "c");
                translateItemStack(itemStack.getBukkitStack(), locale);
            }
        }
        // TABLIST 处理
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.TABLIST", true) && packet instanceof PacketPlayOutPlayerListHeaderFooter) {
            IChatBaseComponent header = (IChatBaseComponent) SimpleReflection.getFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "header");
            IChatBaseComponent footer = (IChatBaseComponent) SimpleReflection.getFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "footer");
            if (header != null) {
                SimpleReflection.setFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "header", IChatBaseComponent.ChatSerializer.a(translateString(IChatBaseComponent.ChatSerializer.a(header), locale)));
            }
            if (footer != null) {
                SimpleReflection.setFieldValue(PacketPlayOutPlayerListHeaderFooter.class, packet, "footer", IChatBaseComponent.ChatSerializer.a(translateString(IChatBaseComponent.ChatSerializer.a(footer), locale)));
            }
        }
        // SCOREBOARD 处理
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.SCOREBOARD", true) && packet instanceof PacketPlayOutScoreboardObjective) {
            if (packet instanceof PacketPlayOutScoreboardObjective) {
                IChatBaseComponent b = (IChatBaseComponent) SimpleReflection.getFieldValue(PacketPlayOutScoreboardObjective.class, packet, "b");
                SimpleReflection.setFieldValue(PacketPlayOutScoreboardObjective.class, packet, "b", IChatBaseComponent.ChatSerializer.a(translateString(IChatBaseComponent.ChatSerializer.a(b), locale)));
            }
        }
        // TITLE
        else if (CNTrans.getSettings().getBoolean("TRANSLATIONS.TITLE", true) && packet instanceof PacketPlayOutTitle) {
            PacketPlayOutTitle.EnumTitleAction action = (PacketPlayOutTitle.EnumTitleAction) SimpleReflection.getFieldValue(PacketPlayOutTitle.class, packet, "a");
            if (action == PacketPlayOutTitle.EnumTitleAction.TITLE || action == PacketPlayOutTitle.EnumTitleAction.SUBTITLE || action == PacketPlayOutTitle.EnumTitleAction.ACTIONBAR) {
                IChatBaseComponent ic = (IChatBaseComponent) SimpleReflection.getFieldValue(PacketPlayOutTitle.class, packet, "b");
                SimpleReflection.setFieldValue(PacketPlayOutTitle.class, packet, "b", IChatBaseComponent.ChatSerializer.a(translateString(IChatBaseComponent.ChatSerializer.a(ic), locale)));
            }
        }

        return true;
    }

    private void translateItemStack(org.bukkit.inventory.ItemStack bukkitStack, String toLocale) {
        if (bukkitStack == null || bukkitStack.getType() == Material.AIR) {
            return;
        }
        ItemMeta meta = bukkitStack.getItemMeta();

        if (meta != null) {
            if (meta.hasLore() && meta.getLore() != null && meta.getLore().size() > 0) {
                List<String> lores = Lists.newArrayList();
                meta.getLore().forEach(l -> lores.add(translateString(l, toLocale)));
                meta.setLore(lores);
            }
            if (meta.hasDisplayName()) {
                String tran = translateString(meta.getDisplayName(), toLocale);
                meta.setDisplayName(meta.getDisplayName().charAt(0) != ChatColor.COLOR_CHAR ? ChatColor.RESET + tran : tran);
            }
            bukkitStack.setItemMeta(meta);
        }
    }

    private static String translateString(String string, String toLocale) {
        return "zh_cn".equals(toLocale) ? ChineseUtils.toSimplified(string) : ChineseUtils.toTraditional(string);
    }

}

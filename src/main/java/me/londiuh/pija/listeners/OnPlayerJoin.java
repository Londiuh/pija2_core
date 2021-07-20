package me.londiuh.pija.listeners;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class OnPlayerJoin {
    public static void onJoin(ServerPlayerEntity player) {
        player.sendMessage(new LiteralText("§f §f §1 §0 §2 §4§f §f §2 §0 §4 §8§0§1§0§1§2§f§f§0§1§3§4§f§f§0§1§5§f§f§0§1§6§f§f§0§1§8§9§a§b§f§f§0§1§7§f§f§3 §9 §2 §0 §0 §1§3 §9 §2 §0 §0 §2§3 §9 §2 §0 §0 §3§0§0§1§f§e§0§0§2§f§e§0§0§3§4§5§6§7§8§f§e§3 §6 §3 §6 §3 §6 §e§3 §6 §3 §6 §3 §6 §d"), false);
        player.sendMessage(new LiteralText("§c §r§5 §r§1 §r§f §r§8 §r§3 §r§8 §r§9 §r§2 §r§3 §r§1"), false);
    }
}

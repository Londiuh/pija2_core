package me.londiuh.pija.listeners;

import net.minecraft.network.MessageType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class OnGameMessage {
    public static boolean canSendMessage(ServerPlayNetworkHandler networkHandler, ChatMessageC2SPacket packet) {
        ServerPlayerEntity player = networkHandler.player;
        String msg = packet.getChatMessage();
        if (msg.contains(":mari:") || msg.contains(":comida:")) {
            Text text = new TranslatableText("chat.type.text", new Object[]{player.getDisplayName(), msg.replace(":mari:", "⑱").replace(":comida:", "⑳")});
            player.getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
            return false;
        }
        if (player.getScoreboardTags().contains("preso") && !msg.equals("/iniciarsesion")) {
            player.sendMessage(new LiteralText("§cEstas preso y no puedes mandar mensajes"), false);
            return false;
        }
        return true;
    }
}

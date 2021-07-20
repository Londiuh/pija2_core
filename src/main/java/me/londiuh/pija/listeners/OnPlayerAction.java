package me.londiuh.pija.listeners;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class OnPlayerAction {
    public static void interact(PlayerInteractItemC2SPacket packet, ServerPlayNetworkHandler networkHandler) {
        ServerPlayerEntity player = networkHandler.player;
        Hand hand = packet.getHand();
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.getItem() == Items.WARPED_FUNGUS_ON_A_STICK && itemStack.getTag().contains("CustomModelData")) {
            player.sendMessage(new LiteralText("§9Ahora te estás fumando el porro"), false);
            MinecraftServer server = player.getServer();
            server.getPlayerManager().sendToAround(null, player.getX(), player.getY(), player.getZ(), 1, player.getServerWorld().getRegistryKey(), new PlaySoundIdS2CPacket(new Identifier("pija", "droga.porro"), SoundCategory.MASTER, player.getPos(), 1f, 1f));
            player.inventory.removeOne(itemStack);
            player.equip(100 + EquipmentSlot.HEAD.getEntitySlotId(), itemStack);
        }
    }
}

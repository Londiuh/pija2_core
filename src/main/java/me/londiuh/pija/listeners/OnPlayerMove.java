package me.londiuh.pija.listeners;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class OnPlayerMove {
    public static void move(ServerPlayNetworkHandler networkHandler) {
        ServerPlayerEntity player = networkHandler.player;
        ItemStack itemStack = player.inventory.armor.get(3);
        if (itemStack.getItem() == Items.WARPED_FUNGUS_ON_A_STICK && itemStack.getTag().contains("CustomModelData")) {
            if (itemStack.getDamage() == 115) {
                player.inventory.removeOne(itemStack);
                player.sendMessage(new LiteralText("§eSe ha acabado el porro"), false);
                return;
            }
            itemStack.setDamage(itemStack.getDamage() + 1);
            player.getServerWorld().spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getEyeY(), player.getZ(), 1, 0.003, 0.01, 0.003, 0.009);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 100, 1));
        }
    }
}

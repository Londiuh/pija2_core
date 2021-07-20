package me.londiuh.pija.mixin;

import me.londiuh.pija.listeners.OnGameMessage;
import me.londiuh.pija.listeners.OnPlayerAction;
import me.londiuh.pija.listeners.OnPlayerMove;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "onPlayerMove", at = @At("HEAD"), cancellable = true)
    public void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        OnPlayerMove.move((ServerPlayNetworkHandler) (Object) this);
    }
    
    @Inject(method = "onPlayerInteractItem", at = @At("HEAD"), cancellable = true)
    public void onPlayerInteractItem(PlayerInteractItemC2SPacket packet, CallbackInfo ci) {
        OnPlayerAction.interact(packet, (ServerPlayNetworkHandler) (Object) this);
    }

    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    public void onGameMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        if (!OnGameMessage.canSendMessage((ServerPlayNetworkHandler) (Object) this, packet)) {
            ci.cancel();
        }
    }
}
package me.londiuh.pija.commands;

import com.mojang.brigadier.CommandDispatcher;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class CarcelCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("carcel")
            .requires(source -> source.hasPermissionLevel(2))
                .then(argument("jugador", EntityArgumentType.player())
                    .executes(ctx -> {
                        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "jugador");
                        RegistryKey<World> registryKey = RegistryKey.of(Registry.DIMENSION, DimensionType.OVERWORLD_ID);
                        player.addScoreboardTag("preso");
                        player.teleport(player.getServer().getWorld(registryKey), 61, 64, 203, -146, 3);
                        player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.TITLE, new LiteralText("§cEstás preso")));
                        player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("entity.zombie.attack_iron_door"), SoundCategory.MASTER, player.getPos(), 1, 0));
                        player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("block.iron_door.close"), SoundCategory.MASTER, player.getPos(), 1, 0));
                        ctx.getSource().getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText(String.format("§a%s ha sido metido en la cárcel", player.getEntityName())), MessageType.CHAT, Util.NIL_UUID);
                        return 1;
        }))); 
    }
}

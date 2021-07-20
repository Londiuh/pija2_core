package me.londiuh.pija.commands;

import com.mojang.brigadier.CommandDispatcher;
import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class CiudadCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("ciudad")
            .executes(ctx -> {
                ServerPlayerEntity player = ctx.getSource().getPlayer();
                if (player.getHealth() < 19.5) {
                    ctx.getSource().sendFeedback(new LiteralText("§cTienes que estar al maximo de vida"), false);
                    return 1;
                }
                RegistryKey<World> registryKey = RegistryKey.of(Registry.DIMENSION, DimensionType.OVERWORLD_ID);
                player.teleport(player.getServer().getWorld(registryKey), 85.50, 73.0, 133.0, 0.31f, 0.15f);
                return 1;
        }));    
    }
}

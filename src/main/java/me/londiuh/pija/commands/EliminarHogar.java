package me.londiuh.pija.commands;

import me.londiuh.pija.HogaresJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class EliminarHogar {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(literal("eliminarhogar")
            .then(argument("hogar", StringArgumentType.word())
                .executes(ctx -> {
                    String hogar = StringArgumentType.getString(ctx, "hogar");
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (!HogaresJson.hogarExiste(player.getEntityName(), hogar)) {
                        ctx.getSource().sendFeedback(new LiteralText(String.format("§cNo tienes ningún hogar llamado '%s'", hogar)), false);
                        return 1;
                    }
                    HogaresJson.eliminarHogar(player.getEntityName(), hogar);
                    ctx.getSource().sendFeedback(new LiteralText(String.format("§aHogar '%s' eliminado", hogar)), false);
                    return 1;
        })));
        dispatcher.register(literal("delhome").redirect(literalCommandNode));
    }
}

package me.londiuh.pija.commands;

import me.londiuh.pija.HogaresJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class DefinirHogarCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(literal("establecerhogar")
            .then(argument("hogar", StringArgumentType.word())
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (!HogaresJson.puedeTenerHogar(player.getEntityName())) {
                        ctx.getSource().sendFeedback(new LiteralText("§cSolo puedes tener 2 hogares"), false);
                        return 1;
                    }
                    if (!player.getEntityWorld().getDimension().hasSkyLight()) {
                        ctx.getSource().sendFeedback(new LiteralText("§cSolo puedes establezer un hogar en el overworld"), false);
                        return 1;
                    }
                    if (player.experienceLevel < 10) {
                        ctx.getSource().sendFeedback(new LiteralText("§cNecesitas ser nivel 10 o superior"), false);
                        return 1;
                    }
                    String hogar = StringArgumentType.getString(ctx, "hogar");
                    if (HogaresJson.hogarExiste(player.getEntityName(), hogar)) {
                        System.out.println("comporbado que existe");
                        ctx.getSource().sendFeedback(new LiteralText(String.format("§cYa tienes un hogar llamado '%s'", hogar)), false);
                        return 1;
                    }
                    HogaresJson.añadirHogar(player.getEntityName(), hogar, player.getX(), player.getY(), player.getZ());
                    player.addExperienceLevels(-5);
                    player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("entity.player.levelup"), SoundCategory.MASTER, player.getPos(), 1, 0.5f));
                    ctx.getSource().sendFeedback(new LiteralText(String.format("§eHogar definido como '%s'", hogar)), false);
                    return 1;
        }))); 
        dispatcher.register(literal("sethome").redirect(literalCommandNode));
    }
}

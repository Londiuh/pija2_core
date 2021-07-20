package me.londiuh.pija.commands;

import me.londiuh.pija.HogaresJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class HogarCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(literal("hogar")
            .then(argument("hogar", StringArgumentType.word())
                .executes(ctx -> {
                    String hogar = StringArgumentType.getString(ctx, "hogar");
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (!HogaresJson.hogarExiste(player.getEntityName(), hogar)) {
                        ctx.getSource().sendFeedback(new LiteralText(String.format("§cNo tienes ningún hogar llamado '%s'", hogar)), false);
                        return 1;
                    }
                    if (!player.getEntityWorld().getDimension().hasSkyLight()) {
                        ctx.getSource().sendFeedback(new LiteralText("§cSolo puedes usar esto en el overworld"), false);
                        return 1;
                    }
                    double[] hogarPos = HogaresJson.getHogarPos(player.getEntityName(), hogar);
                    Block bloqueCasa = ctx.getSource().getWorld().getBlockState(new BlockPos(hogarPos[0], hogarPos[1], hogarPos[2])).getBlock();
                    if (bloqueCasa != Blocks.AIR && bloqueCasa != Blocks.CAVE_AIR) {
                        ctx.getSource().sendFeedback(new LiteralText(String.format("§cTu hogar '%s' esta obstruido", hogar)), false);
                        return 1;
                    }
                    RegistryKey<World> registryKey = RegistryKey.of(Registry.DIMENSION, DimensionType.OVERWORLD_ID);
                    player.teleport(player.getServer().getWorld(registryKey), hogarPos[0], hogarPos[1], hogarPos[2], player.yaw, player.pitch);
                    player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.TITLE, new LiteralText(String.format("§e%s", hogar))));
                    player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("block.beacon.power_select"), SoundCategory.MASTER, player.getPos(), 1, 1.5f));
                    player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("block.beacon.activate"), SoundCategory.MASTER, player.getPos(), 1, 1.5f));
                    player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("block.beacon.ambient"), SoundCategory.MASTER, player.getPos(), 1, 2));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20, 0, true, false));
                    player.getServerWorld().spawnParticles(ParticleTypes.ENTITY_EFFECT, player.getX(), player.getEyeY(), player.getZ(), 100, 0.5, 0.5, 0.5, 1);
                    return 1;
        })));
        dispatcher.register(literal("home").redirect(literalCommandNode));
    }
}

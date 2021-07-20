package me.londiuh.pija;

import me.londiuh.pija.commands.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class PijaMod implements ModInitializer {
	@Override
	public void onInitialize() {
        HogaresJson.read();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            CiudadCommand.register(dispatcher);
            CarcelCommand.register(dispatcher);
            LiberarCommand.register(dispatcher);
            DefinirHogarCommand.register(dispatcher);
            HogarCommand.register(dispatcher);
            EliminarHogar.register(dispatcher);
        });
	}
}

package me.londiuh.pija;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class HogaresJson {
    private static final File JUGADORES_HOGARES = new File("jugadores-hogares.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static JsonObject jsonObject = new JsonObject();
    
    public static String[] obtenerHogares(String jugador) {
        if (jsonObject.get(jugador) == null) {
            return null;
        }
        return jsonObject.get(jugador).getAsJsonObject().entrySet().toArray(new String[] {});
    }

    public static boolean puedeTenerHogar(String jugador) {
        return jsonObject.get(jugador) == null || jsonObject.get(jugador).getAsJsonObject().size() != 2;
    }

    public static boolean hogarExiste(String jugador, String hogar) {
        return (jsonObject.get(jugador) != null && jsonObject.get(jugador).getAsJsonObject().get(hogar) != null);
    }

    public static double[] getHogarPos(String jugador, String hogar) {
        return gson.fromJson(jsonObject.get(jugador).getAsJsonObject().get(hogar), double[].class);
    }

    public static void eliminarHogar(String jugador, String hogar) {
        if (jsonObject.get(jugador) == null) {
            return;
        }
        jsonObject.get(jugador).getAsJsonObject().remove(hogar);
        save();
    }

    public static void añadirHogar(String jugador, String hogar, double x, double y, double z) {
        if (jsonObject.get(jugador) == null) {
            jsonObject.add(jugador, new JsonObject());
        }
        jsonObject.get(jugador).getAsJsonObject().add(hogar, gson.toJsonTree(new double[] {x, y, z}));
        save();
    }

    private static void save() {
        try {
            BufferedWriter bufferedWriter = Files.newWriter(JUGADORES_HOGARES, StandardCharsets.UTF_8);
            bufferedWriter.write(gson.toJson(jsonObject));
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void read() {
        if (!JUGADORES_HOGARES.exists()) {
            return;
        }
        try {
            BufferedReader bufferedReader = Files.newReader(JUGADORES_HOGARES, StandardCharsets.UTF_8);
            jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

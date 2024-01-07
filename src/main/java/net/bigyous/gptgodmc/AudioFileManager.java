package net.bigyous.gptgodmc;

import javax.sound.sampled.AudioFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.server.MinecraftServer;

public class AudioFileManager {
    // bitrate in kbps (48000hz * 16 bits)
    public static final int BIT_RATE = 320;
    public static AudioFormat FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 48000F, 16, 1, 2, 48000F, false);

    public static LevelResource VOICE_DATA = new LevelResource("player_voice_data");

    public static Path getPlayerMp3(ServerPlayer player){
        MinecraftServer server = player.getServer();
        String uuidString = player.getUUID().toString();
        return server.getWorldPath(VOICE_DATA).resolve(uuidString + ".mp3");
    }

    public static OutputStream getPlayerOutputStream(ServerPlayer player){
        Path soundFile = getPlayerMp3(player);
        try {
            Files.createDirectories(soundFile.getParent());
            return Files.newOutputStream(soundFile);
        } catch (IOException e) {
            GPTGOD.LOGGER.warn(String.format("An IO Exception occured getting output stream for player: %s", player.getDisplayName().getString()));
            return null;
        }
    }

    public static void deletePlayerMp3(ServerPlayer player){   
        try {
            Files.delete(getPlayerMp3(player));
        } catch (IOException e) {
            GPTGOD.LOGGER.warn("tried to delete nonexistant file");
        }
    }

}

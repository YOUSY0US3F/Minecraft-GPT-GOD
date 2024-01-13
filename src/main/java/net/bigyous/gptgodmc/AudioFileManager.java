package net.bigyous.gptgodmc;

import javax.sound.sampled.AudioFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.FileUtils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.server.MinecraftServer;

public class AudioFileManager {
    // bitrate in kbps (48000hz * 16 bits)
    public static final int BIT_RATE = 320;
    public static AudioFormat FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 48000F, 16, 1, 
        2, 48000F, false);
    private static AtomicInteger currentId = new AtomicInteger();
    public static LevelResource VOICE_DATA = new LevelResource("player_voice_data");

    public static Path getPlayerMp3(ServerPlayer player, int fileNumber){
        MinecraftServer server = player.getServer();
        String uuidString = player.getUUID().toString();
        return server.getWorldPath(VOICE_DATA).resolve(String.format("%s/%d.mp3", uuidString, fileNumber));
    }

    public static OutputStream getPlayerOutputStream(ServerPlayer player, int fileNumber){
        Path soundFile = getPlayerMp3(player, fileNumber);
        try {
            Files.createDirectories(soundFile.getParent());
            return Files.newOutputStream(soundFile);
        } catch (IOException e) {
            GPTGOD.LOGGER.warn(String.format("An IO Exception occured getting output stream for player: %s", player.getDisplayName().getString()));
            return null;
        }
    }

    public static void deletePlayerData(ServerPlayer player){   
        MinecraftServer server = player.getServer();
        String uuidString = player.getUUID().toString();
        try {
            // Files.delete(getPlayerMp3(player));
            FileUtils.deleteDirectory(server.getWorldPath(VOICE_DATA).resolve(uuidString).toFile());
        } catch (IOException e) {
            GPTGOD.LOGGER.warn("tried to delete nonexistant file");
        }
    }

    public static void deleteFile(Path path){
        try {
            Files.delete(path);
        } catch (IOException e) {
            GPTGOD.LOGGER.error("tried to delete non-existant file", e);
        }
    }

    public static void deleteFile(ServerPlayer player, int fileNumber){
        try {
            Files.delete(getPlayerMp3(player, fileNumber));
        } catch (IOException e) {
            GPTGOD.LOGGER.error("tried to delete non-existant file", e);
        }
    }

    public static int getCurrentId(){
        // overflowing to min int would be fine but I like having positive numbers
        int id = currentId.getAndIncrement();
        if(id == Integer.MIN_VALUE){
            currentId = new AtomicInteger();
            id = currentId.get();
        }
        return id;
    }

    public static void reset(){
        currentId = new AtomicInteger();
    }

}

package net.bigyous.gptgodmc;

import de.maxhenkel.voicechat.api.mp3.Mp3Encoder;

import java.io.IOException;

import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import net.minecraft.server.level.ServerPlayer;

public class PlayerAudioBuffer {
    private ServerPlayer player;
    private short[] samples;
    private VoicechatServerApi api;
    

    public PlayerAudioBuffer(short[] initialSamples, ServerPlayer player, VoicechatServerApi api){
        this.samples = initialSamples;
        this.player = player;
        this.api = api;
    }   

    public short[] getSamples(){
        return this.samples;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public void addSamples(short[] addition){
        short[] replace = new short[this.samples.length + addition.length];
        System.arraycopy(samples, 0, replace, 0, samples.length);
        System.arraycopy(addition, 0, replace, samples.length, addition.length);

        this.samples = replace;
    }

    public void encode(){
        Mp3Encoder encoder = api.createMp3Encoder(AudioFileManager.FORMAT, AudioFileManager.BIT_RATE, 0, AudioFileManager.getPlayerOutputStream(player));
        try {
            encoder.encode(samples);
            encoder.close();
            GPTGOD.LOGGER.info(String.format("created mp3 file at: %s", AudioFileManager.getPlayerMp3(player).toUri().toString()));
        } catch (IOException e) {
            GPTGOD.LOGGER.warn(String.format("An IO Exception occured encoding mp3 file for Player: %s", player.getDisplayName().getString()));
        }
        this.samples = new short[] {};
    }
}

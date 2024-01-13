package net.bigyous.gptgodmc;

import net.bigyous.gptgodmc.utils.TaskQueue;
import net.bigyous.gptgodmc.GPT.Transcription;

import net.minecraft.server.level.ServerPlayer;
import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.PlayerDisconnectedEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStoppedEvent;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import javax.annotation.Nullable;

@ForgeVoicechatPlugin
public class VoiceMonitorPlugin implements VoicechatPlugin {

    private static ConcurrentHashMap<UUID, PlayerAudioBuffer> buffers;
    private static ConcurrentHashMap<UUID, OpusDecoder> decoders;
    private static TaskQueue<PlayerAudioBuffer> encodingQueue;

    /**
     * @return the unique ID for this voice chat plugin
     */
    @Override
    public String getPluginId() {
        return GPTGOD.MOD_ID;
    }

    /**
     * Called when the voice chat initializes the plugin.
     *
     * @param api the voice chat API
     */
    @Override
    public void initialize(VoicechatApi api) {
        GPTGOD.LOGGER.info("voice monitor initialized");
        buffers = new ConcurrentHashMap<UUID, PlayerAudioBuffer>();
        decoders = new ConcurrentHashMap<UUID, OpusDecoder>();
        encodingQueue = new TaskQueue<PlayerAudioBuffer>((PlayerAudioBuffer buffer) -> {
            String speech = Transcription.Transcribe(AudioFileManager.getPlayerMp3(buffer.getPlayer(), buffer.getBufferId()));
            AudioFileManager.deleteFile(buffer.getPlayer(), buffer.getBufferId());
            GPTGOD.LOGGER.info(String.format("%s said: %s", buffer.getPlayer().getDisplayName().getString(), speech));
            //EventLogger.
        });
    }

    /**
     * Called once by the voice chat to register all events.
     *
     * @param registration the event registration
     */
    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
        registration.registerEvent(PlayerDisconnectedEvent.class, this::onPlayerDisconnect);
        registration.registerEvent(VoicechatServerStoppedEvent.class,this::onServerStopped);
    }

    private void onMicPacket(MicrophonePacketEvent event){
        VoicechatConnection senderConnection = event.getSenderConnection();
        byte[] encodedData = event.getPacket().getOpusEncodedData();
        if (senderConnection == null) {
            return;
        }
        if (!(senderConnection.getPlayer().getPlayer() instanceof ServerPlayer player)) {
            GPTGOD.LOGGER.warn("Received microphone packets from non-player");
            return;
        }
        // GPTGOD.LOGGER.info(String.format("Player: %s Sent packet of length: %d", player.getDisplayName().getString(), encodedData.length));
        if (!decoders.containsKey(player.getUUID())) {
            decoders.put(player.getUUID(), event.getVoicechat().createDecoder());
        }
        OpusDecoder decoder = decoders.get(player.getUUID());
        short[] decoded = decoder.decode(event.getPacket().getOpusEncodedData());

        if(encodedData.length > 0){
            if(!buffers.containsKey(player.getUUID())){
                PlayerAudioBuffer buffer = new PlayerAudioBuffer(decoded, player, event.getVoicechat());
                buffers.put(player.getUUID(), buffer);
            }
            else{
                buffers.get(player.getUUID()).addSamples(decoded);
            }
        }
        else{
            PlayerAudioBuffer toBeProcessed = buffers.get(player.getUUID());
            toBeProcessed.encode();
            encodingQueue.insert(toBeProcessed);
            buffers.remove(player.getUUID());
            decoder.resetState();
        }
    }

    private void onPlayerDisconnect(PlayerDisconnectedEvent event){
        cleanUpPlayer(event.getPlayerUuid(), event.getVoicechat());
    }

    private void onServerStopped(VoicechatServerStoppedEvent event){
        decoders.forEach((key, value) -> cleanUpPlayer(key, event.getVoicechat()));
    }

    private void cleanUpPlayer(UUID uuid, VoicechatServerApi vc){
        UUID playerUuid = uuid;
        if (vc.getConnectionOf(playerUuid).getPlayer().getPlayer() instanceof ServerPlayer player){
            AudioFileManager.deletePlayerData(player);
        }
        decoders.get(playerUuid).close();
        decoders.remove(uuid);
        GPTGOD.LOGGER.info(String.format("Cleaned up data for UUID: %s", uuid.toString()));
    }

}

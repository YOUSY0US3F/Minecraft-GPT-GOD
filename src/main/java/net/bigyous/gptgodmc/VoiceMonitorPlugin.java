package net.bigyous.gptgodmc;

import net.minecraft.server.level.ServerPlayer;
import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import javax.annotation.Nullable;

@ForgeVoicechatPlugin
public class VoiceMonitorPlugin implements VoicechatPlugin {

    private static ConcurrentHashMap<UUID, PlayerAudioBuffer> buffers;

    @Nullable
    private OpusDecoder decoder;

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
    }

    /**
     * Called once by the voice chat to register all events.
     *
     * @param registration the event registration
     */
    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
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
        if (decoder == null) {
            decoder = event.getVoicechat().createDecoder();
        }
        decoder.resetState();
        short[] decoded = decoder.decode(event.getPacket().getOpusEncodedData());

        if(encodedData.length > 0){
            if(!buffers.containsKey(player.getUUID())){
                PlayerAudioBuffer buffer = new PlayerAudioBuffer(decoded, player);
                buffers.put(player.getUUID(), buffer);
            }
            else{
                buffers.get(player.getUUID()).addSamples(decoded);
            }
        }
        else{
            buffers.get(player.getUUID()).encode(event.getVoicechat());
            GPTGOD.LOGGER.info(String.format("Player: %s Finished talking, created mp3", player.getDisplayName().getString()));
        }
    }

}

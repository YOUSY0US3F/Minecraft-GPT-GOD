package net.bigyous.gptgodmc.GPT;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
import com.github.mizosoft.methanol.Methanol;
import com.github.mizosoft.methanol.MultipartBodyPublisher;
import com.github.mizosoft.methanol.MutableRequest;

import java.nio.file.Path;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

import net.bigyous.gptgodmc.GPTGOD;
import net.bigyous.gptgodmc.Config;

public class Transcription {
    private static Gson gson = new Gson();
    // private static CloseableHttpClient client = HttpClients.createDefault();
    // private static HttpPost post = new HttpPost("https://api.openai.com/v1/audio/transcriptions");
    private static Methanol client = Methanol.create();

    public static String Transcribe(Path audioPath) {
        if (Config.openAiKey.isBlank()) {
            GPTGOD.LOGGER.warn("No API Key");
            return "something";
        }

        try {
            MultipartBodyPublisher body = MultipartBodyPublisher.newBuilder()
                .filePart("file", audioPath)
                .textPart("model", "whisper-1")
                .textPart("language", "en")
                .build();
            MutableRequest request = MutableRequest.POST("https://api.openai.com/v1/audio/transcriptions", body)
                .header("Authorization", "Bearer " + Config.openAiKey)
                .header("Content-Type", "multipart/form-data");
            HttpResponse<String> response =  client.send(request, BodyHandlers.ofString());
            return gson.fromJson(response.body(), TranscriptionResponse.class).getText();
        } catch (FileNotFoundException e) {
            GPTGOD.LOGGER.error("Attempted to Transcribe non-existant file", e);
            e.printStackTrace();
        } catch (IOException e) {
            GPTGOD.LOGGER.error("An error occured furing Transcription", e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            GPTGOD.LOGGER.error("Transcription Interrupted", e);
        }
        

        // MultipartEntityBuilder builder = MultipartEntityBuilder.create()
        //     .addBinaryBody("file", audioPath.toFile())
        //     .addTextBody("model", "whisper-1")
        //     .addTextBody("language", "en");

        // post.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Config.openAiKey);
        // post.addHeader(HttpHeaders.CONTENT_TYPE, "multipart/form-data");
        // post.setEntity(builder.build());
        // try {
        //     CloseableHttpResponse response = client.execute(post);
        //     GPTGOD.LOGGER.info(response.getEntity().toString());
        //     return gson.toJson(response.getEntity().toString());
        // } catch (ClientProtocolException e) {
        //     GPTGOD.LOGGER.warn("HTTP error transcribing");
        // } catch (IOException e) {
        //     GPTGOD.LOGGER.warn("Audio File for transcription not found!");
        // }

        // MultiPartBodyPublisher publisher = new MultiPartBodyPublisher()
        //     .addPart("model", "whisper-1")
        //     .addPart("file", audioPath)
        //     .addPart("language", "en");
        // try {
        //     HttpRequest post = HttpRequest.newBuilder()
        //         .uri(new URI("https://api.openai.com/v1/audio/transcriptions"))
        //         .header("Authorization","Bearer " + Config.openAiKey)
        //         .header("Content-Type", "multipart/form-data")
        //         .POST(publisher.build())
        //         .build();
        //     HttpResponse<String> response = client.send(post, BodyHandlers.ofString());
        //     GPTGOD.LOGGER.info(response.body());
        //     return gson.fromJson(response.body(), TranscriptionResponse.class).getText();
        // } catch (IOException e) {
        //     GPTGOD.LOGGER.warn("Audio File for transcription not found!");
        // } catch (InterruptedException e) {
        //     GPTGOD.LOGGER.warn("Transcription Interrupted");
        // }catch (URISyntaxException e) {
        //     GPTGOD.LOGGER.warn("incorrect URL");
        // }
        return "something";
    }
}

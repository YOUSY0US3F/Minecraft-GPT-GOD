package net.bigyous.gptgodmc.GPT;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.GsonBuilder;

import net.bigyous.gptgodmc.Config;
import net.bigyous.gptgodmc.GPTGOD;
import net.bigyous.gptgodmc.GPT.Json.GptModel;
import net.bigyous.gptgodmc.GPT.Json.GptRequest;
import net.bigyous.gptgodmc.GPT.Json.GptTool;
import net.bigyous.gptgodmc.GPT.Json.ModelSerializer;

public class GptAPI {
    private GsonBuilder gson = new GsonBuilder();
    private HttpClient client;
    private GptRequest body;
    private String CHATGPTURL = "https://api.openai.com/v1/chat/completions";
    public GptAPI (GptModel model){
        this.body = new GptRequest(model, GptActions.GetAllTools());
        gson.registerTypeAdapter(GptModel.class, new ModelSerializer());
        this.client = HttpClientBuilder.create().build();
    }
    public GptAPI (GptModel model, GptTool[] customTools){
        this.body = new GptRequest(model, customTools);
        gson.registerTypeAdapter(GptModel.class, new ModelSerializer());
    }

    public GptAPI addContext(String context){
        body.addMessage("system", context);
        return this;
    }
    // TODO set this up to accept a list of Log objects that have the tokens
    public GptAPI addLogs(String Logs){
        body.addMessage("user", Logs);
        return this;
    }
    public void send(){
        Thread worker = new Thread(()->{
            StringEntity data =new StringEntity(gson.create().toJson(body),ContentType.APPLICATION_JSON);
            HttpPost post = new HttpPost(CHATGPTURL);
            post.setHeader("Authorization", "Bearer: " + Config.openAiKey);
            post.setEntity(data);
            try {
                HttpResponse response = client.execute(post);
                String out = new String(response.getEntity().getContent().readAllBytes());
                GptActions.processResponse(out);
            } catch (IOException e) {
                GPTGOD.LOGGER.error("There was an error maing a request to GPT", e);
            }
            Thread.currentThread().interrupt();
        });
        worker.start();
    }
}

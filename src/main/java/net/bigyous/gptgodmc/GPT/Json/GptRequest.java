package net.bigyous.gptgodmc.GPT.Json;

import java.util.ArrayList;

public class GptRequest {
    private GptModel model;
    private ArrayList<GptMessage> messages;
    private GptTool[] tools;
    public GptRequest(GptModel model, GptTool[] tools){
        this.model = model;
        this.tools = tools;
        this.messages = new ArrayList<GptMessage>();
    }

    public GptModel getModel() {
        return model;
    }

    public GptTool[] getTools() {
        return tools;
    }

    public void setModel(GptModel model) {
        this.model = model;
    }

    public void addMessage(String role, String content){
        messages.add(new GptMessage(role, content));
    }

    public void clearMessages(){
        this.messages = new ArrayList<GptMessage>();
    }

}

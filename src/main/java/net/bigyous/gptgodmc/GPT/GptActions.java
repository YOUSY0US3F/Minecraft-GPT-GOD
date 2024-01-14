package net.bigyous.gptgodmc.GPT;

import java.util.Collections;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

import net.bigyous.gptgodmc.GPTGOD;
import net.bigyous.gptgodmc.interfaces.Function;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import com.google.gson.Gson;

public class GptActions {
    private static Gson gson = new Gson();
    private static Function<String> whisper = (String args) -> {
        TypeToken<Map<String, String>> mapType = new TypeToken<Map<String, String>>(){};
        Map<String, String> argsMap = gson.fromJson(args, mapType);
        ServerPlayer player = GPTGOD.SERVER.getPlayerList().getPlayerByName(argsMap.get("playerName"));
        player.displayClientMessage(Component.literal(argsMap.get("message")),false);
    };



    private static GptFunction[] functions = {
        new GptFunction("whisper", "send a private message to a player", 
            Map.of("playerName", new Parameter("string", "name of the Player"), "message", new Parameter("string", "message")), whisper)
    };
    private static GptTool[] tools = new GptTool[functions.length];

    public static GptTool[] GetAllTools(){
        if(tools[0] != null){
            return tools;
        }
        for (int i = 0; i< functions.length; i++){
            tools[i] = new GptTool(functions[i]);
        }
        return tools;
    }
}

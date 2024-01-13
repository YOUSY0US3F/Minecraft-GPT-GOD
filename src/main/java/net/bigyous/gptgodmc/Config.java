package net.bigyous.gptgodmc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GPTGOD.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<String> OPENAI_KEY = BUILDER
        .comment("Your OpenAi API key")
        .define("apiKey", "");
    
    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String openAiKey;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){
        openAiKey = OPENAI_KEY.get();
    }
}

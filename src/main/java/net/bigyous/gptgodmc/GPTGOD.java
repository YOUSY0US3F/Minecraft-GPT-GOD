package net.bigyous.gptgodmc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(GPTGOD.MOD_ID)
public class GPTGOD {

    public static final String MOD_ID = "gptgodmc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static EventLogger eventLogger;

    public GPTGOD() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        // REGISTERING THIS WAY DOES NOT WORK FOR SOME EVENTS
        //LoggableEventHandler eventHandler = new LoggableEventHandler();
        //MinecraftForge.EVENT_BUS.register(eventHandler);
        MinecraftForge.EVENT_BUS.register(LoggableEventHandler.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Setting up GPT GOD");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting");

        eventLogger = new EventLogger(event.getServer());

        LOGGER.info("Event logger initialized: " + eventLogger);
    }

}

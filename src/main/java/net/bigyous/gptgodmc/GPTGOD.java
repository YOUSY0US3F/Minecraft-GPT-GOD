package net.bigyous.gptgodmc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(GPTGOD.MOD_ID)
public class GPTGOD {

    public static final String MOD_ID = "gptgodmc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GPTGOD() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        ForgeEventHandler eventHandler = new ForgeEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Setting up example mod");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting");
    }

}

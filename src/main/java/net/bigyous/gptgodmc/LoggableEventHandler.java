package net.bigyous.gptgodmc;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.bigyous.gptgodmc.loggables.ItemPickupLoggable;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ServerChatEvent;


public class LoggableEventHandler {
    //private EventLogger eventLogger;

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        
        System.out.println("Item picked up");
        System.out.println("Name: " + event.getEntity().getName());
        System.out.println("Name: " + event.getEntity().getName());
        System.out.println(event);

        //EventLogger.addEvent(event);
        GPTGOD.eventLogger.addLoggable(
            new ItemPickupLoggable(event)
        );
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {
        System.out.println("Chat received: " + event.getRawText());

        // dbg: dump logs
        System.out.println("=== DUMPED LOGS: ===");
        System.out.println(GPTGOD.eventLogger.dump());
        System.out.println("====================");
    }

    // @SubscribeEvent
    // public void onServerStart(ServerStartingEvent event) {
    //     MinecraftServer server = event.getServer();
    //     eventLogger = new EventLogger(server);
    // }
}

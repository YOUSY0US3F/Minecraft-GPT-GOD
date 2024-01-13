package net.bigyous.gptgodmc;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.event.ServerChatEvent;


public class ForgeEventHandler {
    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        
        System.out.println("Item picked up");
        System.out.println("Name: " + event.getEntity().getName());
        System.out.println("Name: " + event.getEntity().getName());
        System.out.println(event);

        EventLogger.addEvent(event);
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {
        System.out.println("Chat received: " + event.getRawText());

        // dbg: dump logs
        System.out.println("=== DUMPED LOGS: ===");
        System.out.println(EventLogger.dump());
        System.out.println("====================");
    }
}

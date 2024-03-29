package net.bigyous.gptgodmc;

import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.bigyous.gptgodmc.loggables.AttackLoggable;
import net.bigyous.gptgodmc.loggables.DamageLoggable;
import net.bigyous.gptgodmc.loggables.ItemPickupLoggable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.ServerChatEvent;
import net.bigyous.gptgodmc.EventLogger;

@Mod.EventBusSubscriber(value=Dist.DEDICATED_SERVER)
public class LoggableEventHandler {
    //private EventLogger eventLogger;

    @SubscribeEvent
    public static void pickupItem(EntityItemPickupEvent event) {
        
        System.out.println("Item picked up");
        System.out.println("Name: " + event.getEntity().getName());
        System.out.println("Name: " + event.getEntity().getName());
        System.out.println(event);

        //EventLogger.addEvent(event);
        EventLogger.addLoggable(
            new ItemPickupLoggable(event)
        );
    }

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        System.out.println("Chat received: " + event.getRawText());

        // dbg: dump logs
        System.out.println("=== DUMPED LOGS: ===");
        System.out.println(EventLogger.dump());
        System.out.println("====================");
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        System.out.println("Entity attacked");
        EventLogger.addLoggable(
            new AttackLoggable(event)
        );
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        System.out.println("Entity damaged");
        EventLogger.addLoggable(
            new DamageLoggable(event)
        );
    }
}

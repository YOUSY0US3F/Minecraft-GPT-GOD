package net.bigyous.gptgodmc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;

public class EventLogger {
    private static List<Event> events = new ArrayList<>();

    public static void addEvent(Event event) {
        events.add(event);
    }

    public static List<String> logPickups(List<EntityItemPickupEvent> pickups) {
        // Map from player name to item quantity map
        HashMap<String, HashMap<String, Integer>> map = new HashMap<>();

        List<String> logs = new ArrayList<>();

        // Accumulate pickups per entity (player)
        for (EntityItemPickupEvent event : pickups) {
            String playerName = event.getEntity().getDisplayName().getString();

            if (!map.containsKey(playerName)) {
                map.put(playerName, new HashMap<String, Integer>());
            }

            String itemName = event.getItem().getDisplayName().getString();

            // Uh weird design forge but ok
            ItemStack itemStack = event.getItem().getItem();
            int itemCount = itemStack.getCount();

            HashMap<String, Integer> itemMap = map.get(playerName);

            if (!itemMap.containsKey(itemName)) {
                itemMap.put(itemName, 0);
            }

            itemMap.put(itemName, itemMap.get(itemName) + itemCount);
        }

        // Log the accumulated data
        for (Map.Entry<String, HashMap<String, Integer>> entry : map.entrySet()) {
            String playerName = entry.getKey();
            HashMap<String, Integer> itemMap = entry.getValue();

            StringBuilder sb = new StringBuilder(playerName + " picked up: ");

            for (Map.Entry<String, Integer> itemEntry : itemMap.entrySet()) {
                sb.append(itemEntry.getValue() + "x " + itemEntry.getKey() + ", ");
            }
            // Remove trailing comma
            sb.setLength(sb.length() - 2);

            logs.add(sb.toString());
        }

        return logs;
    }

    public static List<String> flushLogs() {
        List<String> logs = new ArrayList<>();

        List<EntityItemPickupEvent> pickups = new ArrayList<>();

        // Convert all capture events to logs
        for (Event event : events) {
            if (event instanceof EntityItemPickupEvent) {
                pickups.add((EntityItemPickupEvent) event);
            }
        }

        logs.addAll(logPickups(pickups));

        return logs;
    }

    public static String dump() {
        return String.join("\n", flushLogs());
    }
}

package net.bigyous.gptgodmc;
import java.util.ArrayList;
import java.util.List;

import net.bigyous.gptgodmc.loggables.Loggable;

public class EventLogger {
    private static List<Loggable> loggables = new ArrayList<>();

    public static void addLoggable(Loggable event) {
        if (loggables.size() > 0) {
            Loggable last = loggables.get(loggables.size() - 1);
            // try combine
            if (!last.combine(event)) {
                // if not combined, add to list
                loggables.add(event);
            }
        } else {
            // If empty, just add
            loggables.add(event);
        }
    }

    public static List<String> flushLogs() {
        List<String> logs = new ArrayList<>();

        // Include status summary at beginning
        logs.add(
            ServerInfoSummarizer.getStatusSummary()
        );
        
        for (Loggable event: loggables) {
            logs.add(event.getLog());
        }

        // Clear events
        // TODO: Summarize instead
        loggables.clear();

        return logs;
    }

    public static String dump() {
        return String.join("\n", flushLogs());
    }
}

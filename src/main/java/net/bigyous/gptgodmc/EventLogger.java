package net.bigyous.gptgodmc;
import java.util.ArrayList;
import java.util.List;

import net.bigyous.gptgodmc.loggables.Loggable;
import net.minecraft.server.MinecraftServer;

public class EventLogger {
    MinecraftServer server;
    ServerInfoSummarizer serverSummarizer;
    private List<Loggable> loggables = new ArrayList<>();

    public EventLogger(MinecraftServer server) {
        this.server = server;
        serverSummarizer = new ServerInfoSummarizer(server);
    }

    public void addLoggable(Loggable event) {
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

    public List<String> flushLogs() {
        List<String> logs = new ArrayList<>();

        // Include status summary at beginning
        logs.add(
            serverSummarizer.getStatusSummary()
        );
        
        for (Loggable event: loggables) {
            logs.add(event.getLog());
        }

        // Clear events
        // TODO: Summarize instead
        loggables.clear();

        return logs;
    }

    public String dump() {
        return String.join("\n", flushLogs());
    }
}

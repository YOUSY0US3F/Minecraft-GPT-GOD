package net.bigyous.gptgodmc.loggables;

import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class AttackLoggable extends BaseLoggable {
    private String attackerName;
    private String targetName;

    public AttackLoggable(AttackEntityEvent event) {
        this.attackerName = event.getEntity().getDisplayName().getString();
        this.targetName = event.getTarget().getName().getString();
    }

    @Override
    public String getLog() {
        return getFormattedTimestamp() + attackerName + " attacked " + targetName;
    }

    @Override
    public boolean combine(Loggable other) {
        // Combine logic if needed
        return false;
    }
}

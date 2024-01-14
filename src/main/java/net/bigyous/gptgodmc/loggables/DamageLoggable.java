package net.bigyous.gptgodmc.loggables;

import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class DamageLoggable extends BaseLoggable {
    private String entityName;
    private float damageAmount;
    private String damageSource;

    public DamageLoggable(LivingDamageEvent event) {
        this.entityName = event.getEntity().getName().getString();
        this.damageAmount = event.getAmount();
        this.damageSource = event.getSource().getMsgId();
    }

    @Override
    public String getLog() {
        return getFormattedTimestamp() + entityName + " took " + damageAmount + " damage from " + damageSource;
    }

    @Override
    public boolean combine(Loggable other) {
        // Combine logic if needed
        return false;
    }
}

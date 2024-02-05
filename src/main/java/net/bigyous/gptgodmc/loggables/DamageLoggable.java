package net.bigyous.gptgodmc.loggables;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class DamageLoggable extends BaseLoggable {
    private String entityName;
    private float damageAmount;
    private String damageSource;
    private boolean isValid;

    public DamageLoggable(LivingDamageEvent event) {
        this.entityName = event.getEntity().getName().getString();
        this.damageAmount = event.getAmount();
        this.damageSource = event.getSource().getMsgId();
        this.isValid = event.getEntity() instanceof Player || 
            (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player);
    }

    @Override
    public String getLog() {
        if (!isValid){
            return null;
        }
        return getFormattedTimestamp() + entityName + " took " + damageAmount + " damage from " + damageSource;
    }

    @Override
    public boolean combine(Loggable other) {
        // Combine logic if needed
        return false;
    }
}

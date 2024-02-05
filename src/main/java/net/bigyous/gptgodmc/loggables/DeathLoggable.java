package net.bigyous.gptgodmc.loggables;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
public class DeathLoggable extends BaseLoggable {
    
    private String entityName;
    private String deathType;
    private boolean isValid;

    public DeathLoggable(LivingDeathEvent event){
        this.entityName = event.getEntity().getDisplayName().getString();
        this.isValid = event.getEntity() instanceof Player;
        this.deathType = event.getSource().type().msgId();
    }

    public String getLog(){
        if(!isValid){
            return null;
        }
        return String.format("%s%s Died from %s!", getFormattedTimestamp(),entityName, deathType);
    }

}

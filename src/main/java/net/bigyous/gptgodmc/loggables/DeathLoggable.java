package net.bigyous.gptgodmc.loggables;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
public class DeathLoggable extends BaseLoggable {
    
    private String entityName;
    private boolean isValid;

    public DeathLoggable(LivingDeathEvent event){
        this.entityName = event.getEntity().getDisplayName().getString();
        this.isValid = event.getEntity() instanceof Player;
    }

    public String getLog(){
        if(!isValid){
            return null;
        }
        return String.format("%s%s Died!", getFormattedTimestamp(),entityName);
    }

}

package net.bigyous.gptgodmc.loggables;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDrownEvent;

public class DrowningLoggable extends BaseLoggable{
    private String entityName;
    private boolean isValid;

    public DrowningLoggable(LivingDrownEvent event){
        this.entityName = event.getEntity().getDisplayName().getString();
        this.isValid = event.getEntity() instanceof Player;
    }

    public String getLog(){
        if(!isValid){
            return null;
        }
        return String.format("%s%s is Drowning!", getFormattedTimestamp(),entityName);
    }
}

package net.bigyous.gptgodmc.loggables;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDrownEvent;

//Useless trash

public class DrowningLoggable extends BaseLoggable{
    private String entityName;
    private boolean isValid;

    public DrowningLoggable(LivingDrownEvent event){
        this.entityName = event.getEntity().getDisplayName().getString();
        this.isValid = event.getEntity() instanceof Player;
    }
    @Override
    public String getLog(){
        if(!isValid){
            return null;
        }
        return String.format("%s%s is Drowning!", getFormattedTimestamp(),entityName);
    }
    @Override
    public boolean combine(Loggable other){
        if(!(other instanceof DrowningLoggable)) return false;
        return true;
    }
}

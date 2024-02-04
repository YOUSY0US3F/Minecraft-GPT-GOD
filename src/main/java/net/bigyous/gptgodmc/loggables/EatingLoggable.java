package net.bigyous.gptgodmc.loggables;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraft.world.entity.player.Player;

public class EatingLoggable extends BaseLoggable{
    private String entityName;
    private String itemName;
    private boolean isValid;
    public EatingLoggable(Finish event){
       this.entityName = event.getEntity().getDisplayName().getString();
       this.itemName = event.getItem().getDisplayName().getString();
       this.isValid = event.getItem().isEdible() && event.getEntity() instanceof Player;
    }    

    @Override
    public String getLog() {
        if (!isValid){
            return null;
        }
        return getFormattedTimestamp() + entityName + " ate a " + itemName;
    }

    @Override
    public boolean combine(Loggable other) {
        return false;
    }
}

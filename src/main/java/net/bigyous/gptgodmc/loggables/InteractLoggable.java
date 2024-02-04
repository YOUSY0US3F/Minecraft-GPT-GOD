package net.bigyous.gptgodmc.loggables;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
public class InteractLoggable extends BaseLoggable {

    protected String playerName;
    protected String targetName;
    protected String itemName;
    private int times;

    public InteractLoggable(EntityInteract event){
        this.playerName = event.getEntity().getDisplayName().getString();
        this.targetName = event.getTarget().getDisplayName().getString();
        this.itemName = event.getItemStack() == ItemStack.EMPTY? null : event.getItemStack().getDisplayName().getString();
        this.times = 1;
    }

    @Override
    public String getLog() {
        StringBuilder sb = new StringBuilder(getFormattedTimestamp() + playerName);
        if(times > 2){
            sb.append(" repeatedly ");
        }
        if(itemName != null){
            sb.append("tried to use " + itemName + " on ");
        }
        else{
            sb.append("interacted with ");
        }
        sb.append(targetName);
        return sb.toString();

    }

    public boolean equals(InteractLoggable other){
        return playerName.equals(other.playerName) && itemName.equals(other.itemName)
            && targetName.equals(other.targetName);
    }

    @Override
    public boolean combine(Loggable other) {
        if (!(other instanceof InteractLoggable)) return false;

        InteractLoggable otherInteraction = (InteractLoggable) other;

        if (this.equals(otherInteraction)){
            this.times += 1;
            return true;
        }
        return false;
    }
    
}

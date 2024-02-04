package net.bigyous.gptgodmc.loggables;

import net.minecraftforge.event.entity.player.ItemFishedEvent;

public class FishingLoggable extends BaseLoggable {
    
    private String playerName;
    private String fishedItemName;

    public FishingLoggable(ItemFishedEvent event){
        this.playerName = event.getEntity().getDisplayName().getString();
        this.fishedItemName = event.getDrops().isEmpty()? null : event.getDrops().get(0).getDisplayName().getString();
    }
    public String getLog(){
        if (fishedItemName== null){
            return null;
        }
        return String.format("%s%s fished a %s!", getFormattedTimestamp(), playerName, fishedItemName);
    }
}

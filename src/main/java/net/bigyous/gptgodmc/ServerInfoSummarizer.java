package net.bigyous.gptgodmc;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ServerInfoSummarizer {
    public static String getInventoryInfo(ServerPlayer player) {
        StringBuilder sb = new StringBuilder();
        // Armor Items
        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

        sb.append("Armor: " + formatItemStack(head) + ", " + formatItemStack(chest) + ", " + formatItemStack(legs) + ", " + formatItemStack(feet) + "\n");

        // Inventory Items
        sb.append("Inventory: ");
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty()) {
                sb.append(formatItemStack(stack) + ", ");
            }
        }
        sb.append("\n");

        // Equipped Item (Main Hand)
        ItemStack equipped = player.getMainHandItem();
        sb.append("Equipped: " + formatItemStack(equipped));
        return sb.toString();
    }

    private static String formatItemStack(ItemStack stack) {
        if (stack.isEmpty()) {
            return "None";
        }
        //stack.getItem()
        return stack.getDisplayName().getString().replaceAll("[\\[\\]]", "") + " x" + stack.getCount();
    }

    public static String getStatusSummary() {
        StringBuilder sb = new StringBuilder("=== Server Status ===\n");
        List<ServerPlayer> players = GPTGOD.SERVER.getPlayerList().getPlayers();

        for (ServerPlayer player : players) {
            //player.getP
            String name = player.getDisplayName().getString();
            float health = player.getHealth();
            float maxHealth = player.getMaxHealth();
            boolean isDead = player.isDeadOrDying();
            //player.getInventory()
            String inventoryInfo = getInventoryInfo(player);

            sb.append("Status of Player " + name + ":\n");
            sb.append("Health: " + health + "/" + maxHealth + "\n");
            // sb.append("\tDead? " + isDead + "\n");
            // sb.append("\tInventory: " + inventoryInfo + "\n");
            sb.append("Dead? " + isDead + "\n");
            sb.append(inventoryInfo + "\n");
        }

        sb.append("==================");
        return sb.toString();
    }
}

package me.smajt.rimcraft.Models;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemCD {
    private UUID playerUuid;
    private ItemStack itemStack;

    public ItemCD(UUID playerUuid, ItemStack itemStack) {
        this.playerUuid = playerUuid;
        this.itemStack = itemStack;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}

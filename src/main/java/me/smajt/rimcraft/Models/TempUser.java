package me.smajt.rimcraft.Models;

import java.util.UUID;

public class TempUser {
    private UUID uuid;
    private double bodyTemp;

    public TempUser(UUID uuid, double bodyTemp) {
        this.uuid = uuid;
        this.bodyTemp = bodyTemp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(double bodyTemp) {
        this.bodyTemp = bodyTemp;
    }
}

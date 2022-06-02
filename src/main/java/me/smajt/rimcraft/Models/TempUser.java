package me.smajt.rimcraft.Models;

import java.util.UUID;

public class TempUser {
    private UUID uuid;
    private double bodyTemp;
    private boolean isDowned;

    public TempUser(UUID uuid, double bodyTemp, boolean isDowned) {
        this.uuid = uuid;
        this.bodyTemp = bodyTemp;
        this.isDowned = isDowned;
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

    public boolean isDowned() {
        return isDowned;
    }

    public void setDowned(boolean downed) {
        isDowned = downed;
    }
}

package me.smajt.rimcraft.Models;

public class Biome {

    private String biomeName;
    private double targetBodyTemp;

    public Biome(String biomeName, double targetBodyTemp) {
        this.biomeName = biomeName;
        this.targetBodyTemp = targetBodyTemp;
    }

    public String getBiomeName() {
        return biomeName;
    }

    public void setBiomeName(String biomeName) {
        this.biomeName = biomeName;
    }

    public double getTargetBodyTemp() {
        return targetBodyTemp;
    }

    public void setTargetBodyTemp(double targetBodyTemp) {
        this.targetBodyTemp = targetBodyTemp;
    }
}

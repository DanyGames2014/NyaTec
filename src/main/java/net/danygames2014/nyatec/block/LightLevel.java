package net.danygames2014.nyatec.block;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum LightLevel implements StringIdentifiable {
    FULL("full", 15),
    REDUCED("reduced", 11),
    DIM("dim", 7),
    OFF("off", 0);

    private final String name;
    public final int lightLevel;
    
    LightLevel(String name, int lightLevel) {
        this.name = name;
        this.lightLevel = lightLevel;
    }
    
    public static LightLevel fromLevel(int level) {
        return switch (level) {
            case 15, 14 -> FULL;
            case 13, 12, 11, 10 -> REDUCED;
            case 9, 8, 7, 6, 5, 4 -> DIM;
            default -> OFF;
        };
    }

    @Override
    public String asString() {
        return this.name;
    }
}

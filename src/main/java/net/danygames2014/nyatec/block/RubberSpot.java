package net.danygames2014.nyatec.block;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

import java.util.Random;

public enum RubberSpot implements StringIdentifiable {
    EAST("east"),
    WEST("west"),
    NORTH("north"),
    SOUTH("south"),
    NONE("none");

    private final String name;
    
    RubberSpot(String minediver) {
        this.name = minediver;
    }
    
    public static RubberSpot getRandomSide(Random random) {
        RubberSpot[] values = RubberSpot.values();
        return values[random.nextInt(values.length - 1)];
    }

    @Override
    public String asString() {
        return this.name;
    }
}

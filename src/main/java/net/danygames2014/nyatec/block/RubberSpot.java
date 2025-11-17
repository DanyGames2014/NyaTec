package net.danygames2014.nyatec.block;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

import java.util.Random;

public enum RubberSpot implements StringIdentifiable {
    EAST("east", 5),
    WEST("west", 4),
    NORTH("north", 2),
    SOUTH("south", 3),
    NONE("none", -1);

    private final String name;
    public final int side;
    
    RubberSpot(String name, int side) {
        this.name = name;
        this.side = side;
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

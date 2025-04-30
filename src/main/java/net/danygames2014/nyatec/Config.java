package net.danygames2014.nyatec;

import net.glasslauncher.mods.gcapi3.api.ConfigCategory;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    public static class WorldgenConfig {
        @ConfigCategory(name = "Copper Ore")
        public CopperOreConfig copperOre = new CopperOreConfig();
        
        public static class CopperOreConfig {
            @ConfigEntry(name = "Generate Copper Ore")
            public Boolean generateCopperOre = true;
            
            @ConfigEntry(name = "Ore Count", comment = "The maximum amount of ores that can generate", maxLength = 32)
            public Integer oreCount = 12;
            
            @ConfigEntry(name = "Ore veins per Chunk", maxLength = 32)
            public Integer oreVeinsPerChunk = 20;
            
            @ConfigEntry(name = "Minimum Y Level", maxLength = 256)
            public Integer minimumYLevel = 40;
            
            @ConfigEntry(name = "Maximum Y Level", maxLength = 256)
            public Integer maximumYLevel = 70;
        }
    }
    
    public static class MachineConfig {
        @ConfigCategory(name = "Generator")
        public GeneratorConfig generator = new GeneratorConfig();
        
        public static class GeneratorConfig {
            @ConfigEntry(name = "Fuel Consumption Rate", description = "How many fuel ticks the generator can convert to power per tick")
            public Integer fuelConsumptionRate = 4;
            
            @ConfigEntry(name = "Energy Per Fuel Tick", description = "How much energy each fuel tick will yield")
            public Double energyPerFuelTick = 2.5D;
            
            @ConfigEntry(name = "Fuel Buffer", description = "The fuel buffer size of the generator")
            public Integer fuelBuffer = 2000;
        }
    }
}

package net.danygames2014.nyatec;

import net.glasslauncher.mods.gcapi3.api.ConfigCategory;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    public static class WorldgenConfig {
        @ConfigCategory(name = "Copper Ore")
        public CopperOreConfig copperOre = new CopperOreConfig();
        
        @ConfigCategory(name = "Rubber Tree")
        public RubberTreeConfig rubberTree = new RubberTreeConfig();
        
        public static class CopperOreConfig {
            @ConfigEntry(name = "Generate Copper Ore")
            public Boolean generateCopperOre = true;
            
            @ConfigEntry(name = "Ore Count", comment = "The maximum amount of ores that can generate", maxValue = 32)
            public Integer oreCount = 12;
            
            @ConfigEntry(name = "Ore veins per Chunk", maxValue = 32)
            public Integer oreVeinsPerChunk = 20;
            
            @ConfigEntry(name = "Minimum Y Level", maxValue = 256)
            public Integer minimumYLevel = 40;
            
            @ConfigEntry(name = "Maximum Y Level", maxValue = 256)
            public Integer maximumYLevel = 70;
        }
        
        public static class RubberTreeConfig {
            @ConfigEntry(name = "Generate Rubber Trees")
            public Boolean generateRubberTrees = true;
            
            @ConfigEntry(name = "Latex spot chance (1 in n)", minValue = 1, maxValue = 16)
            public Integer latexSpotChance = 4;
            
            @ConfigEntry(name = "Maximum Latex Spots", minValue = 1, maxValue = 16)
            public Integer maximumLatexSpots = 3;
        }
    }
    
    public static class MachineConfig {
        @ConfigEntry(name = "Redstone Energy Value", description = "How much energy units will redstone yield", minValue = 1, maxValue = 1000)
        public Integer redstoneEnergyValue = 50;
        
        @ConfigCategory(name = "Generator")
        public GeneratorConfig generator = new GeneratorConfig();
        
        public static class GeneratorConfig {
            @ConfigEntry(name = "Fuel Consumption Rate", description = "How many fuel ticks the generator can convert to power per tick")
            public Integer fuelConsumptionRate = 4;
            
            @ConfigEntry(name = "Energy Per Fuel Tick", description = "How much energy each fuel tick will yield")
            public Double energyPerFuelTick = 2.5D;
            
            @ConfigEntry(name = "Fuel Buffer", description = "The fuel buffer size of the generator", maxValue = 10000)
            public Integer fuelBuffer = 2000;
        }
    }
}

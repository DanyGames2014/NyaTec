package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.world.feature.RubberTreeFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.world.WorldEvent;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.worldgen.feature.HeightScatterFeature;

public class WorldGenListener {
    public static RubberTreeFeature rubberTreeFeature;

    @EventListener
    public void initFeatures(WorldEvent.Init event) {
        rubberTreeFeature = new RubberTreeFeature(event.world, 1);
    }
    
    @EventListener
    public void registerFeatures(BiomeModificationEvent event) {
        int rarity = 10000;

        if (event.biome == Biome.RAINFOREST) {
            rarity = 4; // 1/4 = 25%
        } else if (event.biome == Biome.SWAMPLAND) {
            rarity = 6; // 1/6 = 16%
        } else if (event.biome == Biome.SEASONAL_FOREST) {
            rarity = 20; // 1/20 = 5%
        } else if (event.biome == Biome.FOREST) {
            rarity = 50; // 1/50 = 2%
        } else if (event.biome == Biome.DESERT || event.biome == Biome.SAVANNA || event.biome == Biome.HELL || event.biome == Biome.ICE_DESERT || event.biome == Biome.SKY || event.biome == Biome.TAIGA || event.biome == Biome.TUNDRA || event.biome == Biome.SHRUBLAND || event.biome == Biome.PLAINS) {
            rarity = Integer.MAX_VALUE; // None
        } else {
            if (event.biome.canSnow() || !event.biome.canRain()) {
                rarity = Integer.MAX_VALUE;
            }

            if (event.biome.canRain()) {
                rarity = 100;
            }
        }

        if (rarity <= 1000) {
            event.biome.addFeature(new HeightScatterFeature(new RubberTreeFeature(event.world, rarity), 1));
        }
    }
}

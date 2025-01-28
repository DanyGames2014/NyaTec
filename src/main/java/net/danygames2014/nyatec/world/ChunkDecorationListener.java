package net.danygames2014.nyatec.world;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.world.feature.VanillaOreFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.event.world.WorldEvent;
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent;

import java.util.ArrayList;
import java.util.Random;

import static net.danygames2014.nyatec.NyaTec.WORLDGEN_CONFIG;

public class ChunkDecorationListener {
    public static ArrayList<OreGenEntry> oreGenEntries;

    public static VanillaOreFeature copperVanillaOreFeature;

    @EventListener
    public void decorate(WorldGenEvent.ChunkDecoration event) {
        if (event.world.dimension.id == 0) {
            decorateOverworld(event);
        }
    }

    public void decorateOverworld(WorldGenEvent.ChunkDecoration event) {
        for (var entry : oreGenEntries) {
            entry.generate(event.world, event.random, event.x, event.z);
        }
    }

    @EventListener
    public void initFeatures(WorldEvent.Init event) {
        oreGenEntries = new ArrayList<>();

        if (WORLDGEN_CONFIG.copperOre.generateCopperOre) {
            copperVanillaOreFeature = new VanillaOreFeature(NyaTec.copperOre, WORLDGEN_CONFIG.copperOre.oreCount);
            oreGenEntries.add(new OreGenEntry(copperVanillaOreFeature, WORLDGEN_CONFIG.copperOre.oreCount, WORLDGEN_CONFIG.copperOre.oreVeinsPerChunk, WORLDGEN_CONFIG.copperOre.minimumYLevel, WORLDGEN_CONFIG.copperOre.maximumYLevel));
        }
    }

    public static class OreGenEntry {
        public Feature oreFeature;
        public int oreCount;
        public int veinsPerChunk;
        public int minY;
        public int randomSpread;

        public OreGenEntry(Feature oreFeature, int oreCount, int veinsPerChunk, int minY, int maxY) {
            this.oreFeature = oreFeature;
            this.oreCount = oreCount;
            this.veinsPerChunk = veinsPerChunk;
            this.minY = minY;

            if (maxY >= minY) {
                this.randomSpread = (maxY - minY) + 1;
            } else {
                throw new IllegalArgumentException("maxY must be greater or equal to minY for ore feature of type " + oreFeature);
            }
        }

        public void generate(World world, Random random, int x, int z) {
            if (this.minY < world.getBottomY()) {
                NyaTec.LOGGER.warn("Tried to generate an ore of type " + oreFeature + " but the minY " + this.minY + " is lower than the world bottom height " + world.getBottomY());
                return;
            }
            
            if (this.minY + randomSpread > world.getHeight()) {
                NyaTec.LOGGER.warn("Tried to generate an ore of type " + oreFeature + " but the maxY " + (this.minY + randomSpread) + " is higher than the world height " + world.getHeight());
                return;
            }

            for (int i = 0; i < this.veinsPerChunk; i++) {
                this.oreFeature.generate(world, random, x + random.nextInt(16), minY + random.nextInt(randomSpread), z + random.nextInt(16));
            }
        }
    }
}

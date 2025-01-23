package net.danygames2014.nyatec.world;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.world.feature.OldOreFeature;
import net.danygames2014.nyatec.world.feature.OreFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.world.WorldEvent;
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent;

public class ChunkDecorationListener {
    public static OreFeature copperOreFeature;
    
    @EventListener
    public void decorate(WorldGenEvent.ChunkDecoration event) {
        if (event.world.dimension.id == 0) {
            decorateOverworld(event);
        }
    }

    public void decorateOverworld(WorldGenEvent.ChunkDecoration event) {
        copperOreFeature.generate(event.world, event.random, event.x, event.random.nextInt(20) + 70, event.z);
    }

    @EventListener
    public void initFeatures(WorldEvent.Init event) {
        copperOreFeature = new OreFeature(NyaTec.copperOre, 8);
    }
}

package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.modificationstation.stationapi.api.client.event.render.model.BlockModelPredicateProviderRegistryEvent;

@Environment(EnvType.CLIENT)
public class ClientListener {

    @EventListener
    public void registerBlockModelPredicates(BlockModelPredicateProviderRegistryEvent event) {
        event.registry.register(
            NyaTec.rubberLeaves, 
            NyaTec.NAMESPACE.id("graphics"), 
            (blockState, blockView, blockPos, i) -> {
                return Minecraft.isFancyGraphicsEnabled() ? 1.0F : 0.0F;
            }
        );
    }
}

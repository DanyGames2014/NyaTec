package net.danygames2014.nyatec.init;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;

public class InitListener {
    @EventListener(phase = InitEvent.PRE_INIT_PHASE)
    public void preInitListener(InitEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("nyatec", Object.class).forEach(EntrypointManager::setup);
    }
}

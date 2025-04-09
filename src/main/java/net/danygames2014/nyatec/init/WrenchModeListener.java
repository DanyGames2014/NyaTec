package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.api.event.WrenchModeRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;

public class WrenchModeListener {
    public static WrenchMode FLOW_MODE;
    public static WrenchMode BATTERY_MODE;
    
    @EventListener
    public void registerWrenchModes(WrenchModeRegistryEvent event) {
        FLOW_MODE = new WrenchMode(NyaTec.NAMESPACE.id("flow"));
        BATTERY_MODE = new WrenchMode(NyaTec.NAMESPACE.id("battery"));
    }
}

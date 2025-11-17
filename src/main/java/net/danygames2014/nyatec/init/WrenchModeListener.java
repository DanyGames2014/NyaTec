package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.item.wrenchmode.TreeTapWrenchMode;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.api.event.WrenchModeRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;

public class WrenchModeListener {
    public static WrenchMode FLOW_MODE;
    public static WrenchMode BATTERY_MODE;
    public static WrenchMode DEBUG_MODE;
    public static WrenchMode flowMode;
    public static WrenchMode batteryMode;
    public static WrenchMode debugMode;
    
    @EventListener
    public void registerWrenchModes(WrenchModeRegistryEvent event) {
        FLOW_MODE = new WrenchMode(NyaTec.NAMESPACE.id("flow"));
        BATTERY_MODE = new WrenchMode(NyaTec.NAMESPACE.id("battery"));
        DEBUG_MODE = new WrenchMode(NyaTec.NAMESPACE.id("debug"));
        flowMode = new WrenchMode(NyaTec.NAMESPACE.id("flow"));
        batteryMode = new WrenchMode(NyaTec.NAMESPACE.id("battery"));
        debugMode = new WrenchMode(NyaTec.NAMESPACE.id("debug"));
    }
}

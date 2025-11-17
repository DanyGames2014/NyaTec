package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.item.wrenchmode.TreeTapWrenchMode;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.api.event.WrenchModeRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;

public class WrenchModeListener {
    public static WrenchMode flowMode;
    public static WrenchMode batteryMode;
    public static WrenchMode debugMode;
    public static WrenchMode treeTapMode;
    
    @EventListener
    public void registerWrenchModes(WrenchModeRegistryEvent event) {
        flowMode = new WrenchMode(NyaTec.NAMESPACE.id("flow"));
        batteryMode = new WrenchMode(NyaTec.NAMESPACE.id("battery"));
        debugMode = new WrenchMode(NyaTec.NAMESPACE.id("debug"));
        treeTapMode = new TreeTapWrenchMode(NyaTec.NAMESPACE.id("tree_tap"));
    }
}

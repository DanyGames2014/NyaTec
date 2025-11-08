package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.event.InductionFurnaceRecipeRegisterEvent;
import net.danygames2014.nyatec.event.MaceratorRecipeRegisterEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;

public class AfterBlockAndItemListener {
    @EventListener
    public void afterBlockAndItemListener(AfterBlockAndItemRegisterEvent event) {
        StationAPI.EVENT_BUS.post(new MaceratorRecipeRegisterEvent());
        StationAPI.EVENT_BUS.post(new InductionFurnaceRecipeRegisterEvent());
    }
}

package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.block.entity.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

@SuppressWarnings("unused")
public class BlockEntityListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;
    
    @EventListener
    public void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(GeneratorBlockEntity.class, NAMESPACE.id("generator").toString());
        event.register(EnergyTrashCanBlockEntity.class, NAMESPACE.id("energy_trash_can").toString());
        event.register(ElectricFurnaceBlockEntity.class, NAMESPACE.id("electric_furnace").toString());
        event.register(MaceratorBlockEntity.class, NAMESPACE.id("macerator").toString());
        event.register(BatteryBoxBlockEntity.class, NAMESPACE.id("battery_box").toString());
        event.register(InductionFurnaceBlockEntity.class, NAMESPACE.id("induction_furnace").toString());
        event.register(ElectricLightBlockEntity.class, NAMESPACE.id("electric_light").toString());
        event.register(CreativeEnergySourceBlockEntity.class, NAMESPACE.id("creative_energy_source").toString());
    }
}

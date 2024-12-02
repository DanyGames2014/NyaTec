package net.danygames2014.nyatec;

import net.danygames2014.nyatec.block.template.RotateableBlockTemplate;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class NyaTec {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static final Logger LOGGER = Null.get();
    
    public static Block rubberLog;
    public static Block rubberPlanks;
    
    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        rubberLog = new RotateableBlockTemplate(NAMESPACE.id("rubber_log"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_log").setHardness(1.0F).setResistance(0.1F);
        rubberPlanks = new RotateableBlockTemplate(NAMESPACE.id("rubber_planks"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_planks").setHardness(1.0F).setResistance(0.1F);
    }
}

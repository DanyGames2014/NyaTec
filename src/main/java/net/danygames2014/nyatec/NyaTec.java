package net.danygames2014.nyatec;

import net.danygames2014.nyalib.block.RotateableBlockTemplate;
import net.danygames2014.nyatec.block.RubberLeavesBlock;
import net.danygames2014.nyatec.block.RubberSaplingBlock;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class NyaTec {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();
    
    public static Block rubberLog;
    public static Block rubberPlanks;
    public static Block rubberLeaves;
    public static Block rubberSapling;
    
    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        rubberLog = new RotateableBlockTemplate(NAMESPACE.id("rubber_log"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_log").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberPlanks = new RotateableBlockTemplate(NAMESPACE.id("rubber_planks"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_planks").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        rubberLeaves = new RubberLeavesBlock(NAMESPACE.id("rubber_leaves"), Material.LEAVES).setTranslationKey(NAMESPACE, "rubber_leaves").setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        rubberSapling = new RubberSaplingBlock(NAMESPACE.id("rubber_sapling"), Material.PLANT).setTranslationKey(NAMESPACE, "rubber_sapling").setSoundGroup(Block.DIRT_SOUND_GROUP);
    }
}

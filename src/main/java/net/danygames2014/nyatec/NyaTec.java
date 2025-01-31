package net.danygames2014.nyatec;

import net.danygames2014.nyalib.block.RotateableBlockTemplate;
import net.danygames2014.nyalib.network.NetworkComponent;
import net.danygames2014.nyatec.block.CableBlockTemplate;
import net.danygames2014.nyatec.block.RubberLeavesBlock;
import net.danygames2014.nyatec.block.RubberSaplingBlock;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.world.BlockSetEvent;
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

    @ConfigRoot(value = "worldgen", visibleName = "World Generation")
    public static final Config.WorldgenConfig WORLDGEN_CONFIG = new Config.WorldgenConfig();

    public static Block rubberLog;
    public static Block rubberPlanks;
    public static Block rubberLeaves;
    public static Block rubberSapling;

    public static Block copperOre;

    public static Block testCable;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        rubberLog = new RotateableBlockTemplate(NAMESPACE.id("rubber_log"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_log").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberPlanks = new RotateableBlockTemplate(NAMESPACE.id("rubber_planks"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_planks").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberSapling = new RubberSaplingBlock(NAMESPACE.id("rubber_sapling"), Material.PLANT).setTranslationKey(NAMESPACE, "rubber_sapling").setSoundGroup(Block.DIRT_SOUND_GROUP);
        rubberLeaves = new RubberLeavesBlock(NAMESPACE.id("rubber_leaves"), Material.LEAVES, new ItemStack(rubberSapling, 1), rubberLog).setTranslationKey(NAMESPACE, "rubber_leaves").setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP);

        copperOre = new TemplateBlock(NAMESPACE.id("copper_ore"), Material.STONE).setTranslationKey(NAMESPACE, "copper_ore").setHardness(3.0F).setResistance(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP);

        testCable = new CableBlockTemplate(NAMESPACE.id("test_cable"), Material.WOOL).setTranslationKey(NAMESPACE, "test_cable").setHardness(0.2F).setResistance(0.5F).setSoundGroup(Block.WOOL_SOUND_GROUP);
    }

//    @EventListener
//    public void blockSetListener(BlockSetEvent event) {
//        // Override State is Not Null
//        // Override State is a Network Component
//        if (event.overrideState != null && event.overrideState.getBlock() instanceof NetworkComponent) {
//            System.out.println("Cable Placed");
//        }
//
//        // Current Block State is Not Null
//        // Current Block State is a Network Component
//        // Override State is Not Null
//        // Override State is Not a Network Component
//        if (event.blockState.getBlock() != null && event.blockState.getBlock() instanceof NetworkComponent && (event.overrideState == null || !(event.overrideState.getBlock() instanceof NetworkComponent))) {
//            System.out.println("Cable Removed");
//        }
//    }
}

package net.danygames2014.nyatec;

import net.danygames2014.nyalib.block.RotateableBlockTemplate;
import net.danygames2014.nyatec.block.*;
import net.danygames2014.nyatec.block.entity.EnergyTrashCanBlockEntity;
import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.danygames2014.nyatec.block.material.CableMaterial;
import net.danygames2014.nyatec.item.MultimeterItem;
import net.danygames2014.nyatec.screen.GeneratorScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
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
    
    @ConfigRoot(value = "machine", visibleName = "Machine")
    public static final Config.MachineConfig MACHINE_CONFIG = new Config.MachineConfig();

    public static Block rubberLog;
    public static Block rubberPlanks;
    public static Block rubberLeaves;
    public static Block rubberSapling;

    public static Block copperOre;

    public static Block generatorBlock;
    public static Block energyTrashCanBlock;
    public static Block testCable;
    
    public static Material cableMaterial = new CableMaterial(MapColor.LIGHT_GRAY).setTransparent();
    
    public static Item multimeter;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        multimeter = new MultimeterItem(NAMESPACE.id("multimeter")).setTranslationKey(NAMESPACE, "multimeter");
    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        rubberLog = new RotateableBlockTemplate(NAMESPACE.id("rubber_log"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_log").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberPlanks = new RotateableBlockTemplate(NAMESPACE.id("rubber_planks"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_planks").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberSapling = new RubberSaplingBlock(NAMESPACE.id("rubber_sapling"), Material.PLANT).setTranslationKey(NAMESPACE, "rubber_sapling").setSoundGroup(Block.DIRT_SOUND_GROUP);
        rubberLeaves = new RubberLeavesBlock(NAMESPACE.id("rubber_leaves"), Material.LEAVES, new ItemStack(rubberSapling, 1), rubberLog).setTranslationKey(NAMESPACE, "rubber_leaves").setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP);

        copperOre = new TemplateBlock(NAMESPACE.id("copper_ore"), Material.STONE).setTranslationKey(NAMESPACE, "copper_ore").setHardness(3.0F).setResistance(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP);

        testCable = new CableBlockTemplate(NAMESPACE.id("test_cable"), cableMaterial).setTranslationKey(NAMESPACE, "test_cable").setHardness(0.2F).setResistance(0.5F).setSoundGroup(Block.WOOL_SOUND_GROUP);
        energyTrashCanBlock = new EnergyTrashCanBlock(NAMESPACE.id("energy_trash_can"), Material.METAL).setTranslationKey(NAMESPACE, "energy_trash_can").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        generatorBlock = new GeneratorBlock(NAMESPACE.id("generator"), Material.METAL).setTranslationKey(NAMESPACE, "generator").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
    }

    @EventListener
    public void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(GeneratorBlockEntity.class, NAMESPACE.id("generator").toString());
        event.register(EnergyTrashCanBlockEntity.class, NAMESPACE.id("energy_trash_can").toString());
    }

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerScreenHandlers(GuiHandlerRegistryEvent event) {
        event.register(NAMESPACE.id("generator"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openGenerator, GeneratorBlockEntity::new));
    }

    @Environment(EnvType.CLIENT)
    private Screen openGenerator(PlayerEntity player, Inventory inventory) {
        return new GeneratorScreen(player, (GeneratorBlockEntity) inventory);
    }
}

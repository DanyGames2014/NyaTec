package net.danygames2014.nyatec;

import net.danygames2014.nyalib.block.RotateableBlockTemplate;
import net.danygames2014.nyatec.block.*;
import net.danygames2014.nyatec.block.cable.GoldenCableBlock;
import net.danygames2014.nyatec.block.cable.RubberCableBlock;
import net.danygames2014.nyatec.block.entity.*;
import net.danygames2014.nyatec.block.material.CableMaterial;
import net.danygames2014.nyatec.item.BatteryItem;
import net.danygames2014.nyatec.item.MultimeterItem;
import net.danygames2014.nyatec.item.TreeTapItem;
import net.danygames2014.nyatec.screen.*;
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
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class NyaTec {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    @ConfigRoot(value = "worldgen", visibleName = "World Generation")
    public static final Config.WorldgenConfig WORLDGEN_CONFIG = new Config.WorldgenConfig();

    @ConfigRoot(value = "machine", visibleName = "Machine")
    public static final Config.MachineConfig MACHINE_CONFIG = new Config.MachineConfig();

    public static Block rubberLog;
    public static Block rubberPlanks;
    public static Block rubberLeaves;
    public static Block rubberSapling;

    public static Block copperOre;
    public static Block tinOre;

    public static Block generatorBlock;
    public static Block energyTrashCanBlock;
    public static Block electricFurnaceBlock;
    public static Block maceratorBlock;
    public static Block batteryBoxBlock;
    public static Block inductionFurnaceBlock;
    
    public static Block creativeEnergySourceBlock;

    public static Block whiteElectricLight;
    public static Block orangeElectricLight;
    public static Block magentaElectricLight;
    public static Block lightBlueElectricLight;
    public static Block yellowElectricLight;
    public static Block limeElectricLight;
    public static Block pinkElectricLight;
    public static Block grayElectricLight;
    public static Block lightGrayElectricLight;
    public static Block cyanElectricLight;
    public static Block purpleElectricLight;
    public static Block blueElectricLight;
    public static Block brownElectricLight;
    public static Block greenElectricLight;
    public static Block redElectricLight;
    public static Block blackElectricLight;
    
    public static Block electricLightFixture;
    
    public static Material cableMaterial = new CableMaterial(MapColor.LIGHT_GRAY).setTransparent();

    public static Block rubberCable;
    public static Block goldenCable;

    public static Item multimeter;
    public static Item treeTap;
    public static Item battery;
    public static Item latex;
    public static Item rubber;
    
    public static Item ironDust;
    public static Item copperDust;
    public static Item tinDust;
    public static Item goldDust;
    public static Item bronzeDust;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        multimeter = new MultimeterItem(NAMESPACE.id("multimeter")).setTranslationKey(NAMESPACE, "multimeter");
        battery = new BatteryItem(NAMESPACE.id("basic_battery"), 2000).setTranslationKey(NAMESPACE, "basic_battery");
        latex = new TemplateItem(NAMESPACE.id("latex")).setTranslationKey(NAMESPACE, "latex");
        treeTap = new TreeTapItem(NAMESPACE.id("treetap")).setTranslationKey(NAMESPACE, "treetap");
        rubber = new TemplateItem(NAMESPACE.id("rubber")).setTranslationKey(NAMESPACE, "rubber");
        
        ironDust = new TemplateItem(NAMESPACE.id("iron_dust")).setTranslationKey(NAMESPACE, "iron_dust");
        copperDust = new TemplateItem(NAMESPACE.id("copper_dust")).setTranslationKey(NAMESPACE, "copper_dust");
        tinDust = new TemplateItem(NAMESPACE.id("tin_dust")).setTranslationKey(NAMESPACE, "tin_dust");
        goldDust = new TemplateItem(NAMESPACE.id("gold_dust")).setTranslationKey(NAMESPACE, "gold_dust");
        bronzeDust = new TemplateItem(NAMESPACE.id("bronze_dust")).setTranslationKey(NAMESPACE, "bronze_dust");
    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        rubberLog = new RubberLogBlock(NAMESPACE.id("rubber_log"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_log").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberPlanks = new RotateableBlockTemplate(NAMESPACE.id("rubber_planks"), Material.WOOD).setTranslationKey(NAMESPACE, "rubber_planks").setHardness(1.0F).setResistance(0.1F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        rubberSapling = new RubberSaplingBlock(NAMESPACE.id("rubber_sapling"), Material.PLANT).setTranslationKey(NAMESPACE, "rubber_sapling").setSoundGroup(Block.DIRT_SOUND_GROUP);
        rubberLeaves = new RubberLeavesBlock(NAMESPACE.id("rubber_leaves"), Material.LEAVES, rubberLog).setTranslationKey(NAMESPACE, "rubber_leaves").setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP);

        copperOre = new TemplateBlock(NAMESPACE.id("copper_ore"), Material.STONE).setTranslationKey(NAMESPACE, "copper_ore").setHardness(3.0F).setResistance(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP);
        tinOre = new TemplateBlock(NAMESPACE.id("tin_ore"), Material.STONE).setTranslationKey(NAMESPACE, "tin_ore").setHardness(3.0F).setResistance(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP);

        energyTrashCanBlock = new EnergyTrashCanBlock(NAMESPACE.id("energy_trash_can"), Material.METAL).setTranslationKey(NAMESPACE, "energy_trash_can").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        generatorBlock = new GeneratorBlock(NAMESPACE.id("generator"), Material.METAL).setTranslationKey(NAMESPACE, "generator").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        electricFurnaceBlock = new ElectricFurnaceBlock(NAMESPACE.id("electric_furnace"), Material.METAL).setTranslationKey(NAMESPACE, "electric_furnace").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        maceratorBlock = new MaceratorBlock(NAMESPACE.id("macerator"), Material.METAL).setTranslationKey(NAMESPACE, "macerator").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        batteryBoxBlock = new BatteryBoxBlock(NAMESPACE.id("battery_box"), Material.METAL).setTranslationKey(NAMESPACE, "battery_box").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        inductionFurnaceBlock = new InductionFurnaceBlock(NAMESPACE.id("induction_furnace"), Material.METAL).setTranslationKey(NAMESPACE, "induction_furnace").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);

        creativeEnergySourceBlock = new CreativeEnergySourceBlock(NAMESPACE.id("creative_energy_source"), Material.METAL).setTranslationKey(NAMESPACE, "creative_energy_source").setHardness(2.0F).setResistance(2.0F).setSoundGroup(Block.METAL_SOUND_GROUP);
        
        whiteElectricLight = new ElectricLightFullBlock(NAMESPACE.id("white_electric_light"), Material.GLASS, 0xFFFFFF).setTranslationKey(NAMESPACE, "white_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        orangeElectricLight = new ElectricLightFullBlock(NAMESPACE.id("orange_electric_light"), Material.GLASS, 0xCC6900).setTranslationKey(NAMESPACE, "orange_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        magentaElectricLight = new ElectricLightFullBlock(NAMESPACE.id("magenta_electric_light"), Material.GLASS, 0xCC1DCC).setTranslationKey(NAMESPACE, "magenta_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        lightBlueElectricLight = new ElectricLightFullBlock(NAMESPACE.id("light_blue_electric_light"), Material.GLASS, 0x7386E6).setTranslationKey(NAMESPACE, "light_blue_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        yellowElectricLight = new ElectricLightFullBlock(NAMESPACE.id("yellow_electric_light"), Material.GLASS, 0xE6E645).setTranslationKey(NAMESPACE, "yellow_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        limeElectricLight = new ElectricLightFullBlock(NAMESPACE.id("lime_eletric_light"), Material.GLASS, 0x4FE600).setTranslationKey(NAMESPACE, "lime_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        pinkElectricLight = new ElectricLightFullBlock(NAMESPACE.id("pink_electric_light"), Material.GLASS, 0xF285CE).setTranslationKey(NAMESPACE, "pink_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        grayElectricLight = new ElectricLightFullBlock(NAMESPACE.id("gray_electric_light"), Material.GLASS, 0x454545).setTranslationKey(NAMESPACE, "gray_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        lightGrayElectricLight = new ElectricLightFullBlock(NAMESPACE.id("light_gray_electric_light"), Material.GLASS, 0x808080).setTranslationKey(NAMESPACE, "light_gray_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        cyanElectricLight = new ElectricLightFullBlock(NAMESPACE.id("cyan_electric_light"), Material.GLASS, 0x009E9E).setTranslationKey(NAMESPACE, "cyan_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        purpleElectricLight = new ElectricLightFullBlock(NAMESPACE.id("purple_electric_light"), Material.GLASS, 0x7829CC).setTranslationKey(NAMESPACE, "purple_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        blueElectricLight = new ElectricLightFullBlock(NAMESPACE.id("blue_electric_light"), Material.GLASS, 0x2929CC).setTranslationKey(NAMESPACE, "blue_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        brownElectricLight = new ElectricLightFullBlock(NAMESPACE.id("brown_electric_light"), Material.GLASS, 0x473524).setTranslationKey(NAMESPACE, "brown_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        greenElectricLight = new ElectricLightFullBlock(NAMESPACE.id("green_electric_light"), Material.GLASS, 0x008000).setTranslationKey(NAMESPACE, "green_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        redElectricLight = new ElectricLightFullBlock(NAMESPACE.id("red_electric_light"), Material.GLASS, 0xCC2118).setTranslationKey(NAMESPACE, "red_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);
        blackElectricLight = new ElectricLightFullBlock(NAMESPACE.id("black_electric_light"), Material.GLASS, 0x141414).setTranslationKey(NAMESPACE, "black_electric_light").setHardness(0.2F).setSoundGroup(Block.GLASS_SOUND_GROUP);

        rubberCable = new RubberCableBlock(NAMESPACE.id("rubber_cable"), cableMaterial).setTranslationKey(NAMESPACE, "rubber_cable").setHardness(0.2F).setResistance(0.5F).setSoundGroup(Block.WOOL_SOUND_GROUP);
        goldenCable = new GoldenCableBlock(NAMESPACE.id("golden_cable"), cableMaterial).setTranslationKey(NAMESPACE, "golden_cable").setHardness(0.2F).setResistance(0.5F).setSoundGroup(Block.WOOL_SOUND_GROUP);
    }

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

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerScreenHandlers(GuiHandlerRegistryEvent event) {
        event.register(NAMESPACE.id("generator"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openGenerator, GeneratorBlockEntity::new));
        event.register(NAMESPACE.id("electric_furnace"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openElectricFurnace, ElectricFurnaceBlockEntity::new));
        event.register(NAMESPACE.id("macerator"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openMacerator, MaceratorBlockEntity::new));
        event.register(NAMESPACE.id("battery_box"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openBatteryBox, BatteryBoxBlockEntity::new));
        event.register(NAMESPACE.id("induction_furnace"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openInductionFurnace, InductionFurnaceBlockEntity::new));
    }

    @Environment(EnvType.CLIENT)
    private Screen openInductionFurnace(PlayerEntity player, Inventory inventory) {
        return new InductionFurnaceScreen(player, (InductionFurnaceBlockEntity) inventory);
    }

    @Environment(EnvType.CLIENT)
    private Screen openBatteryBox(PlayerEntity player, Inventory inventory) {
        return new BatteryBoxScreen(player, (BatteryBoxBlockEntity) inventory);
    }

    @Environment(EnvType.CLIENT)
    private Screen openMacerator(PlayerEntity player, Inventory inventory) {
        return new MaceratorScreen(player, (MaceratorBlockEntity) inventory);
    }

    @Environment(EnvType.CLIENT)
    private Screen openElectricFurnace(PlayerEntity player, Inventory inventory) {
        return new ElectricFurnaceScreen(player, (ElectricFurnaceBlockEntity) inventory);
    }

    @Environment(EnvType.CLIENT)
    private Screen openGenerator(PlayerEntity player, Inventory inventory) {
        return new GeneratorScreen(player, (GeneratorBlockEntity) inventory);
    }
}

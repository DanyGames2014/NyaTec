package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.block.entity.*;
import net.danygames2014.nyatec.screen.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

@Environment(EnvType.CLIENT)
public class ScreenHandlerListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void registerScreenHandlers(GuiHandlerRegistryEvent event) {
        event.register(NAMESPACE.id("generator"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openGenerator, GeneratorBlockEntity::new));
        event.register(NAMESPACE.id("electric_furnace"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openElectricFurnace, ElectricFurnaceBlockEntity::new));
        event.register(NAMESPACE.id("macerator"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openMacerator, MaceratorBlockEntity::new));
        event.register(NAMESPACE.id("battery_box"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openBatteryBox, BatteryBoxBlockEntity::new));
        event.register(NAMESPACE.id("induction_furnace"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openInductionFurnace, InductionFurnaceBlockEntity::new));
    }

    private Screen openInductionFurnace(PlayerEntity player, Inventory inventory) {
        return new InductionFurnaceScreen(player, (InductionFurnaceBlockEntity) inventory);
    }

    private Screen openBatteryBox(PlayerEntity player, Inventory inventory) {
        return new BatteryBoxScreen(player, (BatteryBoxBlockEntity) inventory);
    }

    private Screen openMacerator(PlayerEntity player, Inventory inventory) {
        return new MaceratorScreen(player, (MaceratorBlockEntity) inventory);
    }

    private Screen openElectricFurnace(PlayerEntity player, Inventory inventory) {
        return new ElectricFurnaceScreen(player, (ElectricFurnaceBlockEntity) inventory);
    }

    private Screen openGenerator(PlayerEntity player, Inventory inventory) {
        return new GeneratorScreen(player, (GeneratorBlockEntity) inventory);
    }
}

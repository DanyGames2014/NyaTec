package net.danygames2014.nyatec.screen.handler;

import net.danygames2014.nyatec.block.entity.BatteryBoxBlockEntity;
import net.danygames2014.nyatec.block.entity.GeneratorBlockEntity;
import net.danygames2014.nyatec.screen.slot.GeneratorFuelSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.Slot;

public class BatteryBoxScreenHandler extends ScreenHandler {
    public PlayerEntity player;
    public Inventory playerInventory;

    public BatteryBoxBlockEntity blockEntity;
    private int energy;

    public BatteryBoxScreenHandler(PlayerEntity player, BatteryBoxBlockEntity blockEntity) {
        this.player = player;
        this.blockEntity = blockEntity;

        this.playerInventory = player.inventory;

        int playerInventoryVerticalOffset = 168 / 2;
        int playerInventoryHorizontalOffset = 0;

        int row;
        int column;

        // Player Inventory
        for (row = 0; row < 3; row++) {
            for (column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory,
                                column + (row * 9) + 9,
                                playerInventoryHorizontalOffset + 8 + (column * 18),
                                playerInventoryVerticalOffset + (row * 18)
                        )
                );
            }
        }

        // Player Hotbar
        for (row = 0; row < 9; row++) {
            this.addSlot(new Slot(playerInventory,
                            row,
                            playerInventoryHorizontalOffset + 8 + (row * 18),
                            playerInventoryVerticalOffset + 58
                    )
            );
        }
    }

    @Environment(EnvType.SERVER)
    @Override
    public void addListener(ScreenHandlerListener listener) {
        super.addListener(listener);
        listener.onPropertyUpdate(this, 0, this.blockEntity.energy);
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        for (var listenerO : this.listeners) {
            if (listenerO instanceof ScreenHandlerListener listener) {
                if (this.energy != this.blockEntity.energy) {
                    this.energy = this.blockEntity.energy;
                    listener.onPropertyUpdate(this, 0, this.energy);
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setProperty(int id, int value) {
        switch (id) {
            case 0 -> {
                this.blockEntity.energy = value;
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}

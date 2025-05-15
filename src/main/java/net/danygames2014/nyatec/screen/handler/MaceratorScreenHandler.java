package net.danygames2014.nyatec.screen.handler;

import net.danygames2014.nyatec.block.entity.MaceratorBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.Slot;

public class MaceratorScreenHandler extends ScreenHandler {
    public PlayerEntity player;
    public Inventory playerInventory;
    
    public MaceratorBlockEntity macerator;
    private int energy;
    private int progress;
    
    public MaceratorScreenHandler(PlayerEntity player, MaceratorBlockEntity macerator) {
        this.player = player;
        this.macerator = macerator;
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

        // Input Slot
        this.addSlot(new Slot(macerator, 0, 56, 17));

        // Output Slot
        this.addSlot(new Slot(macerator, 1, 116, 35));
    }

    @Environment(EnvType.SERVER)
    @Override
    public void addListener(ScreenHandlerListener listener) {
        super.addListener(listener);
        listener.onPropertyUpdate(this, 0, this.macerator.energy);
        listener.onPropertyUpdate(this, 1, this.macerator.progress);
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        for (var listenerO : this.listeners) {
            if (listenerO instanceof ScreenHandlerListener listener) {
                if (this.energy != this.macerator.energy) {
                    this.energy = this.macerator.energy;
                    listener.onPropertyUpdate(this, 0, this.energy);
                }

                if (this.progress != this.macerator.progress) {
                    this.progress = this.macerator.progress;
                    listener.onPropertyUpdate(this, 1, this.progress);
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setProperty(int id, int value) {
        switch (id) {
            case 0 -> {
                this.macerator.energy = value;
            }
            case 1 -> {
                this.macerator.progress = value;
            }
        }
    }
    
    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}

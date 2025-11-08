package net.danygames2014.nyatec.screen.handler;

import net.danygames2014.nyatec.block.entity.InductionFurnaceBlockEntity;
import net.danygames2014.nyatec.block.entity.MaceratorBlockEntity;
import net.danygames2014.nyatec.block.entity.SlotType;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class InductionFurnaceScreenHandler extends ScreenHandler {
    public PlayerEntity player;
    public Inventory playerInventory;

    public InductionFurnaceBlockEntity furnace;
    private int energy;
    private int progress;

    public InductionFurnaceScreenHandler(PlayerEntity player, InductionFurnaceBlockEntity furnace) {
        this.player = player;
        this.furnace = furnace;
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
        this.addSlot(new Slot(furnace, furnace.getInputIndex(0), 47, 17));

        this.addSlot(new Slot(furnace, furnace.getInputIndex(1), 63, 17));

        // Output Slot
        this.addSlot(new Slot(furnace, furnace.getOutputIndex(RecipeOutputType.PRIMARY, 0), 116, 35));

        this.addSlot(new Slot(furnace, furnace.getOutputIndex(RecipeOutputType.SECONDARY, 0), 116, 60));

        // Fuel Slot
        this.addSlot(new Slot(furnace, furnace.getSlotInventoryIndex(SlotType.FUEL, 0), 56, 53));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}

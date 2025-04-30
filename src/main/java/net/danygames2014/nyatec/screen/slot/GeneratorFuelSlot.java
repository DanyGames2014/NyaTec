package net.danygames2014.nyatec.screen.slot;

import net.danygames2014.nyatec.util.FuelUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class GeneratorFuelSlot extends Slot {
    public GeneratorFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        int fuelTime = FuelUtil.getGeneratorFuelTime(stack);
        return fuelTime > 0;
    }
}

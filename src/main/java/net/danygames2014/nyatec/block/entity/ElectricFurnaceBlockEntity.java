package net.danygames2014.nyatec.block.entity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeManager;
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import static net.danygames2014.nyatec.recipe.output.RecipeOutputType.*;

public class ElectricFurnaceBlockEntity extends BaseMachineBlockEntity {
    public ElectricFurnaceBlockEntity() {
        super(200, 2);
        addInput();
        addOutput(PRIMARY);
        addSlot(SlotType.FUEL);
    }

    @Override
    public boolean canProcess() {
        if (getInput(0) == null) {
            return false;
        }

        ItemStack craftedItem = SmeltingRegistry.getResultFor(getInput(0));
        if (craftedItem == null) {
            return false;
        } else if (getOutput(PRIMARY, 0) == null) {
            return true;
        } else if (!getOutput(PRIMARY, 0).isItemEqual(craftedItem)) {
            return false;
        } else if (getOutput(PRIMARY, 0).count < this.getMaxCountPerStack() && getOutput(PRIMARY, 0).count < getOutput(PRIMARY, 0).getMaxCount()) {
            return true;
        } else {
            return getOutput(PRIMARY, 0).count < craftedItem.getMaxCount();
        }
    }

    @Override
    public void craftRecipe() {
        if (!canProcess()) {
            return;
        }

        ItemStack craftedItem = SmeltingRegistry.getResultFor(getInput(0));
        if (getOutput(PRIMARY, 0) == null) {
            setOutput(PRIMARY, 0, craftedItem.copy());
        } else if (getOutput(PRIMARY, 0).isItemEqual(craftedItem)) {
            getOutput(PRIMARY, 0).count++;
        }

        getInput(0).count--;
        if (getInput(0).count <= 0) {
            setInput(0, null);
        }
    }

    // Energy
    @Override
    public int getMaxInputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getMaxEnergyInput(@Nullable Direction direction) {
        return 10;
    }

    @Override
    public boolean canReceiveEnergy(@Nullable Direction direction) {
        return true;
    }

    @Override
    public void onOvervoltage(@Nullable Direction direction, double voltage) {

    }

    @Override
    public boolean canConnectEnergy(Direction direction) {
        return true;
    }

    @Override
    public int getEnergyCapacity() {
        return 100;
    }

    @Override
    public String getName() {
        return "Electric Furnace";
    }
}

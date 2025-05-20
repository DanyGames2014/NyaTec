package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class MaceratorBlockEntity extends BaseMachineBlockEntity {
    public int progress;
    public static final int MAX_PROGRESS = 100;
    public int processingSpeed = 2;
    public MaceratorRecipe currentRecipe = null;

    public MaceratorBlockEntity() {
        super(2);
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
            // Check if we can smelt
            if (canProcess()) {
                // Check if we have energy
                if (this.energy > 0) {
                    // If we have energy and we can cook, we cookin
                    progress += removeEnergy(processingSpeed);
                } else {
                    // If we don't have energy, slowly revert progress
                    progress -= 2;
                }
            } else {
                // We can't smelt, reset the progress
                progress = 0;
            }

            // Check the cook progress
            if (progress < 0) {
                // If it's less than zero, can it to zero
                progress = 0;
            } else if (progress >= MAX_PROGRESS) {
                // If we have reached the max cook time, craft the recipe
                progress = 0;
                craftRecipe();
            }

            // Update lit state
            if (progress <= 0 && world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, false));
            } else if (progress > 0 && !world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, true));
            }
        }
    }

    ItemStack lastInputStack = null;
    
    public boolean canProcess() {
        if (inventory[0] == null) {
            return false;
        }

        if(!lastInputStack.equals(inventory[0])) {
            currentRecipe = MaceratorRecipeRegistry.get(new ItemStack[]{this.inventory[0]});
            lastInputStack = inventory[0];
        }
        
        return currentRecipe != null;
    }
    
    public boolean canOutput() {
        return true;
    }

    public void craftRecipe() {
        if (canProcess() && canOutput()) {
            currentRecipe.consume(new ItemStack[]{inventory[0]});
            
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

    // Inventory
    @Override
    public String getName() {
        return "Macerator";
    }
}

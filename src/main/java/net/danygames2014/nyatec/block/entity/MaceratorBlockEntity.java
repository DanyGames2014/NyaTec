package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class MaceratorBlockEntity extends BaseMachineBlockEntity {
    public MaceratorRecipe currentRecipe = null;

    public MaceratorBlockEntity() {
        super(2, 100, 2);
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
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
        if (canProcess()) {
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

package net.danygames2014.nyatec.block.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.SmeltingRecipeManager;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class ElectricFurnaceBlockEntity extends BaseMachineBlockEntity {
    public int cookProgress;
    public static final int MAX_COOK_TIME = 200;
    public final int cookSpeed = 2;

    public ElectricFurnaceBlockEntity() {
        super(2);
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
            // Check if we can smelt
            if (canSmelt()) {
                // Check if we have energy
                if (this.energy > 0) {
                    // If we have energy and we can cook, we cookin
                    cookProgress += removeEnergy(cookSpeed);
                } else {
                    // If we don't have energy, slowly revert progress
                    cookProgress -= 2;
                }
            } else {
                // We can't smelt, reset the progress
                cookProgress = 0;
            }

            // Check the cook progress
            if (cookProgress < 0) {
                // If it's less than zero, can it to zero
                cookProgress = 0;
            } else if (cookProgress >= MAX_COOK_TIME) {
                // If we have reached the max cook time, craft the recipe
                cookProgress = 0;
                craftRecipe();
            }

            // Update lit state
            if (cookProgress <= 0 && world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, false));
            } else if (cookProgress > 0 && !world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, true));
            }
        }
    }

    public boolean canSmelt() {
        if (this.inventory[0] == null) {
            return false;
        } else {
            ItemStack craftedItem = SmeltingRecipeManager.getInstance().craft(this.inventory[0].getItem().id);
            if (craftedItem == null) {
                return false;
            } else if (this.inventory[1] == null) {
                return true;
            } else if (!this.inventory[1].isItemEqual(craftedItem)) {
                return false;
            } else if (this.inventory[1].count < this.getMaxCountPerStack() && this.inventory[1].count < this.inventory[1].getMaxCount()) {
                return true;
            } else {
                return this.inventory[1].count < craftedItem.getMaxCount();
            }
        }
    }

    public void craftRecipe() {
        if (this.canSmelt()) {
            ItemStack craftedItem = SmeltingRecipeManager.getInstance().craft(this.inventory[0].getItem().id);
            if (this.inventory[1] == null) {
                this.inventory[1] = craftedItem.copy();
            } else if (this.inventory[1].itemId == craftedItem.itemId) {
                ++this.inventory[1].count;
            }

            --this.inventory[0].count;
            if (this.inventory[0].count <= 0) {
                this.inventory[0] = null;
            }

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
    // 0 Input
    // 1 Output

    @Override
    public String getName() {
        return "Electric Furnace";
    }
}

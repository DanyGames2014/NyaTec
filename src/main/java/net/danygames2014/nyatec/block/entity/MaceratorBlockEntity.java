package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class MaceratorBlockEntity extends BaseMachineBlockEntity {
    public MaceratorRecipe currentRecipe = null;

    public MaceratorBlockEntity() {
        super(100, 2);
        this.addInput();
        this.addOutput(RecipeOutputType.PRIMARY);
        this.addOutput(RecipeOutputType.SECONDARY);
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
        if (getInput(0) == null) {
            return false;
        }

        if (lastInputStack != getInput(0) || !lastInputStack.equals(getInput(0))) {
            currentRecipe = MaceratorRecipeRegistry.get(new ItemStack[]{getInput(0)});
            lastInputStack = getInput(0);
        }

        if (currentRecipe == null) {
            return false;
        }

        return canOutput();
    }

    public boolean canOutput() {
        if (getOutput(RecipeOutputType.PRIMARY, 0) != null) {
            return false;
        }

        if (getOutput(RecipeOutputType.SECONDARY, 0) != null) {
            return false;
        }

        return true;
    }

    public void craftRecipe() {
        if (!canProcess()) {
            return;
        }

        currentRecipe.consume(new ItemStack[]{getInput(0)});

        if (getInput(0).count <= 0) {
            setInput(0, null);
        }

        for (ItemStack output : currentRecipe.getOutputs(random)) {
            if (output != null) {
                this.world.spawnEntity(new ItemEntity(world, this.x + 0.5, this.y + 1, this.z + 0.5, output));
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
    @Override
    public String getName() {
        return "Macerator";
    }
}

package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ForLoopReplaceableByForEach")
public class MaceratorBlockEntity extends BaseRecipeMachineBlockEntity {
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

    boolean outputAvalible = false;

    @Override
    public boolean canProcess() {
        // Check if a valid recipe exists. If not, we can't process
        if (!fetchRecipe()) {
            return false;
        }

        // If the output changed, recalculate the outputAvalible field
        if (outputChanged()) {
            outputAvalible = output(true);

            for (RecipeOutputType type : RecipeOutputType.values()) {
                var outIndexes = getOutputIndexes(type);
                ItemStack[] stacks = new ItemStack[outIndexes.length];
                for (int i = 0; i < outIndexes.length; i++) {
                    ItemStack stack = inventory[outIndexes[i]];
                    stacks[i] = stack != null ? stack.copy() : null;
                }
                lastOutputStacks.put(type, stacks);
            }
        }

        // Return the value showing if there is space to output the current recipe
        return outputAvalible;
    }

    /**
     * Fetches the current recipe according to the input
     *
     * @return <code>true</code> if a recipe was found or is already present, <code>false</code> if no recipe was found
     */
    public boolean fetchRecipe() {
        if (inputChanged()) {
            currentRecipe = MaceratorRecipeRegistry.get(getInputs());
            currentRecipeOutput = currentRecipe != null ? currentRecipe.getOutputs(random) : null;

            int[] inputIndexes = getInputIndexes();
            lastInputStacks = new ItemStack[inputIndexes.length];
            for (int i = 0; i < inputIndexes.length; i++) {
                ItemStack stack = inventory[inputIndexes[i]];
                lastInputStacks[i] = stack != null ? stack.copy() : null;
            }
        }

        return currentRecipe != null;
    }

    @Override
    public void craftRecipe() {
        if (!canProcess()) {
            return;
        }

        currentRecipe.consume(getInputs());

        int[] inputIndexes = getInputIndexes();
        for (int i = 0; i < inputIndexes.length; i++) {
            if (inventory[inputIndexes[i]] != null && inventory[inputIndexes[i]].count <= 0) {
                inventory[inputIndexes[i]] = null;
            }
        }

        output(false);
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

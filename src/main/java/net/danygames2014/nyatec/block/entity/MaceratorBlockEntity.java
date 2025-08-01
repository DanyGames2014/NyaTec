package net.danygames2014.nyatec.block.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.danygames2014.nyatec.recipe.MachineRecipe;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

@SuppressWarnings("ForLoopReplaceableByForEach")
public class MaceratorBlockEntity extends BaseMachineBlockEntity {
    // TODO: Move all of these to Base
    public MachineRecipe currentRecipe = null;
    public HashMap<RecipeOutputType, ObjectArrayList<ItemStack>> currentRecipeOutput = null;
    private ItemStack[] lastInputStacks = null;
    private final HashMap<RecipeOutputType, ItemStack[]> lastOutputStacks = new HashMap<>();

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

    public boolean outputChanged() {
        for (RecipeOutputType type : RecipeOutputType.values()) {
            int[] outIndexes = getOutputIndexes(type);
            ItemStack[] lastStacks = lastOutputStacks.get(type);

            // If lastStacks is null, that means that we haven't ran this check yet
            if (lastStacks == null) {
                return true;
            }

            // This could technically crash if the amount of output slots changes, but if that happens, I wanna know about it
            for (int i = 0; i < outIndexes.length; i++) {
                ItemStack currentStack = inventory[outIndexes[i]];
                ItemStack lastStack = lastStacks[i];

                // Check if one of the stacks is null
                if (currentStack == null || lastStack == null) {
                    if (currentStack == null && lastStack == null) {
                        // If they are both null, then there was no change
                        continue;
                    } else {
                        // If they are not both null, that's a change
                        return true;
                    }
                }

                // Otherwise if both stacks are not null, compare them
                if (!currentStack.equals(lastStack)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Fetches the current recipe according to the input
     *
     * @return <code>true</code> if a recipe was found or is already present, <code>false</code> if no recipe was found
     */
    public boolean fetchRecipe() {
        if (inputChanged()) {
            currentRecipe = MaceratorRecipeRegistry.get(new ItemStack[]{getInput(0)});
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

    public boolean inputChanged() {
        int[] inputIndexes = getInputIndexes();

        // If lastStacks is null, that means that we haven't ran this check yet
        if (lastInputStacks == null) {
            return true;
        }

        // This could technically crash if the amount of output slots changes, but if that happens, I wanna know about it
        for (int i = 0; i < inputIndexes.length; i++) {
            ItemStack currentStack = inventory[inputIndexes[i]];
            ItemStack lastStack = lastInputStacks[i];

            // Check if one of the stacks is null
            if (currentStack == null || lastStack == null) {
                if (currentStack == null && lastStack == null) {
                    // If they are both null, then there was no change
                    continue;
                } else {
                    // If they are not both null, that's a change
                    return true;
                }
            }

            // Otherwise if both stacks are not null, compare them
            if (!currentStack.equals(lastStack)) {
                return true;
            }
        }

        return false;
    }

    public boolean output(boolean simulate) {
        // Loop thru output types
        for (RecipeOutputType type : RecipeOutputType.values()) {
            // For each output type get a copy of the output slots
            ItemStack[] machineSlots = this.getOutputs(type, true);
            ObjectArrayList<ItemStack> currentRecipeTypeOutput;

            if (!currentRecipeOutput.containsKey(type)) {
                continue;
            }

            if (simulate) {
                currentRecipeTypeOutput = new ObjectArrayList<>();
                for (ItemStack item : currentRecipeOutput.get(type)) {
                    if (item != null) {
                        currentRecipeTypeOutput.add(item.copy());
                    } else {
                        currentRecipeTypeOutput.add(null);
                    }
                }
            } else {
                currentRecipeTypeOutput = currentRecipeOutput.get(type);
            }

            // For each stack that needs to be output into the output slots
            for (int j = 0; j < currentRecipeTypeOutput.size(); j++) {
                ItemStack outputStack = currentRecipeTypeOutput.get(j);

                //System.err.println(type + " - (" + j + ") " + outputStack);

                if (outputStack == null) {
                    continue;
                }

                // First try to find identical stack
                for (int i = 0; i < machineSlots.length; i++) {
                    if (outputStack != null && machineSlots[i] != null && outputStack.isItemEqual(machineSlots[i])) {
                        int movedCount = Math.min(machineSlots[i].getMaxCount() - machineSlots[i].count, outputStack.count);

                        machineSlots[i].count += movedCount;
                        outputStack.count -= movedCount;

                        if (outputStack.count <= 0) {
                            currentRecipeTypeOutput.set(j, null);
                            outputStack = null;
                        }
                    }
                }

                if (outputStack == null) {
                    continue;
                }

                // If no identical stack was found, try to find an empty slot
                for (int i = 0; i < machineSlots.length; i++) {
                    if (outputStack != null && machineSlots[i] == null) {
                        machineSlots[i] = outputStack.copy();

                        currentRecipeTypeOutput.set(j, null);
                        outputStack = null;
                    }
                }

                if (outputStack == null) {
                    continue;
                }

                // If finding empty slot was not succcesfull, return false
                return false;
            }

            if (!simulate) {
                setOutputs(type, machineSlots);
            }
        }

        return true;
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

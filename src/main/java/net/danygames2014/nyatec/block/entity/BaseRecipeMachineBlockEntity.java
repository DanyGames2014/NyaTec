package net.danygames2014.nyatec.block.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.nyatec.recipe.MachineRecipe;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public abstract class BaseRecipeMachineBlockEntity extends BaseMachineBlockEntity{
    /**
     * This is the current recipe being processed. <code>null</code> if no recipe is being processed
     */
    public MachineRecipe currentRecipe = null;
    
    /**
     * This is the output that the current recipe will yield
     * TODO: prevent recipe rerolling
     */
    public HashMap<RecipeOutputType, ObjectArrayList<ItemStack>> currentRecipeOutput = null;

    /**
     * The stacks against which the input is compared to determine if the input has changed
     */
    public ItemStack[] lastInputStacks = null;

    /**
     * The stacks against which the output is compared to determine if the output has changed
     */
    public final HashMap<RecipeOutputType, ItemStack[]> lastOutputStacks = new HashMap<>();
    
    public BaseRecipeMachineBlockEntity(int maxProgress, int processingSpeed) {
        super(maxProgress, processingSpeed);
    }

    /**
     * @return <code>true</code> if the current machine input is different than the lastInputStacks
     */
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

    /**
     * @return <code>true</code> if the current machine output is different than the lastOutputStacks
     */
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
     * Takes the current recipe output and tries to fit them into the machine output
     * 
     * @param simulate If <code>true</code>, the action will be simulated, but not executed
     * @return Whether the action was succesfull
     */
    public boolean output(boolean simulate) {
        // Loop thru output types
        for (RecipeOutputType type : RecipeOutputType.values()) {
            // For each output type get a copy of the output slots
            ItemStack[] machineSlots = this.getOutputs(type, true);
            ObjectArrayList<ItemStack> currentRecipeTypeOutput;

            if (!currentRecipeOutput.containsKey(type)) {
                continue;
            }

            // If we are only simulating, we will create a copy of the current recipe output items
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

                if (outputStack == null) {
                    continue;
                }

                // First try to find identical stack
                //noinspection ForLoopReplaceableByForEach
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

            // If we are not simulating, we set the machine output
            if (!simulate) {
                setOutputs(type, machineSlots);
            }
        }

        return true;
    }
}

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
    // TODO: Move this to Base
    public MachineRecipe currentRecipe = null;
    // TODO: Move this to Base
    public HashMap<RecipeOutputType, ObjectArrayList<ItemStack>> currentRecipeOutput = null;
    // TODO: Move this to Base
    ItemStack lastInputStack = null;

    public MaceratorBlockEntity() {
        super(100, 2);
        this.addInput();
        this.addOutput(RecipeOutputType.PRIMARY);
        this.addOutput(RecipeOutputType.SECONDARY);
    }

    @Override
    public void tick() {
        super.tick();

        // TODO: Make a template machine block with a property and add this to base
        if (!world.isRemote) {
            // Update lit state
            if (progress <= 0 && world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, false));
            } else if (progress > 0 && !world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, true));
            }
        }
    }

    @Override
    public boolean canProcess() {
        long nanoTime = System.nanoTime();
        
        if (!fetchRecipe()) {
            return false;
        }
        
        // TODO : outputChanged() method so this doesnt run that often
        boolean result = output(true);
        System.out.println("CANPROCESS CHECK TOOK " + (System.nanoTime() - nanoTime) / 1000 + "μs");
        return result;
    }

    /**
     * Fetches the current recipe according to the input
     *
     * @return <code>true</code> if a recipe was found or is already present, <code>false</code> if no recipe was found
     */
    public boolean fetchRecipe() {
        // TODO: Do not use hardcoded indexes, but try to figure out a performance efficient way to go about this
//        if (getInput(0) == null) {
//            currentRecipe = null;
//            currentRecipeOutput = null;
//            lastInputStack = null;
//            return false;
//        }

        if (inputChanged()) {
            System.err.println("CHANGED");
            currentRecipe = MaceratorRecipeRegistry.get(new ItemStack[]{getInput(0)});
            currentRecipeOutput = currentRecipe != null ? currentRecipe.getOutputs(random) : null;
            lastInputStack = getInput(0) != null ? getInput(0).copy() : null; 
        }

        return currentRecipe != null;
    }

    public boolean inputChanged() {
        // TODO: Do not use hardcoded indexes, use getInputIndexes()
        if (lastInputStack == null || getInput(0) == null) {
            return !(lastInputStack == null && getInput(0) == null);
        }

        return !lastInputStack.equals(getInput(0));
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
    
    // TODO: make a outputChanged() method that uses getOutputIndexes

    @Override
    public void craftRecipe() {
        long nanoTime = System.nanoTime();
        
        if (!canProcess()) {
            return;
        }

        // TODO: dont use hardcoded indexes
        currentRecipe.consume(new ItemStack[]{getInput(0)});

        // TODO: dont use hardcoded indexes
        if (getInput(0).count <= 0) {
            setInput(0, null);
        }

        output(false);

        System.out.println("CRAFT TOOK " + (System.nanoTime() - nanoTime) / 1000 + "μs");
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

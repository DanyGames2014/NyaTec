package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergyConsumerBlockEntityTemplate;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.HashMap;
import java.util.Random;

public abstract class BaseMachineBlockEntity extends EnergyConsumerBlockEntityTemplate implements Inventory {
    // Progress
    public int progress;
    public int maxProgress;

    // Properties
    public int processingSpeed;

    // Random
    public Random random;

    public BaseMachineBlockEntity(int inventorySize, int maxProgress, int processingSpeed) {
        // Progress
        this.progress = 0;
        this.maxProgress = maxProgress;

        // Properties
        this.processingSpeed = processingSpeed;

        // Random
        this.random = new Random();

        // Machine Inventory
        this.inventorySize = 0;
        this.inputs = new ItemStack[]{};
        this.outputs = new HashMap<>();
        for (var outputType : RecipeOutputType.values()) {
            this.outputs.put(outputType, new ItemStack[]{});
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
            // Check if we can process the current input
            if (canProcess()) {
                if (this.energy > 0) {
                    // If we can process and have the energy, process the recipe
                    progress += removeEnergy(processingSpeed);
                } else {
                    // If we can process but don't have the energy, slowly revert
                    progress -= 2;
                }
            } else {
                // If we can't process, revert progress to 0
                progress = 0;
            }

            if (progress < 0) {
                // If progress is less than zero, clamp it to zero
                progress = 0;
            } else if (progress >= maxProgress) {
                // If the progress has reached maximum, craft the recipe
                progress = 0;
                craftRecipe();
            }
        }
    }

    public abstract boolean canProcess();

    public abstract void craftRecipe();

    // Machine Inventory
    public int inventorySize;
    public ItemStack[] inputs;
    public HashMap<RecipeOutputType, ItemStack[]> outputs;

    public void addInput() {
        // Create a new array that is larger by one
        var newArray = new ItemStack[inputs.length + 1];

        // Copy values from old array into new one
        System.arraycopy(inputs, 0, newArray, 0, inputs.length);
        this.inputs = newArray;
        this.inventorySize++;
    }

    public void addOutput(RecipeOutputType type) {
        // Fetch the old array
        var oldArray = outputs.get(type);

        // Create a new array that is larger by one
        var newArray = new ItemStack[oldArray.length + 1];

        // Copy values from old array into new one
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        outputs.put(type, newArray);
        this.inventorySize++;
    }

    public ItemStack getInput(int index) {
        // If the index is too high, return null
        if (index >= inputs.length) {
            return null;
        }

        return inputs[index];
    }

    public ItemStack getOutput(RecipeOutputType type, int index) {
        ItemStack[] arr = outputs.get(type);

        // If the index is too high for this type, return null
        if (index >= arr.length) {
            return null;
        }

        return arr[index];
    }

    public void setInput(int index, ItemStack stack) {
        if (index >= inputs.length) {
            return;
        }

        inputs[index] = stack;
    }

    public void setOutput(RecipeOutputType type, int index, ItemStack stack) {
        ItemStack[] arr = outputs.get(type);

        // If the index is too high for this type, return null
        if (index >= arr.length) {
            return;
        }

        arr[index] = stack;
    }

    // Inventory
    @Override
    public int size() {
        return inventorySize;
    }

    @Override
    public ItemStack getStack(int slot) {
//        if (slot < 0 || slot >= inventory.length) {
//            return null;
//        }
//
//        return inventory[slot];
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
//        if (slot < 0 || slot >= inventory.length) {
//            return null;
//        }
//
//        ItemStack stack = null;
//
//        if (this.inventory[slot] != null) {
//            if (this.inventory[slot].count <= amount) {
//                stack = this.inventory[slot];
//                this.inventory[slot] = null;
//            } else {
//                stack = this.inventory[slot].split(amount);
//                if (this.inventory[slot].count == 0) {
//                    this.inventory[slot] = null;
//                }
//            }
//        }
//
//        return stack;
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
//        if (slot < 0 || slot >= inventory.length) {
//            return;
//        }
//
//        this.inventory[slot] = stack;
//        if (stack != null && stack.count > this.getMaxCountPerStack()) {
//            stack.count = this.getMaxCountPerStack();
//        }
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return !(player.getSquaredDistance(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) > 64.0D);
        }
    }

    // NBT
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        // Inventory
        // Input
        NbtList inputSlotsNbt = nbt.getList("InputSlots");

        for (int slot = 0; slot < this.inputs.length; slot++) {
            NbtCompound stackNbt = (NbtCompound) inputSlotsNbt.get(slot);
            byte slotIndex = stackNbt.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < this.inputs.length) {
                this.inputs[slotIndex] = new ItemStack(stackNbt);
            }
        }

        // Output
        NbtCompound outputSlotsNbt = nbt.getCompound("OutputSlots");

        for (var type : RecipeOutputType.values()) {
            if (!outputSlotsNbt.contains(type.name())) {
                continue;
            }

            ItemStack[] typeOutputs = this.outputs.get(type);
            NbtList typeOutputsNbt = outputSlotsNbt.getList(type.name());

            for (int slot = 0; slot < typeOutputs.length; slot++) {
                NbtCompound stackNbt = (NbtCompound) typeOutputsNbt.get(slot);
                byte slotIndex = stackNbt.getByte("Slot");
                if (slotIndex >= 0 && slotIndex < typeOutputs.length) {
                    typeOutputs[slotIndex] = new ItemStack(stackNbt);
                }
            }
        }

    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        // Inventory
        // Input
        NbtList inputSlotsNbt = new NbtList();

        for (int slot = 0; slot < this.inputs.length; slot++) {
            if (this.inputs[slot] == null) {
                continue;
            }

            NbtCompound stackNbt = new NbtCompound();
            stackNbt.putByte("Slot", (byte) slot);
            this.inputs[slot].writeNbt(stackNbt);
            inputSlotsNbt.add(stackNbt);
        }

        nbt.put("InputSlots", inputSlotsNbt);

        // Output
        NbtCompound outputSlotsNbt = new NbtCompound();

        for (var type : RecipeOutputType.values()) {
            if (!this.outputs.containsKey(type)) {
                continue;
            }

            ItemStack[] typeOutputs = this.outputs.get(type);
            NbtList typeOutputsNbt = new NbtList();

            for (int slot = 0; slot < typeOutputs.length; slot++) {
                if (typeOutputs[slot] == null) {
                    continue;
                }

                NbtCompound stackNbt = new NbtCompound();
                stackNbt.putByte("Slot", (byte) slot);
                typeOutputs[slot].writeNbt(stackNbt);
                typeOutputsNbt.add(stackNbt);
            }

            outputSlotsNbt.put(type.name(), typeOutputsNbt);
        }

        nbt.put("OutputSlots", outputSlotsNbt);
    }
}

package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergyConsumerBlockEntityTemplate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

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
        this.inventory = new ItemStack[inventorySize];

        // Progress
        this.progress = 0;
        this.maxProgress = maxProgress;
        
        // Properties
        this.processingSpeed = processingSpeed;
        
        // Random
        this.random = new Random();
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

    // Inventory
    public ItemStack[] inventory;

    @Override
    public int size() {
        return inventory.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot < 0 || slot >= inventory.length) {
            return null;
        }

        return inventory[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (slot < 0 || slot >= inventory.length) {
            return null;
        }

        ItemStack stack = null;

        if (this.inventory[slot] != null) {
            if (this.inventory[slot].count <= amount) {
                stack = this.inventory[slot];
                this.inventory[slot] = null;
            } else {
                stack = this.inventory[slot].split(amount);
                if (this.inventory[slot].count == 0) {
                    this.inventory[slot] = null;
                }
            }
        }

        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot < 0 || slot >= inventory.length) {
            return;
        }

        this.inventory[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }
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
        NbtList itemsNbt = nbt.getList("Items");
        this.inventory = new ItemStack[this.size()];

        for (int slot = 0; slot < itemsNbt.size(); ++slot) {
            NbtCompound stackNbt = (NbtCompound) itemsNbt.get(slot);
            byte slotIndex = stackNbt.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < this.inventory.length) {
                this.inventory[slotIndex] = new ItemStack(stackNbt);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        // Inventory
        NbtList itemsNbt = new NbtList();

        for (int slot = 0; slot < this.inventory.length; ++slot) {
            if (this.inventory[slot] != null) {
                NbtCompound stackNbt = new NbtCompound();
                stackNbt.putByte("Slot", (byte) slot);
                this.inventory[slot].writeNbt(stackNbt);
                itemsNbt.add(stackNbt);
            }
        }

        nbt.put("Items", itemsNbt);
    }
}

package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergySourceBlockEntityTemplate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlockEntity extends EnergySourceBlockEntityTemplate implements Inventory {
    public GeneratorBlockEntity() {
        this.stack = null;
    }

    @Override
    public int getMaxOutputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getOutputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getMaxEnergyOutput(@Nullable Direction direction) {
        return 1760;
    }

    @Override
    public boolean canExtractEnergy(@Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean canConnectEnergy(Direction direction) {
        return true;
    }

    @Override
    public int getEnergyCapacity() {
        return 1100;
    }

    // Inventory
    ItemStack stack;

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot == 0) {
            return stack;
        }
        return null;
    }

    public ItemStack removeStack(int slot, int amount) {
        if (stack != null) {
            ItemStack itemStack;
            
            if (stack.count <= amount) {
                itemStack = stack.copy();
                stack = null;
            } else {
                itemStack = stack.split(amount);
                if (stack.count == 0) {
                    stack = null;
                }

            }
            return itemStack;
        }
        return null;
    }

    public void setStack(int slot, ItemStack stack) {
        this.stack = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }

    }

    @Override
    public String getName() {
        return "Generator";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}

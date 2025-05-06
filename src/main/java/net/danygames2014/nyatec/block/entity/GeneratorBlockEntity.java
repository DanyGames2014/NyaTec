package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.util.FuelUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlockEntity extends BaseGeneratorBlockEntity implements Inventory {
    public GeneratorBlockEntity() {
        super(NyaTec.MACHINE_CONFIG.generator.fuelConsumptionRate, NyaTec.MACHINE_CONFIG.generator.energyPerFuelTick, NyaTec.MACHINE_CONFIG.generator.fuelBuffer);
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
        return 200;
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
        return 1000;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.fuel < this.fuelBuffer) {
            if (stack != null && stack.count > 0) {
                if (addFuel(FuelUtil.getGeneratorFuelTime(stack))) {
                    stack.count--;
                    if (stack.count == 0) {
                        stack = null;
                    }
                    markDirty();
                }
            }
        }

        if (this.state == GeneratorState.NOT_RUNNING && world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
            world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, false));
        } else if ((this.state == GeneratorState.IDLING || this.state == GeneratorState.RUNNING) && !world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
            world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, true));
        }
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

    // NBT
    @Override
    public void writeNbt(NbtCompound nbt) {
        if (stack != null) {
            nbt.put("Item", stack.writeNbt(new NbtCompound()));
        }
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("Item")) {
            stack = new ItemStack(nbt.getCompound("Item"));
        }
        super.readNbt(nbt);
    }
}

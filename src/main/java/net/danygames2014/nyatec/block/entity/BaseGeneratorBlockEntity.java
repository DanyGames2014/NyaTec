package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergySourceBlockEntityTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public abstract class BaseGeneratorBlockEntity extends EnergySourceBlockEntityTemplate {
    // Generator State
    public boolean operating = false;
    public int currentRate = 0;
    public int fuel = 0;

    // Generator Properties
    public final int fuelConsumptionRate;
    public final int energyPerFuel;

    public BaseGeneratorBlockEntity(int fuelConsumptionRate, int energyPerFuel) {
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.energyPerFuel = energyPerFuel;
    }

    @Override
    public abstract int getMaxOutputVoltage(@Nullable Direction direction);

    @Override
    public abstract int getOutputVoltage(@Nullable Direction direction);

    @Override
    public abstract int getMaxEnergyOutput(@Nullable Direction direction);

    @Override
    public abstract boolean canExtractEnergy(@Nullable Direction direction);

    @Override
    public abstract boolean canConnectEnergy(Direction direction);

    @Override
    public abstract int getEnergyCapacity();

    @Override
    public void tick() {
        super.tick();

        operating = false;
        currentRate = 0;

        // Check if we even have fuel
        if (fuel > 0) {
            operating = true;
            if (this.getRemainingCapacity() > 0) {
                // If the buffer is not full, operate in "active" mode
                int consumedFuel = Math.min(fuel, fuelConsumptionRate);
                fuel -= consumedFuel;
                currentRate = addEnergy(consumedFuel * energyPerFuel);
            } else {
                // If the buffer is full, operate in "idle" mode
                fuel -= Math.min(fuel, 1);
            }
        }

        if (fuel < 0) {
            fuel = 0;
            NyaTec.LOGGER.warn("Generator fuel was below zero. x:" + this.x + " y:" + this.y + " z:" + this.z);
        }
    }

    // NBT
    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("fuel", fuel);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        fuel = nbt.getInt("fuel");
        super.readNbt(nbt);
    }
}

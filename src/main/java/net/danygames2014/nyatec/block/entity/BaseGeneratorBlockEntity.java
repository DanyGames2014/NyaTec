package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergySourceBlockEntityTemplate;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public abstract class BaseGeneratorBlockEntity extends EnergySourceBlockEntityTemplate {
    // Generator State
    public GeneratorState state = GeneratorState.NOT_RUNNING;
    public int currentRate = 0;
    public int fuel = 0;

    // Generator Properties
    public final int fuelConsumptionRate;
    public final double energyPerFuelTick;
    public final int maxFuel;

    public BaseGeneratorBlockEntity(int fuelConsumptionRate, double energyPerFuelTick, int maxFuel) {
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.energyPerFuelTick = energyPerFuelTick;
        this.maxFuel = maxFuel;
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

        state = GeneratorState.NOT_RUNNING;
        currentRate = 0;

        // Check if we even have fuel
        if (fuel > 0) {
            if (this.getRemainingCapacity() > 0) {
                // If the buffer is not full, operate in "active" mode
                int consumedFuel = Math.min(fuel, fuelConsumptionRate);
                fuel -= consumedFuel;
                currentRate = addEnergy((int) (consumedFuel * energyPerFuelTick));
                state = GeneratorState.RUNNING;
            } else {
                // If the buffer is full, operate in "idle" mode
                fuel -= Math.min(fuel, 1);
                state = GeneratorState.IDLING;
            }
        }

        if (fuel < 0) {
            fuel = 0;
            NyaTec.LOGGER.warn("Generator fuel was below zero. x:" + this.x + " y:" + this.y + " z:" + this.z);
        }
    }

    public boolean addFuel(int fuel) {
        if (this.fuel + fuel > maxFuel) {
            // Allow overflowing when the fuel is below 100 fuel ticks 
            if (this.fuel < 100) {
                this.fuel += fuel;
                return true;
            }
            
            return false;
        }

        this.fuel += fuel;
        return true;
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
    
    public enum GeneratorState {
        /**
         * The generator is completely off
         */
        NOT_RUNNING,

        /**
         * The generator's energy buffer is full but it is still burning fuel 
         */
        IDLING,

        /**
         * The generator is running and producing power
         */
        RUNNING
    }
}

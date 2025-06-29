package net.danygames2014.nyatec.block.entity.template;

import it.unimi.dsi.fastutil.objects.ObjectObjectMutablePair;
import net.danygames2014.nyalib.energy.EnergyConsumer;
import net.danygames2014.nyalib.energy.EnergySource;
import net.danygames2014.nyalib.network.Network;
import net.danygames2014.nyalib.network.energy.EnergyNetwork;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class EnergySourceConsumerBlockEntityTemplate extends BlockEntity implements EnergySource, EnergyConsumer {
    public int energy;

    private final HashMap<EnergyNetwork, Direction> energyNets = new HashMap<>(2);

    // TODO: Solve this without queues
    private final ArrayList<EnergyNetwork> energyNetRemoveQueue = new ArrayList<>(2);
    private final ArrayList<ObjectObjectMutablePair<EnergyNetwork, Direction>> energyNetAddQueue = new ArrayList<>(2);

    @Override
    public void tick() {
        // We are using a push system, so the machine is responsible for sending the energy

        // Reset the energy counters
        extracted = 0;
        consumed = 0;

        // First check if we have anything to actually send
        if (energy > 0) {
            // If we do, first try to send to adjacent energy consumers
            for (Direction side : Direction.values()) {
                if (!canExtractEnergy(side)) {
                    continue;
                }

                if (world.getBlockEntity(x + side.getOffsetX(), y + side.getOffsetY(), z + side.getOffsetZ()) instanceof EnergyConsumer consumer) {
                    int usedPower = consumer.receiveEnergy(side.getOpposite(), getOutputVoltage(side), Math.min(energy, getMaxEnergyOutput(side) - extracted));
                    removeEnergy(usedPower);
                    extracted += usedPower;
                }
            }

            // After that, try to send energy to the energy networks this source is part of
            for (Map.Entry<EnergyNetwork, Direction> energyNet : energyNets.entrySet()) {
                if (!canExtractEnergy(energyNet.getValue())) {
                    continue;
                }
                
                int usedPower = energyNet.getKey().provideEnergy(this, new Vec3i(this.x, this.y, this.z), getOutputVoltage(energyNet.getValue()), Math.min(energy, getMaxEnergyOutput(null) - extracted));
                removeEnergy(usedPower);
                extracted += usedPower;
            }
        }

        // Remove queued energy nets
        for (EnergyNetwork energyNet : energyNetRemoveQueue) {
            energyNets.remove(energyNet);
        }
        energyNetRemoveQueue.clear();

        // Add queued energy nets
        for (ObjectObjectMutablePair<EnergyNetwork, Direction> entry : energyNetAddQueue) {
            energyNets.put(entry.left(), entry.right());
        }
        energyNetAddQueue.clear();
    }

    public void addedToNet(World world, int x, int y, int z, Network network) {
        if (network instanceof EnergyNetwork energyNet) {
            if (!energyNets.containsKey(energyNet)) {
                energyNetAddQueue.add(new ObjectObjectMutablePair<>(energyNet, getNetSide(x, y, z, network)));
            }
        }
    }

    public void removedFromNet(World world, int x, int y, int z, Network network) {
        if (network instanceof EnergyNetwork energyNet) {
            energyNetRemoveQueue.add(energyNet);
        }
    }

    public void update(World world, int x, int y, int z, Network network) {
        if (network instanceof EnergyNetwork energyNet) {
            if (!energyNets.containsKey(energyNet)) {
                energyNetAddQueue.add(new ObjectObjectMutablePair<>(energyNet, getNetSide(x, y, z, network)));
            }
        }
    }

    public @Nullable Direction getNetSide(int x, int y, int z, Network network) {
        for (Direction side : Direction.values()) {
            if (network.isAt(x + side.getOffsetX(), y + side.getOffsetY(), z + side.getOffsetZ())) {
                return side;
            }
        }
        return null;
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
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public abstract int getEnergyCapacity();

    @Override
    public int setEnergy(int value) {
        this.energy = value;

        if (energy > getEnergyCapacity()) {
            energy = getEnergyCapacity();
        }

        return energy;
    }

    public int extracted = 0;

    @Override
    public int extractEnergy(@Nullable Direction direction, int requestedEnergy) {
        // If energy cannot be extracted on this side, return zero
        if (!canExtractEnergy(direction)) {
            return 0;
        }

        // If there is no energy, skip the calculations
        if (getEnergyStored() <= 0) {
            return 0;
        }

        // Cap the requested energy at the max this machine will be able to provide
        requestedEnergy = Math.min(requestedEnergy, getMaxEnergyOutput(direction) - extracted);

        // If no or negative energy is requested, return zero
        if (requestedEnergy <= 0) {
            return 0;
        }

        // Return the extracted energy
        int extractedEnergy = removeEnergy(requestedEnergy);

        // Increment the extracted value
        extracted += extractedEnergy;

        // Return the extracted energy
        return extractedEnergy;
    }

    public int consumed = 0;

    @Override
    public int receiveEnergy(@Nullable Direction direction, int voltage, int energy) {
        // If we cannot receive energy in this direction, dont care, return zero
        if (!canReceiveEnergy(direction)) {
            return 0;
        }

        // Cap the energy on the maximum this machine will be able to receive
        energy = Math.min(energy, getMaxEnergyInput(direction) - consumed);

        // If the received power is zero or negative, return zero
        if (energy <= 0) {
            return 0;
        }

        // If we wouldn't be able to store any power anyway, dont bother calculating and return zero
        if (getRemainingCapacity() <= 0) {
            return 0;
        }

        // Check if the voltage is higher than the maximum input voltage
        if (voltage > getMaxInputVoltage(direction)) {
            // If the voltage is higher, trigger an overvoltage event and return zero
            this.onOvervoltage(direction, voltage);
            return 0;
        }

        // Return the used power
        int consumedPower = addEnergy(Math.min(energy, getMaxEnergyInput(direction)));

        // Increase the consumed last tick value
        consumed += consumedPower;

        // Return the maount of power used
        return consumedPower;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("energy", energy);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        energy = nbt.getInt("energy");
    }
}

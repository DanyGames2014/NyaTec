package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergySourceBlockEntityTemplate;
import net.danygames2014.nyatec.block.CreativeEnergySourceBlock;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergySourceBlockEntity extends EnergySourceBlockEntityTemplate {
    public boolean enabled = true;
    public boolean inverted = true;
    
    @Override
    public void tick() {
        super.tick();
        enabled = world.isPowered(x, y, z);
        
        if (enabled && inverted) {
            enabled = false;
        } else if (!enabled && inverted) {
            enabled = true;
        }
        
        if (enabled) {
            this.energy = this.getEnergyCapacity();
        } else {
            this.energy = 0;
        }
        
        if (enabled && !world.getBlockState(x,y,z).get(CreativeEnergySourceBlock.ENABLED)) {
            world.setBlockStateWithNotify(x,y,z, world.getBlockState(x,y,z).with(CreativeEnergySourceBlock.ENABLED, true));
        } else if (!enabled && world.getBlockState(x,y,z).get(CreativeEnergySourceBlock.ENABLED)) {
            world.setBlockStateWithNotify(x,y,z, world.getBlockState(x,y,z).with(CreativeEnergySourceBlock.ENABLED, false));
        }
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
        return 10000;
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
        return 100000;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("enabled", enabled);
        nbt.putBoolean("inverted", inverted);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        enabled = nbt.getBoolean("enabled");
        inverted = nbt.getBoolean("inverted");
    }
}

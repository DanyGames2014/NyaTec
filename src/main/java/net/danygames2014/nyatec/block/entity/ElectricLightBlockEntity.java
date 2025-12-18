package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergyConsumerBlockEntityTemplate;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyatec.block.ElectricLightBlock;
import net.danygames2014.nyatec.block.LightLevel;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class ElectricLightBlockEntity extends EnergyConsumerBlockEntityTemplate {
    public boolean enabled = true;
    public int lastLightLevel = -1;
    public float luminance = 0;

    @Override
    public void tick() {
        super.tick();

        if (enabled) {
            if (energy > 0) {
                updateLightLevel(removeEnergy(3) * 5);
            } else {
                updateLightLevel(0);
            }
        }
    }

    public void updateLightLevel(int lightLevel) {
        if (lightLevel != lastLightLevel) {
            luminance = lightLevel * 0.06666667F;
            world.setBlockStateWithNotify(x,y,z, world.getBlockState(x,y,z).with(ElectricLightBlock.LIGHT_LEVEL, LightLevel.fromLevel(lightLevel)));
            lastLightLevel = lightLevel;
        }
    }

    @Override
    public int getMaxInputVoltage(@Nullable Direction direction) {
        return 240;
    }

    @Override
    public int getMaxEnergyInput(@Nullable Direction direction) {
        return 4;
    }

    @Override
    public boolean canReceiveEnergy(@Nullable Direction direction) {
        return true;
    }

    @Override
    public void onOvervoltage(@Nullable Direction direction, double voltage) {
        for (int particle = 0; particle < 4; particle++) {
            ParticleHelper.addParticle(world, "smoke", this.x + 0.5D + (world.random.nextDouble() - 0.5D), this.y + 0.5D, this.z + 0.5D + (world.random.nextDouble() - 0.5D));
            world.setBlockStateWithNotify(this.x, this.y, this.z, States.AIR.get());
        }
    }

    @Override
    public boolean canConnectEnergy(Direction direction) {
        return true;
    }

    @Override
    public int getEnergyCapacity() {
        return 10;
    }
}

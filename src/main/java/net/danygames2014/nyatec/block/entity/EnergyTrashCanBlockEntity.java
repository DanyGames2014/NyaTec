package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergyConsumerBlockEntityTemplate;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class EnergyTrashCanBlockEntity extends EnergyConsumerBlockEntityTemplate {
    boolean fastMode = false;

    @Override
    public int getMaxInputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getMaxEnergyInput(@Nullable Direction direction) {
        if (powered) {
            return 0;
        }

        return fastMode ? 10000 : 10;
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
        return Integer.MAX_VALUE;
    }

    boolean powered;

    @Override
    public void tick() {
        super.tick();

        powered = false;
        for (Direction side : Direction.values()) {
            if (world.isPowered(x + side.getOffsetX(), y + side.getOffsetY(), z + side.getOffsetZ())) {
                powered = true;
            }
        }

        setEnergy(0);
    }

    public void switchMode() {
        fastMode = !fastMode;
    }

    public boolean isFastMode() {
        return fastMode;
    }
}

package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.block.entity.template.EnergySourceConsumerBlockEntityTemplate;

public abstract class BaseEnergyStorageBlockEntity extends EnergySourceConsumerBlockEntityTemplate {
    int size;

    public BaseEnergyStorageBlockEntity(int size) {
        super();
        this.size = size;
    }

    @Override
    public int getEnergyCapacity() {
        return size;
    }
}

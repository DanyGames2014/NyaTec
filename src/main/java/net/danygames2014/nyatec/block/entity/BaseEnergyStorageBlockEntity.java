package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.block.entity.template.EnergySourceConsumerBlockEntityTemplate;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import org.jetbrains.annotations.NotNull;

public abstract class BaseEnergyStorageBlockEntity extends EnergySourceConsumerBlockEntityTemplate implements CustomTooltipProvider {
    int size;

    public BaseEnergyStorageBlockEntity(int size) {
        super();
        this.size = size;
    }

    @Override
    public int getEnergyCapacity() {
        return size;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[]{
                originalTooltip,
                "Maximum Voltage Input: " + getMaxInputVoltage(null) + " EU",
                "Maximum Energy Input: " + getMaxEnergyInput(null) + " EU/t",
                "Maximum Voltage Output: " + getMaxOutputVoltage(null) + " EU",
                "Maximum Energy Output: " + getMaxEnergyOutput(null) + " EU/t"
        };
    }
}

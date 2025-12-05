package net.danygames2014.nyatec.compat.ami.generator;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.util.FuelUtil;
import net.minecraft.item.ItemStack;

public class GeneratorFuelEntry {
    public final ItemStack fuel;
    public final int burnTime;
    public final int energyValue;

    public GeneratorFuelEntry(ItemStack fuel) {
        this.fuel = fuel;
        this.burnTime = FuelUtil.getGeneratorFuelTime(fuel);
        this.energyValue = (int) (this.burnTime * NyaTec.MACHINE_CONFIG.generator.energyPerFuelTick);
    }
    
    public boolean isValid() {
        return burnTime > 0;
    }
}

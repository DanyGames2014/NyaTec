package net.danygames2014.nyatec.block.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class BatteryBoxBlockEntity extends BaseEnergyStorageBlockEntity implements Inventory {
    public BatteryBoxBlockEntity() {
        super(10000);
    }

    @Override
    public int getMaxOutputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getOutputVoltage(@Nullable Direction direction) {
        return getEnergyStored() > 0 ? getMaxOutputVoltage(direction) : 0;
    }

    @Override
    public int getMaxEnergyOutput(@Nullable Direction direction) {
        return 880;
    }

    @Override
    public boolean canExtractEnergy(@Nullable Direction direction) {
        return this.world.getBlockState(this.x, this.y, this.z).get(Properties.FACING).equals(direction);
    }

    @Override
    public boolean canConnectEnergy(Direction direction) {
        return true;
    }

    @Override
    public int getMaxInputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getMaxEnergyInput(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public boolean canReceiveEnergy(@Nullable Direction direction) {
        return !this.world.getBlockState(this.x, this.y, this.z).get(Properties.FACING).equals(direction);
    }

    @Override
    public void onOvervoltage(@Nullable Direction direction, double voltage) {
        
    }

    // Inventory
    @Override
    public int size() {
        return 0;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public String getName() {
        return "Battery Box";
    }

    @Override
    public int getMaxCountPerStack() {
        return 0;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}

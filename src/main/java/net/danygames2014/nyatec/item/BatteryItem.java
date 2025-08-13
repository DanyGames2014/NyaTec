package net.danygames2014.nyatec.item;

import net.danygames2014.nyalib.energy.EnergyStorageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class BatteryItem extends TemplateItem implements EnergyStorageItem, CustomTooltipProvider {
    private final int capacity;
    private final float multiplier;

    public BatteryItem(Identifier identifier, int capacity) {
        super(identifier);
        this.capacity = capacity;
        this.setMaxDamage(100);
        this.setMaxCount(16);
        this.multiplier = (1.0F / capacity) * getMaxDamage();
    }

    public int getScaledEnergy(ItemStack stack) {
        return (int) ((float) getRemainingCapacity(stack) * multiplier);
    }

    @Override
    public int getEnergyStored(ItemStack stack) {
        return stack.getStationNbt().getInt("energy");
    }

    @Override
    public int getEnergyCapacity(ItemStack stack) {
        return capacity;
    }

    @Override
    public int addEnergy(ItemStack stack, int amount) {
        if (stack.count > 1) {
            return 0;
        }
        return EnergyStorageItem.super.addEnergy(stack, amount);
    }

    @Override
    public int setEnergy(ItemStack stack, int value) {
        stack.getStationNbt().putInt("energy", value);
        stack.setDamage(getScaledEnergy(stack));
        return value;
    }

    @Override
    public boolean canReceiveEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxEnergyInput(ItemStack stack) {
        return 5;
    }

    @Override
    public boolean canExtractEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxEnergyOutput(ItemStack stack) {
        return 10;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        addEnergy(stack, 50);

        return super.use(stack, world, user);
    }


    @Override
    public @NotNull String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[]{
                originalTooltip,
                "Energy: " + getEnergyStored(stack) + "/" + getEnergyCapacity(stack) + " EU",
        };
    }
}

package net.danygames2014.nyatec.item;

import net.minecraft.item.ItemStack;

// TODO: Turn this into a registry with event
public interface SingleUseEnergyItem {
    int getEnergyValue(ItemStack stack);
}

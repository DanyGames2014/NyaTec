package net.danygames2014.nyatec.util;

import com.google.common.collect.ImmutableList;
import net.danygames2014.nyalib.fluid.Fluid;
import net.danygames2014.nyalib.fluid.Fluids;
import net.danygames2014.nyatec.compat.ami.generator.GeneratorFuelEntry;
import net.glasslauncher.mods.alwaysmoreitems.api.AMIHelpers;
import net.glasslauncher.mods.alwaysmoreitems.api.ItemRegistry;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;

import java.util.ArrayList;
import java.util.HashMap;

public class FuelUtil {
    public static int getGeneratorFuelTime(ItemStack stack) {
        if (stack.getItem() != null && !(stack.getItem() instanceof BucketItem)) {
            return FuelRegistry.getFuelTime(stack);
        }
        return 0;
    }

    public static HashMap<Fluid, Integer> FLUID_FUEL_TIME = new HashMap<>() {{
        put(Fluids.LAVA, 10);
    }};

    public static int getGeneratorFuelTime(Fluid fluid) {
        return FLUID_FUEL_TIME.getOrDefault(fluid, 0);
    }
    
    public static ArrayList<GeneratorFuelEntry> getGeneratorFuelEntries(ItemRegistry itemRegistry, AMIHelpers helpers) {
        ImmutableList<ItemStack> stacks = itemRegistry.getItemList();
        ArrayList<GeneratorFuelEntry> entries = new ArrayList<>();
        
        for (ItemStack stack : stacks) {
            if (getGeneratorFuelTime(stack) > 0) {
                entries.add(new GeneratorFuelEntry(stack));
            }
        }
        
        return entries;
    }
}

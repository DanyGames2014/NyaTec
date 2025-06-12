package net.danygames2014.nyatec.block.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class MachineInventory {
    private Int2ObjectMap<ItemStack> input = new Int2ObjectOpenHashMap<>();
    private Int2ObjectMap<ItemStack> output = new Int2ObjectOpenHashMap<>();
    
    public MachineInventory() {
        
    }
    
    public void addInput() {
        
    }
    
    public void addOutput() {
        
    }
    
    public record OutputSlot(ItemStack stack, RecipeOutputType type) {
        
    }
}

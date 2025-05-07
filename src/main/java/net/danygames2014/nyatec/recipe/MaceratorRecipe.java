package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.ItemStack;

public class MaceratorRecipe {
    public ObjectArrayList<MaceratorRecipeOutput> outputs = new ObjectArrayList<>();
    public int recipeTime;
    
    public record MaceratorRecipeOutput(ItemStack stack, int chance) {
        
    }
}

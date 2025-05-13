package net.danygames2014.nyatec.recipe.input;

import net.minecraft.item.ItemStack;

/**
 * Recipe Input which compares to a stack using the ItemStack.equals method
 */
public class StackRecipeInput extends RecipeInput{
    public final ItemStack stack;
    
    public StackRecipeInput(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean matches(ItemStack other) {
        return other.equals(stack);
    }

    @Override
    public String toString() {
        return "StackRecipeInput{" +
                "stack=" + stack +
                '}';
    }
}

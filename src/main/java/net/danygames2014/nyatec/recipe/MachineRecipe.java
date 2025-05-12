package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.nyatec.recipe.input.RecipeInput;
import net.danygames2014.nyatec.recipe.output.RangeRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;
import net.minecraft.item.ItemStack;

public class MachineRecipe {
    public ObjectArrayList<RecipeInput> inputs;
    public ObjectArrayList<RecipeOutput> outputs;
    public int recipeTime;

    public MachineRecipe(int recipeTime) {
        this.recipeTime = recipeTime;
        this.inputs = new ObjectArrayList<>();
        this.outputs = new ObjectArrayList<>();
    }

    public MachineRecipe() {
        this(100);
    }

    public boolean matches(ItemStack[] inputs) {
        // Loop over the required ingredients
        for (RecipeInput input : this.inputs) {
            // Check all the provided ingredients
            boolean satisfied = false;
            for (ItemStack inputStack : inputs) {
                if(inputStack == null) continue;
                
                if(input.matches(inputStack)) {
                    satisfied = true;
                    break;
                }
            }

            // If not satisfied, return false
            if (!satisfied) {
                return false;
            }
        }

        return true;
    }
}

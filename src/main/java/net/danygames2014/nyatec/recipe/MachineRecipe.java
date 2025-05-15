package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.recipe.input.RecipeInput;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;
import net.minecraft.item.ItemStack;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class MachineRecipe {
    public final ObjectArrayList<RecipeInput> inputs;
    public final ObjectArrayList<RecipeOutput> outputs;
    public final int recipeTime;

    public MachineRecipe(int recipeTime) {
        this.recipeTime = recipeTime;
        this.inputs = new ObjectArrayList<>();
        this.outputs = new ObjectArrayList<>();
    }

    public MachineRecipe() {
        this(100);
    }

    public MachineRecipe addInput(RecipeInput input) {
        for (RecipeInput i : inputs) {
            if (i.equals(input)) {
                NyaTec.LOGGER.warn("Input already exists in recipe " + input);
                return this;
            }
        }
        
        inputs.add(input);
        return this;
    }

    public MachineRecipe addOutput(RecipeOutput output) {
        for (RecipeOutput i : outputs) {
            if (i.equals(output)) {
                NyaTec.LOGGER.warn("Output already exists in recipe " + output);
                return this;
            }
        }
        
        outputs.add(output);
        return this;
    }

    public boolean matches(ItemStack[] inputs) {
        // This is used to keep track of whether an ingredient has already been "consumed"
        boolean[] used = new boolean[inputs.length];

        if(this.inputs.isEmpty()) {
            return false;
        }
        
        // Loop over the required ingredients
        for (RecipeInput input : this.inputs) {
            // Check all the provided ingredients
            boolean satisfied = false;

            for (int i = 0; i < inputs.length; i++) {
                if (used[i]) {
                    continue;
                }

                ItemStack inputStack = inputs[i];
                if (inputStack == null) {
                    continue;
                }

                if (input.matches(inputStack)) {
                    satisfied = true;
                    used[i] = true;
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

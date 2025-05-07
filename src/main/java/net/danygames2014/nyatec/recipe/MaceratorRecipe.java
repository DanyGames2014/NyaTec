package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;

public class MaceratorRecipe {
    public ObjectArrayList<RecipeOutput> outputs;
    public int recipeTime;

    public MaceratorRecipe(int recipeTime) {
        this.recipeTime = recipeTime;
        this.outputs = new ObjectArrayList<>();
    }

    public MaceratorRecipe() {
        this(100);
    }
}

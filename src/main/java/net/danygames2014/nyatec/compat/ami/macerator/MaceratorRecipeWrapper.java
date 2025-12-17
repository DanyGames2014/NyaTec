package net.danygames2014.nyatec.compat.ami.macerator;

import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.input.RecipeInput;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MaceratorRecipeWrapper implements RecipeWrapper {
    public final MaceratorRecipe recipe;

    public MaceratorRecipeWrapper(@NotNull MaceratorRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        ArrayList<Object> inputs = new ArrayList<>();
        for (RecipeInput input : recipe.inputs) {
            inputs.add(input.getRepresentingStacks());
        }
        return inputs;
    }

    @Override
    public List<?> getOutputs() {
        ArrayList<Object> outputs = new ArrayList<>();
        for (RecipeOutput output : recipe.outputs) {
            outputs.add(output.getOutput(null));
        }
        return outputs;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public void drawAnimations(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Override
    public @Nullable ArrayList<Object> getTooltip(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(@NotNull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}

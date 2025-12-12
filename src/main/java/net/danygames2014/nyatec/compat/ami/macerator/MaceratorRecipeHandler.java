package net.danygames2014.nyatec.compat.ami.macerator;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class MaceratorRecipeHandler implements RecipeHandler<MaceratorRecipe> {
    @Override
    public @NotNull Class<MaceratorRecipe> getRecipeClass() {
        return MaceratorRecipe.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return NyaTec.NAMESPACE.id("macerator").toString();
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull MaceratorRecipe recipe) {
        return new MaceratorRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull MaceratorRecipe recipe) {
        return !recipe.inputs.isEmpty();
    }
}

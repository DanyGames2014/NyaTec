package net.danygames2014.nyatec.compat.ami.generator;

import net.danygames2014.nyatec.NyaTec;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class GeneratorFuelRecipeHandler implements RecipeHandler<GeneratorFuelEntry> {
    @Override
    public @NotNull Class<GeneratorFuelEntry> getRecipeClass() {
        return GeneratorFuelEntry.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return NyaTec.NAMESPACE.id("generator_fuel").toString();
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull GeneratorFuelEntry generatorFuelEntry) {
        return new GeneratorFuelRecipeWrapper(generatorFuelEntry);
    }

    @Override
    public boolean isRecipeValid(@NotNull GeneratorFuelEntry generatorFuelEntry) {
        return generatorFuelEntry.isValid();
    }
}

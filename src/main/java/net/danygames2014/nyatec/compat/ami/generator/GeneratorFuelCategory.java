package net.danygames2014.nyatec.compat.ami.generator;

import net.danygames2014.nyatec.NyaTec;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.AMIDrawable;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.RecipeLayout;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class GeneratorFuelCategory implements RecipeCategory {
    private final AMIDrawable background;
    
    
    public GeneratorFuelCategory() {
        super();
        background = DrawableHelper.createDrawable("/gui/furnace.png", 55, 38, 18, 32, 0, 0, 0, 80);
    }

    @Override
    public @NotNull String getUid() {
        return NyaTec.NAMESPACE.id("generator_fuel").toString();
    }

    @Override
    public @NotNull String getTitle() {
        return "Generator Fuel";
    }

    @Override
    public @NotNull AMIDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {

    }
}

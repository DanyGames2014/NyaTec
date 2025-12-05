package net.danygames2014.nyatec.compat.ami.generator;

import net.glasslauncher.mods.alwaysmoreitems.api.gui.AnimatedDrawable;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.StaticDrawable;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.AMITextRenderer;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GeneratorFuelRecipeWrapper implements RecipeWrapper {
    private final GeneratorFuelEntry fuelEntry;
    private final AnimatedDrawable flame;

    public GeneratorFuelRecipeWrapper(GeneratorFuelEntry fuelEntry) {
        this.fuelEntry = fuelEntry;
        
        StaticDrawable flameDrawable = DrawableHelper.createDrawable("/gui/furnace.png", 176, 0, 14, 13);
        flame = DrawableHelper.createAnimatedDrawable(flameDrawable, fuelEntry.burnTime, AnimatedDrawable.StartDirection.TOP, true);
    }

    @Override
    public List<?> getInputs() {
        return List.of(fuelEntry.fuel);
    }

    @Override
    public List<?> getOutputs() {
        return List.of();
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        AMITextRenderer.INSTANCE.draw("Burn time: " + fuelEntry.burnTime + " ticks", 24, 12, Color.DARK_GRAY.getRGB());
        AMITextRenderer.INSTANCE.draw("Generates " + fuelEntry.energyValue + " EU", 24, 24, Color.DARK_GRAY.getRGB());
    }

    @Override
    public void drawAnimations(@NotNull Minecraft minecraft, int recipeWidth, int recipeHeight) {
        flame.draw(minecraft, 2, -1);
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

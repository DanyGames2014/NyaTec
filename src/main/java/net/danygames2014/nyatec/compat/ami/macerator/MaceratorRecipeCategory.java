package net.danygames2014.nyatec.compat.ami.macerator;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.output.ChanceRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RangeRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;
import net.glasslauncher.mods.alwaysmoreitems.api.gui.*;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MaceratorRecipeCategory implements RecipeCategory {
    private final AMIDrawable background;
    private final AnimatedDrawable energy;
    private final AnimatedDrawable arrow;

    public MaceratorRecipeCategory() {
        super();
        background = DrawableHelper.createDrawable("/assets/nyatec/stationapi/textures/gui/macerator.png", 54, 15, 84, 56, 0, 0, 0, 0);

        StaticDrawable energyDrawable = DrawableHelper.createDrawable("/assets/nyatec/stationapi/textures/gui/macerator.png", 176, 0, 14, 14);
        this.energy = DrawableHelper.createAnimatedDrawable(energyDrawable, 300, AnimatedDrawable.StartDirection.TOP, true);

        StaticDrawable arrowDrawable = DrawableHelper.createDrawable("/assets/nyatec/stationapi/textures/gui/macerator.png", 176, 14, 24, 17);
        this.arrow = DrawableHelper.createAnimatedDrawable(arrowDrawable, 200, AnimatedDrawable.StartDirection.LEFT, false);
    }

    @Override
    public @NotNull String getUid() {
        return NyaTec.NAMESPACE.id("macerator").toString();
    }

    @Override
    public @NotNull String getTitle() {
        return "Macerator";
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
        energy.draw(minecraft, 2, 21);
        arrow.draw(minecraft, 25, 19);
    }

    private final Object2ObjectOpenHashMap<ItemStack, RecipeOutput> slotToOutputMap = new Object2ObjectOpenHashMap<>();

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {
        if (recipeWrapper instanceof MaceratorRecipeWrapper maceratorRecipeWrapper) {
            MaceratorRecipe recipe = maceratorRecipeWrapper.recipe;

            GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

            guiItemStacks.init(0, true, 1, 1);
            guiItemStacks.set(0, recipe.inputs.get(0).getRepresentingStacks());

            int i = 0, pI = 0, sI = 0;
            for (var output : recipe.outputs) {
                i++;
                
                switch (output.type) {
                    case PRIMARY -> {
                        guiItemStacks.init(i, false, 61 + (pI * 22), 19);
                        pI++;
                    }
                    case SECONDARY, WASTE -> {
                        guiItemStacks.init(i, false, 61 + (sI * 22), 41);
                        sI++;
                    }
                }

                ItemStack stack = output.getOutput(null);
                guiItemStacks.set(i, stack);
                slotToOutputMap.put(stack, output);
            }

            guiItemStacks.addTooltipCallback(this::callMeMaybe);
        }
    }

    private void callMeMaybe(int slotIndex, boolean input, ItemStack ingredient, ArrayList<Object> tooltip) {
        if (!slotToOutputMap.containsKey(ingredient)) {
            return;
        }
        
        RecipeOutput output = slotToOutputMap.get(ingredient);
        if (output instanceof ChanceRecipeOutput chanceOutput) {
            tooltip.add(Formatting.GRAY + "Output Chance: " + (100 / chanceOutput.chance) + "%");
        }
        
        if (output instanceof RangeRecipeOutput rangeOutput) {
            tooltip.add(Formatting.GRAY + "Output Range: " + rangeOutput.minCount + "-" + rangeOutput.maxCount);
        }
    }

}

package net.danygames2014.nyatec.event;

import net.danygames2014.nyatec.recipe.InductionFurnaceRecipe;
import net.danygames2014.nyatec.recipe.InductionFurnaceRecipeRegistry;
import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.util.Identifier;

public class InductionFurnaceRecipeRegisterEvent extends Event {
    public InductionFurnaceRecipeRegistry registry;

    public InductionFurnaceRecipeRegisterEvent() {
        registry = InductionFurnaceRecipeRegistry.getInstance();
    }

    public boolean register(Identifier identifier, InductionFurnaceRecipe recipe) {
        return InductionFurnaceRecipeRegistry.register(identifier, recipe);
    }
}

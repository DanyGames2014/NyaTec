package net.danygames2014.nyatec.event;

import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.util.Identifier;

public class MaceratorRecipeRegisterEvent extends Event {
    public MaceratorRecipeRegistry registry;

    public MaceratorRecipeRegisterEvent() {
        registry = MaceratorRecipeRegistry.getInstance();
    }

    public boolean register(Identifier identifier, MaceratorRecipe recipe) {
        return MaceratorRecipeRegistry.register(identifier, recipe);
    }
}

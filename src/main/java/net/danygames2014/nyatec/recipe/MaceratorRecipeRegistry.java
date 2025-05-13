package net.danygames2014.nyatec.recipe;

import net.danygames2014.nyatec.NyaTec;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class MaceratorRecipeRegistry {
    public final HashMap<Identifier, MaceratorRecipe> registry;
    public static MaceratorRecipeRegistry INSTANCE;

    public static MaceratorRecipeRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MaceratorRecipeRegistry();
        }

        return INSTANCE;
    }

    public MaceratorRecipeRegistry() {
        this.registry = new HashMap<>();
    }

    public static HashMap<Identifier, MaceratorRecipe> getRegistry() {
        return getInstance().registry;
    }

    public static boolean register(Identifier identifier, MaceratorRecipe recipe) {
        if (getInstance().registry.containsKey(identifier)) {
            NyaTec.LOGGER.warn("Macerator Recipe " + identifier + " already exists!");
            return false;
        }

        getInstance().registry.put(identifier, recipe);
        return true;
    }

    public static MaceratorRecipe get(Identifier identifier) {
        return getInstance().registry.getOrDefault(identifier, null);
    }

    public static MaceratorRecipe get(ItemStack[] input) {
        var r = getInstance();

        for (var recipe : r.registry.entrySet()) {
            if (recipe.getValue().matches(input)) {
                return recipe.getValue();
            }
        }

        return null;
    }
}

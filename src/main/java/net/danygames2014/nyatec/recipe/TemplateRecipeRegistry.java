package net.danygames2014.nyatec.recipe;

import net.danygames2014.nyatec.NyaTec;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class TemplateRecipeRegistry {
    public final HashMap<Identifier, MachineRecipe> registry;
    public static TemplateRecipeRegistry INSTANCE;

    public static TemplateRecipeRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TemplateRecipeRegistry();
        }

        return INSTANCE;
    }

    public TemplateRecipeRegistry() {
        this.registry = new HashMap<>();
    }

    public static HashMap<Identifier, MachineRecipe> getRegistry() {
        return getInstance().registry;
    }

    public static boolean register(Identifier identifier, MachineRecipe recipe) {
        if (getInstance().registry.containsKey(identifier)) {
            NyaTec.LOGGER.warn("Recipe " + identifier + " already exists!");
            return false;
        }

        getInstance().registry.put(identifier, recipe);
        return true;
    }

    public static MachineRecipe get(Identifier identifier) {
        return getInstance().registry.getOrDefault(identifier, null);
    }

    public static MachineRecipe get(ItemStack[] input) {
        var r = getInstance();

        for (var recipe : r.registry.entrySet()) {
            if (recipe.getValue().matches(input)) {
                return recipe.getValue();
            }
        }

        return null;
    }
}

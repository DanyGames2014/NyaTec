package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class MaceratorRecipeRegistry {
    public final HashMap<Identifier, MaceratorRecipe> registry;
    public final Int2ObjectMap<MaceratorRecipe> cache;

    public static MaceratorRecipeRegistry INSTANCE;

    public static MaceratorRecipeRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MaceratorRecipeRegistry();
        }

        return INSTANCE;
    }

    public MaceratorRecipeRegistry() {
        this.registry = new HashMap<>();
        this.cache = new Int2ObjectOpenHashMap<>();
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
        long startTime = System.nanoTime();

        var r = getInstance();

        // Calculate a hash based on the array contents
        int arrayHash = Arrays.hashCode(input);
        
        // Check if the cache contains this input
        if (r.cache.containsKey(arrayHash)) {
            System.out.println("Match time: " + (System.nanoTime() - startTime) / 1000 + "μs [CACHE HIT]");
        } else {
            r.cache.put(arrayHash, fetch(input));
            System.out.println("Match time: " + (System.nanoTime() - startTime) / 1000 + "μs");
        }

        return r.cache.get(arrayHash);
    }

    private static MaceratorRecipe fetch(ItemStack[] input) {
        var r = getInstance();

        for (var recipe : r.registry.entrySet()) {
            if (recipe.getValue().matches(input)) {
                return recipe.getValue();
            }
        }

        return null;
    }
}

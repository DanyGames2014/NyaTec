package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.util.HashUtil;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class InductionFurnaceRecipeRegistry {
    public final HashMap<Identifier, InductionFurnaceRecipe> registry;
    public final Long2ObjectMap<InductionFurnaceRecipe> cache;

    public static InductionFurnaceRecipeRegistry INSTANCE;

    public static InductionFurnaceRecipeRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InductionFurnaceRecipeRegistry();
        }

        return INSTANCE;
    }

    public InductionFurnaceRecipeRegistry() {
        this.registry = new HashMap<>();
        this.cache = new Long2ObjectOpenHashMap<>();
    }

    public static HashMap<Identifier, InductionFurnaceRecipe> getRegistry() {
        return getInstance().registry;
    }

    public static boolean register(Identifier identifier, InductionFurnaceRecipe recipe) {
        if (getInstance().registry.containsKey(identifier)) {
            NyaTec.LOGGER.warn("Induction Furnace Recipe " + identifier + " already exists!");
            return false;
        }

        getInstance().registry.put(identifier, recipe);
        return true;
    }

    public static InductionFurnaceRecipe get(Identifier identifier) {
        return getInstance().registry.getOrDefault(identifier, null);
    }

    public static InductionFurnaceRecipe get(ItemStack[] input) {
        var r = getInstance();

        // Calculate a hash based on the array contents
        long arrayHash = HashUtil.hashInputs(input);

        // Check if the cache contains this input
        if (!r.cache.containsKey(arrayHash)) {
            r.cache.put(arrayHash, fetch(input));
        }

        return r.cache.get(arrayHash);
    }

    private static InductionFurnaceRecipe fetch(ItemStack[] input) {
        var r = getInstance();

        for (Map.Entry<Identifier, InductionFurnaceRecipe> recipe : r.registry.entrySet()) {
            if (recipe.getValue().matches(input)) {
                return recipe.getValue();
            }
        }

        return null;
    }
}

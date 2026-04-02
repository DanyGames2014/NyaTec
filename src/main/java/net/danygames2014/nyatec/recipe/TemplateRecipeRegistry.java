package net.danygames2014.nyatec.recipe;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.util.HashUtil;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class TemplateRecipeRegistry<T extends MachineRecipe> {
    public final HashMap<Identifier, T> registry;
    public final Long2ObjectOpenHashMap<T> cache;

    public TemplateRecipeRegistry() {
        this.registry = new HashMap<>();
        this.cache = new Long2ObjectOpenHashMap<>();
    }

    public HashMap<Identifier, T> getRegistry() {
        return registry;
    }

    public boolean register(Identifier identifier, T recipe) {
        if (registry.containsKey(identifier)) {
            NyaTec.LOGGER.warn("Macerator Recipe " + identifier + " already exists!");
            return false;
        }

        registry.put(identifier, recipe);
        return true;
    }

    public T get(Identifier identifier) {
        return registry.getOrDefault(identifier, null);
    }

    public T get(ItemStack[] input) {
        // Calculate a hash based on the array contents
        long arrayHash = HashUtil.hashInputs(input);
        
        // Check if the cache contains this input
        if (!cache.containsKey(arrayHash)) {
            cache.put(arrayHash, fetch(input));
        }

        return cache.get(arrayHash);
    }

    private T fetch(ItemStack[] input) {
        for (var recipe : registry.entrySet()) {
            if (recipe.getValue().matches(input)) {
                return recipe.getValue();
            }
        }

        return null;
    }
}

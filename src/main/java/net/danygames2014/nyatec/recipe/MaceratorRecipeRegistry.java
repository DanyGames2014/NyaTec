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

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class MaceratorRecipeRegistry extends TemplateRecipeRegistry<MaceratorRecipe>{
    public static MaceratorRecipeRegistry INSTANCE = new MaceratorRecipeRegistry();
}

package net.danygames2014.nyatec.recipe;

import net.minecraft.item.ItemStack;

public final class RecipeOutput {
    private final ItemStack stack;

    public RecipeOutput(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getOutput() {
        return stack;
    }
}

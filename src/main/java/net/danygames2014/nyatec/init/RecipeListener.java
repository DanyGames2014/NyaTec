package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.event.MaceratorRecipeRegisterEvent;
import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.input.ItemRecipeInput;
import net.danygames2014.nyatec.recipe.output.ChanceRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RangeRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeListener {
    @EventListener
    public void registerMaceratorRecipes(MaceratorRecipeRegisterEvent event) {
        event.register(
                NyaTec.NAMESPACE.id("cobblestone_to_sand"),
                (MaceratorRecipe) new MaceratorRecipe()
                        .addInput(new ItemRecipeInput(Block.COBBLESTONE.asItem()))
                        .addOutput(new RecipeOutput(new ItemStack(Block.SAND)))
                        .addOutput(new ChanceRecipeOutput(new ItemStack(Block.SAND), 4))
                        .addOutput(new ChanceRecipeOutput(new ItemStack(Item.FLINT), RecipeOutputType.SECONDARY, 4))
        );

        event.register(
                NyaTec.NAMESPACE.id("bone_to_bone_meal"),
                (MaceratorRecipe) new MaceratorRecipe(40)
                        .addInput(new ItemRecipeInput(Item.BONE.asItem()))
                        .addOutput(new RangeRecipeOutput(new ItemStack(Item.DYE, 1, 15), 3, 5))
        );

    }
}

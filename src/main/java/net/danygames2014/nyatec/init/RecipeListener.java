package net.danygames2014.nyatec.init;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.event.InductionFurnaceRecipeRegisterEvent;
import net.danygames2014.nyatec.event.MaceratorRecipeRegisterEvent;
import net.danygames2014.nyatec.recipe.InductionFurnaceRecipe;
import net.danygames2014.nyatec.recipe.MaceratorRecipe;
import net.danygames2014.nyatec.recipe.input.ItemRecipeInput;
import net.danygames2014.nyatec.recipe.input.TagRecipeInput;
import net.danygames2014.nyatec.recipe.output.ChanceRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RangeRecipeOutput;
import net.danygames2014.nyatec.recipe.output.RecipeOutput;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

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

        event.register(
                NyaTec.NAMESPACE.id("iron_ore_to_dust"),
                (MaceratorRecipe) new MaceratorRecipe(80)
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ore/iron"))))
                        .addOutput(new RecipeOutput(new ItemStack(NyaTec.ironDust, 2)))
                        .addOutput(new ChanceRecipeOutput(new ItemStack(NyaTec.ironDust, 1), RecipeOutputType.SECONDARY, 10))
        );

        event.register(
                NyaTec.NAMESPACE.id("gold_ore_to_dust"),
                (MaceratorRecipe) new MaceratorRecipe(80)
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ore/gold"))))
                        .addOutput(new RecipeOutput(new ItemStack(NyaTec.goldDust, 2)))
                        .addOutput(new ChanceRecipeOutput(new ItemStack(NyaTec.goldDust, 1), RecipeOutputType.SECONDARY, 15))
        );

        event.register(
                NyaTec.NAMESPACE.id("copper_ore_to_dust"),
                (MaceratorRecipe) new MaceratorRecipe(80)
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ore/copper"))))
                        .addOutput(new RecipeOutput(new ItemStack(NyaTec.copperDust, 2)))
                        .addOutput(new ChanceRecipeOutput(new ItemStack(NyaTec.copperDust, 1), RecipeOutputType.SECONDARY, 10))
        );

        event.register(
                NyaTec.NAMESPACE.id("tin_ore_to_dust"),
                (MaceratorRecipe) new MaceratorRecipe(80)
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ore/tin"))))
                        .addOutput(new RecipeOutput(new ItemStack(NyaTec.tinDust, 2)))
                        .addOutput(new ChanceRecipeOutput(new ItemStack(NyaTec.tinDust, 1), RecipeOutputType.SECONDARY, 10))
        );

        event.register(
                NyaTec.NAMESPACE.id("coal_ore_to_dust"),
                (MaceratorRecipe) new MaceratorRecipe(80)
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ore/coal"))))
                        .addOutput(new RangeRecipeOutput(new ItemStack(NyaTec.coalDust, 1), 1, 3))
        );

        event.register(
                NyaTec.NAMESPACE.id("coal_to_dust"),
                (MaceratorRecipe) new MaceratorRecipe(80)
                        .addInput(new ItemRecipeInput(Item.COAL, 1, 0))
                        .addOutput(new RecipeOutput(new ItemStack(NyaTec.coalDust, 1)))
        );
    }
    
    @EventListener
    public void registerInductionFurnaceRecipes(InductionFurnaceRecipeRegisterEvent event) {
        event.register(
                NyaTec.NAMESPACE.id("test"),
                (InductionFurnaceRecipe) new InductionFurnaceRecipe(100)
                        .addInput(new ItemRecipeInput(Item.IRON_INGOT, 2))
                        .addInput(new ItemRecipeInput(Item.GOLD_INGOT))
                        .addOutput(new RecipeOutput(new ItemStack(Item.BRICK, 2)))
        );

        event.register(
                NyaTec.NAMESPACE.id("bronze_ingot"),
                (InductionFurnaceRecipe) new InductionFurnaceRecipe(120)
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ingot/copper")), 3))
                        .addInput(new TagRecipeInput(TagKey.of(ItemRegistry.KEY, Identifier.of("minecraft:ingot/tin")), 1))
                        .addOutput(new RecipeOutput(new ItemStack(NyaTec.bronzeIngot, 4)))
        );
    }
}

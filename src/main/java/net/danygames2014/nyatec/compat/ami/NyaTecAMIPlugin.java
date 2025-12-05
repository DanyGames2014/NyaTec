package net.danygames2014.nyatec.compat.ami;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.compat.ami.generator.GeneratorFuelCategory;
import net.danygames2014.nyatec.compat.ami.generator.GeneratorFuelEntry;
import net.danygames2014.nyatec.compat.ami.generator.GeneratorFuelRecipeHandler;
import net.danygames2014.nyatec.util.FuelUtil;
import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class NyaTecAMIPlugin implements ModPluginProvider {
    private ItemRegistry itemRegistry;
    private AMIHelpers amiHelpers;
    
    @Override
    public String getName() {
        return "NyaTec";
    }

    @Override
    public Identifier getId() {
        return NyaTec.NAMESPACE.id("nyatec");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {
        this.amiHelpers = amiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @Override
    public void register(ModRegistry registry) {
        registry.addRecipeCategories(new GeneratorFuelCategory());
        
        registry.addRecipeHandlers(new GeneratorFuelRecipeHandler());
        
        registry.addRecipes(FuelUtil.getGeneratorFuelEntries(itemRegistry, amiHelpers));
    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {
        
    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound nbtCompound) {
        return null;
    }

    @Override
    public void updateBlacklist(AMIHelpers amiHelpers) {
        
    }
}

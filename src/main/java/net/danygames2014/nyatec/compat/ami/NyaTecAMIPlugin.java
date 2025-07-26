package net.danygames2014.nyatec.compat.ami;

import net.danygames2014.nyatec.NyaTec;
import net.danygames2014.nyatec.compat.ami.generator.GeneratorFuelCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

public class NyaTecAMIPlugin implements ModPluginProvider {
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
        
    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {
        
    }

    @Override
    public void register(ModRegistry registry) {
        registry.addRecipeCategories(
                new GeneratorFuelCategory()
        );
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

package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.danygames2014.nyatec.recipe.MachineRecipe;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class MaceratorBlockEntity extends BaseRecipeMachineBlockEntity {
    public MaceratorBlockEntity() {
        super(200, 2);
        this.addInput();
        this.addOutput(RecipeOutputType.PRIMARY);
        this.addOutput(RecipeOutputType.SECONDARY);
        this.addSlot(SlotType.FUEL);
    }

    @Override
    public MachineRecipe fetchRecipe(ItemStack[] input) {
        return MaceratorRecipeRegistry.get(input);
    }

    // Energy
    @Override
    public int getMaxInputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getMaxEnergyInput(@Nullable Direction direction) {
        return 10;
    }

    @Override
    public boolean canReceiveEnergy(@Nullable Direction direction) {
        return true;
    }

    @Override
    public void onOvervoltage(@Nullable Direction direction, double voltage) {

    }

    @Override
    public boolean canConnectEnergy(Direction direction) {
        return true;
    }

    @Override
    public int getEnergyCapacity() {
        return 100;
    }

    // Inventory
    @Override
    public String getName() {
        return "Macerator";
    }
}

package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyatec.recipe.MaceratorRecipeRegistry;
import net.danygames2014.nyatec.recipe.MachineRecipe;
import net.danygames2014.nyatec.recipe.output.RecipeOutputType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
    public void tick() {
        super.tick();

        if (energy <= 90) {
            if (getSlot(SlotType.FUEL, 0) != null) {
                ItemStack fuelStack = getSlot(SlotType.FUEL, 0);
                if (fuelStack.getItem() == Item.REDSTONE) {
                    fuelStack.count--;
                    energy += 10;
                }

                if (fuelStack.count <= 0) {
                    setSlot(SlotType.FUEL, 0, null);
                }
            }
        }


        if (!world.isRemote) {
            // Update lit state
            if (progress <= 0 && world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, false));
            } else if (progress > 0 && !world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, true));
            }
        }
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

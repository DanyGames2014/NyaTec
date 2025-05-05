package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.energy.template.block.entity.EnergyConsumerBlockEntityTemplate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.SmeltingRecipeManager;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class ElectricFurnaceBlockEntity extends EnergyConsumerBlockEntityTemplate implements Inventory {
    public int cookProgress;
    public static final int MAX_COOK_TIME = 200;
    public final int cookSpeed = 2;

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
            // Check if we can smelt
            if (canSmelt()) {
                // Check if we have energy
                if (this.energy > 0) {
                    // If we have energy and we can cook, we cookin
                    cookProgress += removeEnergy(cookSpeed);
                } else {
                    // If we don't have energy, slowly revert progress
                    cookProgress -= 2;
                }
            } else {
                // We can't smelt, reset the progress
                cookProgress = 0;
            }

            // Check the cook progress
            if (cookProgress < 0) {
                // If it's less than zero, can it to zero
                cookProgress = 0;
            } else if (cookProgress >= MAX_COOK_TIME) {
                // If we have reached the max cook time, craft the recipe
                cookProgress = 0;
                craftRecipe();
            }

            // Update lit state
            if (cookProgress <= 0 && world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, false));
            } else if (cookProgress > 0 && !world.getBlockState(this.x, this.y, this.z).get(Properties.LIT)) {
                world.setBlockStateWithNotify(this.x, this.y, this.z, world.getBlockState(this.x, this.y, this.z).with(Properties.LIT, true));
            }
        }
    }

    public boolean canSmelt() {
        if (this.inventory[0] == null) {
            return false;
        } else {
            ItemStack craftedItem = SmeltingRecipeManager.getInstance().craft(this.inventory[0].getItem().id);
            if (craftedItem == null) {
                return false;
            } else if (this.inventory[1] == null) {
                return true;
            } else if (!this.inventory[1].isItemEqual(craftedItem)) {
                return false;
            } else if (this.inventory[1].count < this.getMaxCountPerStack() && this.inventory[1].count < this.inventory[1].getMaxCount()) {
                return true;
            } else {
                return this.inventory[1].count < craftedItem.getMaxCount();
            }
        }
    }

    public void craftRecipe() {
        if (this.canSmelt()) {
            ItemStack craftedItem = SmeltingRecipeManager.getInstance().craft(this.inventory[0].getItem().id);
            if (this.inventory[1] == null) {
                this.inventory[1] = craftedItem.copy();
            } else if (this.inventory[1].itemId == craftedItem.itemId) {
                ++this.inventory[1].count;
            }

            --this.inventory[0].count;
            if (this.inventory[0].count <= 0) {
                this.inventory[0] = null;
            }

        }
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
    private ItemStack[] inventory = new ItemStack[2];
    // 0 Input
    // 1 Output

    @Override
    public int size() {
        return inventory.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot < 0 || slot >= inventory.length) {
            return null;
        }

        return inventory[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (slot < 0 || slot >= inventory.length) {
            return null;
        }

        ItemStack stack = null;

        if (this.inventory[slot] != null) {
            if (this.inventory[slot].count <= amount) {
                stack = this.inventory[slot];
                this.inventory[slot] = null;
            } else {
                stack = this.inventory[slot].split(amount);
                if (this.inventory[slot].count == 0) {
                    this.inventory[slot] = null;
                }
            }
        }

        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot < 0 || slot >= inventory.length) {
            return;
        }

        this.inventory[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }
    }

    @Override
    public String getName() {
        return "Electric Furnace";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return !(player.getSquaredDistance(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) > 64.0D);
        }
    }

    // NBT
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        // Inventory
        NbtList itemsNbt = nbt.getList("Items");
        this.inventory = new ItemStack[this.size()];

        for (int slot = 0; slot < itemsNbt.size(); ++slot) {
            NbtCompound stackNbt = (NbtCompound) itemsNbt.get(slot);
            byte slotIndex = stackNbt.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < this.inventory.length) {
                this.inventory[slotIndex] = new ItemStack(stackNbt);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        // Inventory
        NbtList itemsNbt = new NbtList();

        for (int slot = 0; slot < this.inventory.length; ++slot) {
            if (this.inventory[slot] != null) {
                NbtCompound stackNbt = new NbtCompound();
                stackNbt.putByte("Slot", (byte) slot);
                this.inventory[slot].writeNbt(stackNbt);
                itemsNbt.add(stackNbt);
            }
        }

        nbt.put("Items", itemsNbt);
    }
}

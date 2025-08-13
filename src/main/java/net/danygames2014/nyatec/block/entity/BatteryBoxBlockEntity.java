package net.danygames2014.nyatec.block.entity;

import net.danygames2014.nyalib.capability.CapabilityHelper;
import net.danygames2014.nyalib.capability.item.energyhandler.EnergyStorageItemCapability;
import net.danygames2014.nyatec.NyaTec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class BatteryBoxBlockEntity extends BaseEnergyStorageBlockEntity implements Inventory {
    public BatteryBoxBlockEntity() {
        super(10000);
    }

    @Override
    public void tick() {
        super.tick();
        
        chargeItem();
        dischargeItem();
    }

    public void chargeItem() {
        if (energy == 0) {
            return;
        }
        
        if (inventory[0] == null) {
            return;
        }

        // Energy Storage Item
        EnergyStorageItemCapability item = CapabilityHelper.getCapability(inventory[0], EnergyStorageItemCapability.class);
        if (item != null && item.canReceiveEnergy() && item.getRemainingCapacity() > 0) {
            removeEnergy(item.receiveEnergy(Math.min(getMaxEnergyOutput(null), getEnergyStored())));
        }
    }

    public void dischargeItem() {
        if (energy == getEnergyCapacity()) {
            return;
        }

        if (inventory[1] == null) {
            return;
        }

        // Redstone
        if (inventory[1].getItem() == Item.REDSTONE) {
            if (energy + NyaTec.MACHINE_CONFIG.redstoneEnergyValue <= getEnergyCapacity()) {
                inventory[1].count--;
                energy += NyaTec.MACHINE_CONFIG.redstoneEnergyValue;
            }

            if (inventory[1].count <= 0) {
                inventory[1] = null;
            }
        }

        // Energy Storage Item
        EnergyStorageItemCapability energyStorage = CapabilityHelper.getCapability(inventory[1], EnergyStorageItemCapability.class);
        if (energyStorage != null && energyStorage.canExtractEnergy() && energyStorage.getEnergyStored() > 0) {
            addEnergy(energyStorage.extractEnergy(Math.min(getRemainingCapacity(), getMaxEnergyInput(null))));
        }
    }

    @Override
    public int getMaxOutputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getOutputVoltage(@Nullable Direction direction) {
        return getEnergyStored() > 0 ? getMaxOutputVoltage(direction) : 0;
    }

    @Override
    public int getMaxEnergyOutput(@Nullable Direction direction) {
        return 880;
    }

    @Override
    public boolean canExtractEnergy(@Nullable Direction direction) {
        return this.world.getBlockState(this.x, this.y, this.z).get(Properties.FACING).equals(direction);
    }

    @Override
    public boolean canConnectEnergy(Direction direction) {
        return true;
    }

    @Override
    public int getMaxInputVoltage(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public int getMaxEnergyInput(@Nullable Direction direction) {
        return 220;
    }

    @Override
    public boolean canReceiveEnergy(@Nullable Direction direction) {
        return !this.world.getBlockState(this.x, this.y, this.z).get(Properties.FACING).equals(direction);
    }

    @Override
    public void onOvervoltage(@Nullable Direction direction, double voltage) {

    }

    // Inventory
    // 0 - Charge Slot
    // 1 - Discharge Slot
    ItemStack[] inventory = new ItemStack[2];

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
        return "Battery Box";
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
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

        for (int slot = 0; slot < itemsNbt.size(); slot++) {
            NbtCompound stackNbt = (NbtCompound) itemsNbt.get(slot);
            byte slotIndex = stackNbt.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length) {
                inventory[slotIndex] = new ItemStack(stackNbt);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        // Inventory
        // Input
        NbtList itemsNbt = new NbtList();

        for (int slot = 0; slot < inventory.length; slot++) {
            if (inventory[slot] == null) {
                continue;
            }

            NbtCompound stackNbt = new NbtCompound();
            stackNbt.putByte("Slot", (byte) slot);
            inventory[slot].writeNbt(stackNbt);
            itemsNbt.add(stackNbt);
        }

        nbt.put("Items", itemsNbt);
    }
}

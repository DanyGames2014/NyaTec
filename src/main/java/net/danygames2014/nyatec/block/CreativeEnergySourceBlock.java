package net.danygames2014.nyatec.block;

import net.danygames2014.nyalib.energy.template.block.EnergySourceBlockTemplate;
import net.danygames2014.nyatec.block.entity.CreativeEnergySourceBlockEntity;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.api.Wrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.util.Identifier;

public class CreativeEnergySourceBlock extends EnergySourceBlockTemplate implements Wrenchable {
    public static final BooleanProperty ENABLED = BooleanProperty.of("enabled");
    
    public CreativeEnergySourceBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ENABLED);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new CreativeEnergySourceBlockEntity();
    }

    @Override
    public boolean wrenchRightClick(ItemStack stack, PlayerEntity player, boolean isSneaking, World world, int x, int y, int z, int side, WrenchMode wrenchMode) {
        if (wrenchMode == WrenchMode.MODE_WRENCH && !isSneaking) {
            if (world.getBlockEntity(x, y, z) instanceof CreativeEnergySourceBlockEntity creativeEnergySourceBlockEntity) {
                creativeEnergySourceBlockEntity.inverted = !creativeEnergySourceBlockEntity.inverted;
                player.sendMessage("Inverted: " + creativeEnergySourceBlockEntity.inverted);
                return true;
            }
        }
        
        return Wrenchable.super.wrenchRightClick(stack, player, isSneaking, world, x, y, z, side, wrenchMode);
    }
}
